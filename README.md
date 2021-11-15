# SpringBootRestAPITesting
Testing in spring boot rest API
---

## In this project we will learn how to do testing in spring boot using junit 5 and mockito.

### What is mockito?
Mockito lets you write beautiful tests with a clean & simple API. Mockito doesn’t give you hangover because the tests are very readable and they produce clean verification errors. 

---

## First let's get our hands dirty by writing some simple test cases

### STEP 1 : Make a business class

#### SomeBusinessImpl.java

```java
  
  package com.rtb.unittesting.business;
  
  public class SomeBusinessImpl {
  
   	  public int calculateSum(int[] data) {
		
		  int sum = 0;
				
		  for(int value : data) {
			  sum += value;
		  }
		
		  return sum;
	  }
  
  }
  
```

### STEP 2 :  Make test class for testing the buiseness implementation

#### SomeBusinessTest.java

```java

package com.rtb.unittesting.buiness;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.rtb.unittesting.business.SomeBusinessImpl;

class SomeBusinessTest {

	@Test
	public void calculateSum_basic() {
		
		SomeBusinessImpl business = new SomeBusinessImpl();
		int actualResult =  business.calculateSum(new int[] {1,2,3});
		int expectedResult = 6;
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void calculateSum_empty() {
		
		SomeBusinessImpl business = new SomeBusinessImpl();
		int actualResult =  business.calculateSum(new int[] {});
		int expectedResult = 0;
		assertEquals(expectedResult, actualResult);
	}
	
	@Test
	public void calculateSum_oneValue() {
		
		SomeBusinessImpl business = new SomeBusinessImpl();
		int actualResult =  business.calculateSum(new int[] {5});
		int expectedResult = 5;
		assertEquals(expectedResult, actualResult);
	}

}

```
---

## Now let's add a service layer on top of business layer

### STEP 1 : Make an interface

#### SomeDataService.java

```java

package com.rtb.unittesting.data;

public interface SomeDataService {

	int[] retriveAllData();

}

```

### STEP 2 : Modifying our buiseness class

#### SomeBusinessImpl.java

```java

package com.rtb.unittesting.business;

import com.rtb.unittesting.data.SomeDataService;

public class SomeBusinessImpl {

	private SomeDataService someDataService;
	
	public void setSomeDataService(SomeDataService someDataService) {
		this.someDataService = someDataService;
	}

	public int calculateSum(int[] data) {
		
		int sum = 0;
				
		for(int value : data) {
			sum += value;
		}
		
		return sum;
	}
	
	public int calculateSumUsingDataService() {
		
		int sum = 0;
		
		int[] data = someDataService.retriveAllData();
		
		for(int value : data) {
			sum += value;
		}
		
		return sum;
	}
	
}

```

### STEP 3 : Wrting test class - stub approach

#### SomeBusinessTestStubTest.java

```java

package com.rtb.unittesting.buiness;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.rtb.unittesting.business.SomeBusinessImpl;
import com.rtb.unittesting.data.SomeDataService;

// these are the problems with junit and therefore mokito comes into picture
// we always have to create different stubs for different scenarios, instead of
// this, using mokito we can programmatically create mock classes

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

```

### STEP 4 : Now let's see the mock approcah

#### SomeBusinessMockTest.java

```java

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

```

---

## Let's make another test class which uses List

### Here we will see in different scenarios how mockito can help us wrting the test

#### ListMockTest.java

```java

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
		// there are many any methods in mockito
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


```

---

## REST controller test

### Enough basics, now let's start testing our rest controller class

### STEP 1 : Make a contoller class

#### HelloWorldController.java

```java

package com.rtb.unittesting.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@GetMapping("/hello-world")
	public String helloWorld() {
		
		return "Hello World";
	}
}

```

#### HelloWorldControllerTest.java

```java

package com.rtb.unittesting.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

// here we will use MockMvcTest and will invoke only the HelloWorldController class
@WebMvcTest(HelloWorldController.class)
public class HelloWorldControllerTest {


	// Spring already have bean for MockMvc so we just need to use @Autowired and MockMvc  will be instantiated
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void helloWorld_basic() throws Exception {
		
		/*
		* Here we are making GET request with the endpoint /hello-world
		* And we are accepting the JSON response
		* After builiding a request we are performiing that request and returing the reponse to MvcResult
		* For getting the content as String we are calling the result.getReponse().getContentAsString() mehthod
		* After that we are verifying the result
		*/
		
		// call GET  "/hello-world"
		RequestBuilder request = MockMvcRequestBuilders.get("/hello-world")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(request).andReturn();
		
		String contentString = result.getResponse().getContentAsString();
		
		// verify "Hello World"
		assertEquals("Hello World", contentString);
	}
}

```
