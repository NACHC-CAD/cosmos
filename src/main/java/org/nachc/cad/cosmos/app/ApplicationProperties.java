package org.nachc.cad.cosmos.app;

import java.util.Properties;

import com.nach.core.util.props.PropertiesUtil;

public class ApplicationProperties {

	private static final Properties PROPS = PropertiesUtil.getAsProperties("/application.properties");
	
	public static String getRxNormRrfDir() {
		return PROPS.getProperty("rxnorm-rrf-dir");
	}

	public static String getRxNormDatabricksDir() {
		return PROPS.getProperty("rxnorm-databricks-dir");
	}
	
}
