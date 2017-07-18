package com.db.retail.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.db.retail.dao.ShopDao;
import com.db.retail.domain.Address;
import com.db.retail.domain.GeoDetails;
import com.db.retail.domain.Shop;
import com.db.retail.exception.ErrorCodes;
import com.db.retail.exception.RetailSystemException;
import com.db.retail.googleservice.client.GeoCodingClient;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestShopService {
	
	@InjectMocks
	@Autowired
	private IShopService shopService;
	
	@Mock
	private GeoCodingClient geoCodingClientMock;
	
	@Autowired
	private ShopDao shopDao;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		shopDao.deleteAll();
	}

	@Test
	public void testAddShopWithValidData() {
		Shop shop = new Shop();
		shop.setShopName("test name");
		Address addr = new Address();
		addr.setNumber("abc");
		addr.setPostCode("ghj");
		shop.setShopAddress(addr);
		
		when(geoCodingClientMock.getGeoDetails(any())).thenReturn(new GeoDetails(2.34, 5.23));
		
		Shop addedShop = shopService.addShop(shop);
		Assert.assertEquals(2.34, addedShop.getStoreGeoDetails().getLatitude(), 0);
		Assert.assertEquals(5.23, addedShop.getStoreGeoDetails().getLongitude(), 0);
		
		List<Shop> shopList = shopDao.getAllShops();
		Assert.assertEquals(1, shopList.size());
		Assert.assertEquals("test name", shopList.get(0).getShopName());
		
		Assert.assertEquals(2.34, shopList.get(0).getStoreGeoDetails().getLatitude(), 0);
		Assert.assertEquals(5.23, shopList.get(0).getStoreGeoDetails().getLongitude(), 0);
	}
	
	@Test
	public void testAddShopWithTwoAddition() {
		Shop shop1 = new Shop();
		shop1.setShopName("test name 1");
		Address addr = new Address();
		addr.setNumber("abc1");
		addr.setPostCode("ghj1");
		shop1.setShopAddress(addr);
		
		when(geoCodingClientMock.getGeoDetails(any())).thenReturn(new GeoDetails(2.34, 5.23));
		
		Shop addedShop1 = shopService.addShop(shop1);
		Assert.assertEquals(2.34, addedShop1.getStoreGeoDetails().getLatitude(), 0);
		Assert.assertEquals(5.23, addedShop1.getStoreGeoDetails().getLongitude(), 0);
		
		Shop shop2 = new Shop();
		shop2.setShopName("test name 2");
		Address addr2 = new Address();
		addr2.setNumber("abc2");
		addr2.setPostCode("ghj2");
		shop2.setShopAddress(addr2);
		
		Shop addedShop2 = shopService.addShop(shop2);
		Assert.assertEquals(2.34, addedShop2.getStoreGeoDetails().getLatitude(), 0);
		Assert.assertEquals(5.23, addedShop2.getStoreGeoDetails().getLongitude(), 0);
		
		List<Shop> shopList = shopDao.getAllShops();
		Assert.assertEquals(2, shopList.size());
		Assert.assertEquals("test name 1", shopList.get(0).getShopName());
		Assert.assertEquals("test name 2", shopList.get(1).getShopName());
		
		Assert.assertEquals(2.34, shopList.get(0).getStoreGeoDetails().getLatitude(), 0);
		Assert.assertEquals(5.23, shopList.get(0).getStoreGeoDetails().getLongitude(), 0);
		Assert.assertEquals(2.34, shopList.get(1).getStoreGeoDetails().getLatitude(), 0);
		Assert.assertEquals(5.23, shopList.get(1).getStoreGeoDetails().getLongitude(), 0);
	}
	
	@Test(expected = RetailSystemException.class)
	public void testAddShopWithInValidData() {
		Shop shop = new Shop();
		shop.setShopName("test name");
		Address addr = new Address();
		addr.setNumber("invalid address");
		addr.setPostCode("invalid address");
		shop.setShopAddress(addr);
		
		when(geoCodingClientMock.getGeoDetails(any())).thenThrow(new RetailSystemException(ErrorCodes.GEOCODE_FETCH_ERROR));
		shopService.addShop(shop);
	}
	
	@Test(expected = RetailSystemException.class)
	public void testAddShopWithNullShopName() {
		Shop shop = new Shop();
		Address addr = new Address();
		addr.setNumber("some address");
		addr.setPostCode("some address");
		shop.setShopAddress(addr);
		
		shopService.addShop(shop);
	}
	

}
