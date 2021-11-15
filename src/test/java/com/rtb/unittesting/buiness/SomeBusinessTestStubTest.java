package com.rtb.unittesting.buiness;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.rtb.unittesting.business.SomeBusinessImpl;
import com.rtb.unittesting.data.SomeDataService;

// these are the problems with junit and therefore mokito comes into picture
// we always have to create different stubs for different scenarios, instead of
// this using mokito we can programmatically create class

class SomeDataServiceStub implements SomeDataService {

	@Override
	public int[] retriveAllData() {
		return new int[] {1,2,3};
	}
}

class SomeDataServiceEmptyStub implements SomeDataService {
	
	@Override
	public int[] retriveAllData() {
		return new int[] {};
	}
}

class SomeDataServiceOneValueStub implements SomeDataService {
	
	@Override
	public int[] retriveAllData() {
		return new int[] {5};
	}
}

class SomeBusinessTestStubTest {

	@Test
	public void calculateSumDataService_basic() {
		
		SomeBusinessImpl business = new SomeBusinessImpl();
		
		business.setSomeDataService(new SomeDataServiceStub());
		
		int actualResult =  business.calculateSumUsingDataService();
		int expectedResult = 6;
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void calculateSumDataService_empty() {
		
		SomeBusinessImpl business = new SomeBusinessImpl();
		
		business.setSomeDataService(new SomeDataServiceEmptyStub());
		
		int actualResult =  business.calculateSumUsingDataService();
		int expectedResult = 0;
		assertEquals(expectedResult, actualResult);
	}
	
	@Test
	public void calculateSumSumDataService_oneValue() {
		
		SomeBusinessImpl business = new SomeBusinessImpl();
		
		business.setSomeDataService(new SomeDataServiceOneValueStub());
		
		int actualResult =  business.calculateSumUsingDataService();
		int expectedResult = 5;
		assertEquals(expectedResult, actualResult);
	}

}
