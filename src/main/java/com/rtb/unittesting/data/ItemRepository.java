package com.rtb.unittesting.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rtb.unittesting.model.Item;

public interface ItemRepository extends JpaRepository<Item, Integer>{

	
}
