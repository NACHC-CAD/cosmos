package org.nachc.cad.cosmos.create.rxnorm;

import java.io.File;

import org.nachc.cad.cosmos.util.databricks.DatabricksFileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class B_DeleteRxNormDatabricksFiles {

	public static void delete() {
		String dirName = A_ParametersForRxNorm.RX_NORM_DATABRICKS_DIR;
		log.info("Deleting from: " + dirName);
		DatabricksFileUtil.delete(dirName);
		log.info("Done with delete");
	}
	
}
