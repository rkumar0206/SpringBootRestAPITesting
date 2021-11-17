package com.rtb.unittesting.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rtb.unittesting.data.SomeDataService;

/*
 * @Mock creates a mock.
 * 
 * @InjectMocks creates an instance of the class and 
 * injects the mocks that are created with the @Mock 
 * (or @Spy) annotations into this instance.	
 *	
 *	Note you must use @RunWith(MockitoJUnitRunner.class) or 
 *	Mockito.initMocks(this) to initialize these mocks and inject them (JUnit 4).
 *
 *	With JUnit 5, you must use @ExtendWith(MockitoExtension.class).
 */

@ExtendWith(MockitoExtension.class)
class SomeBusinessMockTest {

	@InjectMocks
	SomeBusinessImpl business;

	@Mock
	SomeDataService dataServiceMock;

	// ---------------------------------------------

	/*
	 * Instead of using the below lines, we can make use of
	 * 
	 * @InjectMocks and @Mock and also @ExtendWith(MockitoExtension.class) to
	 * instantiate the SomeBusinessImpl and SomeDataService, we also don't need the
	 * before method because now mockito will automatically set the dataServiceMock
	 * wherever required
	 */

//	SomeBusinessImpl business = new SomeBusinessImpl();
//	SomeDataService dataServiceMock = mock(SomeDataService.class);
//
//	@BeforeEach
//	public void before() {
//
//		business.setSomeDataService(dataServiceMock);
//	}

	// -----------------------------------------------------

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
