package com.rtb.unittesting.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rtb.unittesting.business.ItemBusinessService;
import com.rtb.unittesting.model.Item;

@RestController
public class ItemController {

	@Autowired
	private ItemBusinessService businessService;
	
	@GetMapping("/dummy-item")
	public Item dummyItem() {
		
		return new Item(1, "Ball", 10, 100);
	}

	@GetMapping("/item-from-buiseness-service")
	public Item itemFromBusinessService() {
		
		return businessService.retreiveHardcodedItem();
	}
	
	@GetMapping("/all-items-from-database")
	public List<Item> retriveAllItems() {
		
		return businessService.retrieveAllItems();
	}
}
