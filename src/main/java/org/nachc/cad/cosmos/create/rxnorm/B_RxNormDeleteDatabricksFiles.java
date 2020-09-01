package org.nachc.cad.cosmos.create.rxnorm;

import org.nachc.cad.cosmos.util.databricks.DatabricksFileUtil;
import org.nachc.cad.cosmos.util.databricks.DatabricksFileUtilResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class B_RxNormDeleteDatabricksFiles {

	public static void delete() {
		String dirName = A_RxNormParameters.DATABRICKS_DIR;
		log.info("Deleting from: " + dirName);
		DatabricksFileUtilResponse resp = DatabricksFileUtil.rmdir(dirName);
		log.info(resp.isSuccess() + ": (" + resp.getStatusCode() + ") " + resp.getDatabricksFilePath() + "\t" + resp.getResponse());
		log.info("Done with delete");
	}

}
