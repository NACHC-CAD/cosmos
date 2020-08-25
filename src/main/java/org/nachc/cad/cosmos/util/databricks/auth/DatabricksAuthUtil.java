package org.nachc.cad.cosmos.util.databricks.auth;

import java.util.Properties;

import com.nach.core.util.props.PropertiesUtil;

public class DatabricksAuthUtil {

	private static final Properties PROPS = PropertiesUtil.getAsProperties("/auth/auth.properties");
	
	public static String getApiUrl() {
		return PROPS.getProperty("databricks-api-url");
	}
	
	public static String getToken() {
		return PROPS.getProperty("databricks-bearer-token");
	}
	
	public static String getTestFilesDir() {
		return PROPS.getProperty("test-files-dir");
	}

}
