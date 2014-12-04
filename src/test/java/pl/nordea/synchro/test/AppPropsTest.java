package pl.nordea.synchro.test;

import java.util.Properties;

import junit.framework.Assert;

import org.junit.Test;

import pl.nordea.synchro.utils.AppProps;

public class AppPropsTest {
	
	@Test
	public void testParsingValuesFromStrings() {
		//G
		AppProps props= new AppProps();
		Properties properties= new Properties();
				
		//W
		properties.put("mail", "true");
		boolean testValue=props.isChangeEmailModuleActive(properties);
		String trueValue="true";
	
		
		//T
	
		Assert.assertTrue(Boolean.valueOf(trueValue));
		Assert.assertEquals(true, testValue);
	}
}
