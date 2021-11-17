package com.rtb.unittesting.controller.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.rtb.unittesting.data.ItemRepository;
import com.rtb.unittesting.model.Item;

/*
 * DataJPATest : all the data related elements are fired up. the table is created,
 * datasource is created. It useful for testing the repository classes.
 * In this example we are using JPARepository so there is no use for testing
 * the repository, but in a situation where we are using a normal hibernate 
 * this can be very useful
 */

@DataJpaTest
public class ItemRepositoryTest {

	@Autowired
	private ItemRepository repository;
	
	@Test
	public void testFindAll() {
		
		List<Item> items = repository.findAll();
		
		assertEquals(3, items.size());
	}
}
