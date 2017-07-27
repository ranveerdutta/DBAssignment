package com.db.retail.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.After;
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
		
		Shop oldShop = shopService.addOrReplaceShop(shop);
		Assert.assertEquals(2.34, shop.getShopGeoDetails().getLatitude(), 0);
		Assert.assertEquals(5.23, shop.getShopGeoDetails().getLongitude(), 0);
		Assert.assertNull(oldShop);
		
		Set<Shop> shopSet = shopDao.getAllShops();
		Assert.assertEquals(1, shopSet.size());
		Shop firstShop = shopSet.iterator().next();
		Assert.assertEquals("test name", firstShop.getShopName());
		
		Assert.assertEquals(2.34, firstShop.getShopGeoDetails().getLatitude(), 0);
		Assert.assertEquals(5.23, firstShop.getShopGeoDetails().getLongitude(), 0);
	}
	
	@Test
	public void testAddShopWithDuplicatedData() {
		Shop shop = new Shop();
		shop.setShopName("test name");
		Address addr = new Address();
		addr.setNumber("abc");
		addr.setPostCode("ghj");
		shop.setShopAddress(addr);
		
		when(geoCodingClientMock.getGeoDetails(any())).thenReturn(new GeoDetails(2.34, 5.23));
		
		Shop oldShop = shopService.addOrReplaceShop(shop);
		
		Shop shop1 = new Shop();
		shop1.setShopName("test name");
		when(geoCodingClientMock.getGeoDetails(any())).thenReturn(new GeoDetails(3.45, 4.56));
		shop1.setShopAddress(addr);
		Shop oldShop1 = shopService.addOrReplaceShop(shop1);
		Assert.assertNull(oldShop);
		Assert.assertEquals(2.34, oldShop1.getShopGeoDetails().getLatitude(), 0);
		Assert.assertEquals(5.23, oldShop1.getShopGeoDetails().getLongitude(), 0);
		
		Assert.assertEquals(3.45, shop1.getShopGeoDetails().getLatitude(), 0);
		Assert.assertEquals(4.56, shop1.getShopGeoDetails().getLongitude(), 0);
		
		Set<Shop> shopSet = shopDao.getAllShops();
		Assert.assertEquals(1, shopSet.size());
		Shop firstShop = shopSet.iterator().next();
		Assert.assertEquals("test name", firstShop.getShopName());
		
		Assert.assertEquals(2.34, firstShop.getShopGeoDetails().getLatitude(), 0);
		Assert.assertEquals(5.23, firstShop.getShopGeoDetails().getLongitude(), 0);
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
		
		Shop oldShop1 = shopService.addOrReplaceShop(shop1);
		Assert.assertNull(oldShop1);
		Assert.assertEquals(2.34, shop1.getShopGeoDetails().getLatitude(), 0);
		Assert.assertEquals(5.23, shop1.getShopGeoDetails().getLongitude(), 0);
		
		Shop shop2 = new Shop();
		shop2.setShopName("test name 2");
		Address addr2 = new Address();
		addr2.setNumber("abc2");
		addr2.setPostCode("ghj2");
		shop2.setShopAddress(addr2);
		
		Shop oldShop2 = shopService.addOrReplaceShop(shop2);
		Assert.assertNull(oldShop2);
		Assert.assertEquals(2.34, shop2.getShopGeoDetails().getLatitude(), 0);
		Assert.assertEquals(5.23, shop2.getShopGeoDetails().getLongitude(), 0);
		
		Set<Shop> shopSet = shopDao.getAllShops();
		Assert.assertEquals(2, shopSet.size());
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
		shopService.addOrReplaceShop(shop);
	}
	
	@Test(expected = RetailSystemException.class)
	public void testAddShopWithNullShopName() {
		Shop shop = new Shop();
		Address addr = new Address();
		addr.setNumber("some address");
		addr.setPostCode("some address");
		shop.setShopAddress(addr);
		
		shopService.addOrReplaceShop(shop);
	}
	
	@Test
	public void testAddDuplicateShopsWithConcurrentUsers() throws InterruptedException, ExecutionException {
		Shop shop1 = new Shop();
		shop1.setShopName("test");
		Address addr1 = new Address();
		addr1.setNumber("some address1");
		addr1.setPostCode("some address1");
		shop1.setShopAddress(addr1);
		AddShopCallable callable1 = new AddShopCallable(shop1, shopDao);
		
		Shop shop2 = new Shop();
		shop2.setShopName("test");
		Address addr2 = new Address();
		addr2.setNumber("some address2");
		addr2.setPostCode("some address2");
		shop2.setShopAddress(addr2);
		AddShopCallable callable2 = new AddShopCallable(shop2, shopDao);
		
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Future<Shop> future1 = executor.submit(callable1);
		Future<Shop> future2 = executor.submit(callable2);
		
		Shop oldShop1 = future1.get();
		Shop oldShop2 = future2.get();
		
		Assert.assertTrue((null == oldShop1 && oldShop2 != null) || (null == oldShop2 && oldShop1 != null));
		
	}
	
	@Test
	public void testAddUniqueShopsWithConcurrentUsers() throws InterruptedException, ExecutionException {
		Shop shop1 = new Shop();
		shop1.setShopName("test1");
		Address addr1 = new Address();
		addr1.setNumber("some address1");
		addr1.setPostCode("some address1");
		shop1.setShopAddress(addr1);
		AddShopCallable callable1 = new AddShopCallable(shop1, shopDao);
		
		Shop shop2 = new Shop();
		shop2.setShopName("test2");
		Address addr2 = new Address();
		addr2.setNumber("some address2");
		addr2.setPostCode("some address2");
		shop2.setShopAddress(addr2);
		AddShopCallable callable2 = new AddShopCallable(shop2, shopDao);
		
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Future<Shop> future1 = executor.submit(callable1);
		Future<Shop> future2 = executor.submit(callable2);
		
		Shop oldShop1 = future1.get();
		Shop oldShop2 = future2.get();
		
		Assert.assertTrue(null == oldShop1 && null == oldShop2);
		
	}
	
	@Test
	public void testGetNearestShopWithValidData1() {
		Shop shop1 = new Shop();
		shop1.setShopName("shop1");
		Address addr1 = new Address();
		addr1.setNumber("abc");
		addr1.setPostCode("ghj");
		shop1.setShopAddress(addr1);
		
		when(geoCodingClientMock.getGeoDetails(any())).thenReturn(new GeoDetails(18.5822452, 73.6914557));
		//add shop1
		shopService.addOrReplaceShop(shop1);
		
		
		Shop shop2 = new Shop();
		shop2.setShopName("shop2");
		Address addr2 = new Address();
		addr2.setNumber("abc");
		addr2.setPostCode("ghj");
		shop2.setShopAddress(addr2);
		when(geoCodingClientMock.getGeoDetails(any())).thenReturn(new GeoDetails(17.5707615, 78.4339131));
		//add shop2
		shopService.addOrReplaceShop(shop2);
		
		Shop shop3 = new Shop();
		shop3.setShopName("shop3");
		Address addr3 = new Address();
		addr3.setNumber("abc");
		addr3.setPostCode("ghj");
		shop3.setShopAddress(addr3);
		when(geoCodingClientMock.getGeoDetails(any())).thenReturn(new GeoDetails(12.9373056, 77.6064054));
		//add shop3
		shopService.addOrReplaceShop(shop3);
		
		Shop nearestShop = shopService.getNearestShop(new GeoDetails(12.937, 77.606));
		Assert.assertEquals("shop3", nearestShop.getShopName());
	}
	
	@Test
	public void testGetNearestShopWithValidData2() {
		Shop shop1 = new Shop();
		shop1.setShopName("shop1");
		Address addr1 = new Address();
		addr1.setNumber("abc");
		addr1.setPostCode("ghj");
		shop1.setShopAddress(addr1);
		
		when(geoCodingClientMock.getGeoDetails(any())).thenReturn(new GeoDetails(18.5822452, 73.6914557));
		//add shop1
		shopService.addOrReplaceShop(shop1);
		
		
		Shop shop2 = new Shop();
		shop2.setShopName("shop2");
		Address addr2 = new Address();
		addr2.setNumber("abc");
		addr2.setPostCode("ghj");
		shop2.setShopAddress(addr2);
		when(geoCodingClientMock.getGeoDetails(any())).thenReturn(new GeoDetails(17.5707615, 78.4339131));
		//add shop2
		shopService.addOrReplaceShop(shop2);
		
		Shop shop3 = new Shop();
		shop3.setShopName("shop3");
		Address addr3 = new Address();
		addr3.setNumber("abc");
		addr3.setPostCode("ghj");
		shop3.setShopAddress(addr3);
		when(geoCodingClientMock.getGeoDetails(any())).thenReturn(new GeoDetails(12.9373056, 77.6064054));
		//add shop3
		shopService.addOrReplaceShop(shop3);
		
		Shop nearestShop = shopService.getNearestShop(new GeoDetails(18.5822, 73.6914));
		Assert.assertEquals("shop1", nearestShop.getShopName());
	}
	
	@Test
	public void testGetNearestShopWithValidData3() {
		Shop shop1 = new Shop();
		shop1.setShopName("shop1");
		Address addr1 = new Address();
		addr1.setNumber("abc");
		addr1.setPostCode("ghj");
		shop1.setShopAddress(addr1);
		
		when(geoCodingClientMock.getGeoDetails(any())).thenReturn(new GeoDetails(18.5822452, 73.6914557));
		//add shop1
		shopService.addOrReplaceShop(shop1);
		
		
		Shop shop2 = new Shop();
		shop2.setShopName("shop2");
		Address addr2 = new Address();
		addr2.setNumber("abc");
		addr2.setPostCode("ghj");
		shop2.setShopAddress(addr2);
		when(geoCodingClientMock.getGeoDetails(any())).thenReturn(new GeoDetails(17.5707615, 78.4339131));
		//add shop2
		shopService.addOrReplaceShop(shop2);
		
		Shop shop3 = new Shop();
		shop3.setShopName("shop3");
		Address addr3 = new Address();
		addr3.setNumber("abc");
		addr3.setPostCode("ghj");
		shop3.setShopAddress(addr3);
		when(geoCodingClientMock.getGeoDetails(any())).thenReturn(new GeoDetails(12.9373056, 77.6064054));
		//add shop3
		shopService.addOrReplaceShop(shop3);
		
		Shop nearestShop = shopService.getNearestShop(new GeoDetails(17.570, 78.433));
		Assert.assertEquals("shop2", nearestShop.getShopName());
	}
	
	@Test(expected = RetailSystemException.class)
	public void testGetNearestShopWithoutANyShopInSystem() {
		shopService.getNearestShop(new GeoDetails(17.570, 78.433));
	}

	@After
	public void tearDown() {
		shopDao.deleteAll();
	}
	
}

class AddShopCallable implements Callable<Shop> {
	private Shop shop;
	private ShopDao shopDao;
	AddShopCallable(Shop shop,ShopDao shopDao){
          this.shop=shop;
          this.shopDao=shopDao;             
    }          
    public Shop call() throws Exception {
           return shopDao.createNewOrOverrideExistingShop(shop);
    }
}
