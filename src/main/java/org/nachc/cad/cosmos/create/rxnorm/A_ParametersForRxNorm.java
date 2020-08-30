package org.nachc.cad.cosmos.create.rxnorm;

import org.nachc.cad.cosmos.app.ApplicationProperties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class A_ParametersForRxNorm {

	public static final String RX_NORM_DIR = ApplicationProperties.getRxNormRrfDir();
	
	public static final String RX_NORM_DATABRICKS_DIR = ApplicationProperties.getRxNormDatabricksDir();

	public static void log() {
		log.info("-------------------------------------------------------------");
		log.info("Parameters: ");
		log.info("RX_NORM_DIR            " + RX_NORM_DIR);
		log.info("RX_NORM_DATABRICKS_DIR " + RX_NORM_DATABRICKS_DIR);
		log.info("End parameters. ");
		log.info("-------------------------------------------------------------");
	}

}
