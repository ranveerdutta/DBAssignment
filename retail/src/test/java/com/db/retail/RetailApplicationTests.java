package com.db.retail;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.db.retail.rest.service.ShopRestService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RetailApplicationTests {
	
	@Autowired
	private ShopRestService shopRestService;

	//test case to verify the application context is initialized properly
	@Test
	public void contextLoads() {
		
		Assert.assertNotNull(shopRestService);
	}

}
