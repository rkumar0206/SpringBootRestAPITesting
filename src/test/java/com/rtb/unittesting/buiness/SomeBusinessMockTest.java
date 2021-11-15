package com.rtb.unittesting.buiness;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rtb.unittesting.business.SomeBusinessImpl;
import com.rtb.unittesting.data.SomeDataService;

@ExtendWith(MockitoExtension.class)
class SomeBusinessMockTest {

	@InjectMocks
	SomeBusinessImpl business;
	
	@Mock
	SomeDataService dataServiceMock;
	

	// ---------------------------------------------
	
	/*
	 * Instead of using the below lines, we can make use of
	 * @InjectMocks and @Mock and also @ExtendWith(MockitoExtension.class)
	 *  to instantiate the SomeBusinessImpl and SomeDataService,
	 * we also don't need the before method because now mockito will
	 * automatically set the dataServiceMock wherever required
	 */
	
//	SomeBusinessImpl business = new SomeBusinessImpl();
//	SomeDataService dataServiceMock = mock(SomeDataService.class);
//
//	@BeforeEach
//	public void before() {
//
//		business.setSomeDataService(dataServiceMock);
//	}
	
	//-----------------------------------------------------

	@Test
	public void calculateSumDataService_basic() {

		when(dataServiceMock.retriveAllData()).thenReturn(new int[] { 1, 2, 3 });

		assertEquals(6, business.calculateSumUsingDataService());
	}

	@Test
	public void calculateSumDataService_empty() {

		when(dataServiceMock.retriveAllData()).thenReturn(new int[] {});
		assertEquals(0, business.calculateSumUsingDataService());
	}

	@Test
	public void calculateSumSumDataService_oneValue() {

		when(dataServiceMock.retriveAllData()).thenReturn(new int[] { 5 });
		assertEquals(5, business.calculateSumUsingDataService());
	}

}
