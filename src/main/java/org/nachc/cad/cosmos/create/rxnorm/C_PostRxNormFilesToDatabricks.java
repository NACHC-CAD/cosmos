package org.nachc.cad.cosmos.create.rxnorm;

import java.io.File;

import org.nachc.cad.cosmos.util.databricks.DatabricksFileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class C_PostRxNormFilesToDatabricks {

	public static void post() {
		log.info("Starting upload to databricks");
		String databricksDir = A_ParametersForRxNorm.RX_NORM_DATABRICKS_DIR;
		String rrfDir = A_ParametersForRxNorm.RX_NORM_DIR;
		// upload conso
		log.info("Uploading RXNORM CONSO");
		File consoFile = new File(rrfDir, "RXNCONSO.RRF");
		String consoPath = databricksDir + "/conso";
		DatabricksFileUtil.put(consoPath, consoFile);
		// upload rel
		log.info("Uploading RXNORM REL");
		File relFile = new File(rrfDir, "RXNREL.RRF");
		String relPath = databricksDir + "/rel";
		DatabricksFileUtil.put(relPath, relFile);
		// upload sat
		log.info("Uploading RXNORM SAT");
		File satFile = new File(rrfDir, "RXNSAT.RRF");
		String satPath = databricksDir + "/sat";
		DatabricksFileUtil.put(satPath, satFile);
		log.info("Done with upload to databricks");
	}
	
}
