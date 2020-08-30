package org.nachc.cad.cosmos.create.rxnorm;

import java.io.File;

import org.nachc.cad.cosmos.util.databricks.DatabricksFileUtil;
import org.nachc.cad.cosmos.util.databricks.DatabricksFileUtilResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class C_PostRxNormFilesToDatabricks {

	public static void post() {
		log.info("Starting upload to databricks");
		String databricksDir = A_ParametersForRxNorm.RX_NORM_DATABRICKS_DIR;
		String rrfDir = A_ParametersForRxNorm.RX_NORM_DIR;
		DatabricksFileUtilResponse resp;
		// upload conso
		File consoFile = new File(rrfDir, "RXNCONSO.RRF");
		String consoPath = databricksDir + "/conso";
		log.info("Uploading RXNORM CONSO: " + consoPath);
		resp = DatabricksFileUtil.put(consoPath, consoFile);
		log.info(resp.isSuccess() + ": (" + resp.getStatusCode() + ") " + resp.getDatabricksFilePath() + "\t" + resp.getResponse());
		// upload rel
		File relFile = new File(rrfDir, "RXNREL.RRF");
		String relPath = databricksDir + "/rel";
		log.info("Uploading RXNORM REL: " + relPath);
		resp = DatabricksFileUtil.put(relPath, relFile);
		log.info(resp.isSuccess() + ": (" + resp.getStatusCode() + ") " + resp.getDatabricksFilePath() + "\t" + resp.getResponse());
		// upload sat
		File satFile = new File(rrfDir, "RXNSAT.RRF");
		String satPath = databricksDir + "/sat";
		log.info("Uploading RXNORM SAT: " + satPath);
		resp = DatabricksFileUtil.put(satPath, satFile);
		log.info(resp.isSuccess() + ": (" + resp.getStatusCode() + ") " + resp.getDatabricksFilePath() + "\t" + resp.getResponse());
		log.info("Done with upload to databricks");
	}
	
}
