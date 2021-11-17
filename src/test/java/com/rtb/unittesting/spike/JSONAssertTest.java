package com.rtb.unittesting.spike;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class JSONAssertTest {

	String actualResponse = "{\"id\":1,\"name\":\"Ball\",\"price\":10,\"quantity\":100}";

	/*
	 * Here JSONAssert will look for exact match of the actualResponse and 
	 * expectedResponse as we have declared strict = true
	 */
	@Test
	public void jsonAssert_StringTrue_ExactMatchExceptForSpeaces() throws JSONException {

		String expectdResponse = "{\"id\": 1,\"name\":\"Ball\",\"price\":10,\"quantity\":100}";

		JSONAssert.assertEquals(expectdResponse, actualResponse, true);
	}

	/*
	 * Here JSONAssert will not look for exact match of the actualResponse and 
	 * expectedResponse as we have declared strict = false,
	 * here e can remove any element
	 */
	@Test
	public void jsonAssert_StrictFalse() throws JSONException {

		String expectdResponse = "{\"id\": 1,\"name\":\"Ball\",\"price\":10}";

		JSONAssert.assertEquals(expectdResponse, actualResponse, false);
	}
	
	/*
	 * If using JSONAssert we can also write our response without
	 * the escape characters, but note that, if a value contains spaces between it then
	 * then we will have to use the escape characters 
	 */
	@Test
	public void jsonAssert_WithoutEscapeCharacters() throws JSONException {
		
		String expectdResponse = "{id: 1,name:Ball,price:10}";
		
		JSONAssert.assertEquals(expectdResponse, actualResponse, false);
	}
}
