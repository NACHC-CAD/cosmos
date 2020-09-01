package org.nachc.cad.cosmos.util.databricks.auth;

import java.util.Properties;

import com.nach.core.util.props.PropertiesUtil;

public class DatabricksAuthUtil {

	private static final Properties PROPS = PropertiesUtil.getAsProperties("/auth/auth.properties");
	
	public static String getVsacToken() {
		return PROPS.getProperty("vsac-token");
	}
	
	public static String getApiUrl() {
		return PROPS.getProperty("databricks-api-url");
	}
	
	public static String getToken() {
		return PROPS.getProperty("databricks-bearer-token");
	}
	
	public static String getJdbcUrl() {
		String url = PROPS.getProperty("databricks-jdbc-url");
		String token = DatabricksAuthUtil.getToken();
		url = url + "PWD=" + token;
		return url;
	}
	
	public static String getTestFilesDir() {
		return PROPS.getProperty("test-files-dir");
	}

}
