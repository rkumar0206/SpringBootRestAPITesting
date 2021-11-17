package com.rtb.unittesting.business;

import java.util.Arrays;

import com.rtb.unittesting.data.SomeDataService;

public class SomeBusinessImpl {

	private SomeDataService someDataService;

	public void setSomeDataService(SomeDataService someDataService) {
		this.someDataService = someDataService;
	}

	public int calculateSum(int[] data) {

		return Arrays.stream(data).reduce(Integer::sum).orElse(0);
	}

	public int calculateSumUsingDataService() {

		int[] data = someDataService.retriveAllData();

		return Arrays.stream(data).reduce(Integer::sum).orElse(0);

	}

}
