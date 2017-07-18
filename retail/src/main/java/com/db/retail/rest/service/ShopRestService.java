package com.db.retail.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.db.retail.domain.Shop;
import com.db.retail.service.IShopService;

@RestController
public class ShopRestService {
	
	@Autowired
	private IShopService storeService;

	@RequestMapping(value="/shop", method = RequestMethod.POST)
	@ResponseStatus( HttpStatus.CREATED)
	public Shop addShop(@RequestBody Shop shop) {
		return storeService.addShop(shop);
	}
}
