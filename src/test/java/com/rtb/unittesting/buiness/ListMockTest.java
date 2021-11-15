package com.rtb.unittesting.buiness;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;;

class ListMockTest {

	List<String> mock = mock(List.class);
	
	@Test
	void size_basic() {
				
		when(mock.size()).thenReturn(5);
		assertEquals(5,  mock.size());
		
	}
	
	@Test
	void returnDifferentValues() {
		
		List mock = mock(List.class);
		
		// returning multiple values
		when(mock.size()).thenReturn(5).thenReturn(10);
		assertEquals(5,  mock.size());
		assertEquals(10,  mock.size());
		
	}
	
	@Test
	public void returnWithParameter() {
		
		when(mock.get(0)).thenReturn("rohitThebest");
		assertEquals("rohitThebest", mock.get(0));
		assertEquals(null, mock.get(1));  // we will get the default value
	}
	
	@Test
	public void returnWithGenericParameters() {
		
		// here the mokito will return 'rohitThebest' for any integer value
		// here many any methods in mockito
		when(mock.get(anyInt())).thenReturn("rohitThebest");
		assertEquals("rohitThebest", mock.get(0));
		assertEquals("rohitThebest", mock.get(1));  // we will get the default value
	}
	
	@Test
	public void verfifcationBasics() {
		
		// System under test
		String value1 = mock.get(0);
		String value2 = mock.get(1);
		
		/*
		 * Using verify we can check how many times a specific method is called
		 * (here mock.get() and also the a specific is called or not with a 
		 * specific value
		 * 
		 * verify methods are used in the scenarios where the value is not returned
		 * back
		 */
		// verify
		verify(mock).get(0);
		verify(mock, times(2)).get(anyInt());
		verify(mock, atLeast(1)).get(anyInt());
		verify(mock, atLeastOnce()).get(anyInt());
		verify(mock, atMost(2)).get(anyInt());
		verify(mock, never()).get(2);
	}
	
	@Test
	public void argumentCapturing() {
		
		// System under test
		mock.add("SomeString");
		
		/*
		 * In verification we want to capture the "SomeString" argument.
		 * Scenarios where instead of returning value back, only method is invoked,
		 * like here List.add does not return any value but the add method is
		 * surely invoked
		 */
		// Verification
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(mock).add(captor.capture());
		
		assertEquals("SomeString", captor.getValue());
	}
	
	
	@Test
	public void multipleArgumentCapturing() {
		
		// System under test
		mock.add("SomeString1");
		mock.add("SomeString2");
		
		/*
		 * In verification we want to capture the multiple values
		 */
		// Verification
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		
		// by default the verify checks a method is only called for once
		verify(mock, times(2)).add(captor.capture());
		
		List<String> allValues = captor.getAllValues();
		assertEquals("SomeString1", allValues.get(0));
		assertEquals("SomeString2", allValues.get(1));
	}
	
	@Test
	public void moking() {
		
		ArrayList arrayListMock = mock(ArrayList.class);
		
		arrayListMock.get(0); // null
		arrayListMock.size(); // 0
		
		arrayListMock.add("Test");
		arrayListMock.add("Test");
		
		// even if we added two elements the size will return 0, as 
		// with mockito we are just mocking the values
		arrayListMock.size(); // 0
		
		// here we are specifying the values
		when(arrayListMock.size()).thenReturn(5);
		arrayListMock.size(); // 5
	
	}
	
	@Test
	public void spying() {
		
		/*
		 * A spy by default retains behavior (code) of the original class,
		 * Scenarios where we don't want to mock the behavior.
		 * We use spying for knowing the real working
		 */
		
		ArrayList arryListSpy = spy(ArrayList.class);
		
		//arryListSpy.get(0); // this will throw an exception
		
		arryListSpy.add("Test0");
		arryListSpy.get(0); // Test0
		
		arryListSpy.size(); // 1
		
		arryListSpy.add("Test");
		arryListSpy.add("Test2");
		
		arryListSpy.size(); // 3
		
		/*
		 * here after specifying the the size, from now onwards
		 * the arrayListSpy will always return 5, even if we 
		 * add another element
		 */
		when(arryListSpy.size()).thenReturn(5);
		
		arryListSpy.add("Test3");

		arryListSpy.size(); // 5
		
	}

}
















