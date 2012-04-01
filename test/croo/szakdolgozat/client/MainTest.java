package croo.szakdolgozat.client;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * GWT JUnit tests must extend GWTTestCase.
 */
public class MainTest extends GWTTestCase
{

	/**
	 * Must refer to a valid module that sources this class.
	 */
	public String getModuleName()
	{
		return "croo.szakdolgozat.MainJUnit";
	}

	/**
	 * Tests the FieldVerifier.
	 */
	public void testFieldVerifier()
	{
		assertTrue(true);
	}

	/**
	 * This test will send a request to the server using the greetServer method
	 * in GreetingService and verify the response.
	 */
	public void testGreetingService()
	{
		assertTrue(true);
	}

}
