package org.nachc.cad.cosmos.create.datadictionary;

import org.nachc.cad.cosmos.util.databricks.DatabricksFileUtil;
import org.nachc.cad.cosmos.util.databricks.DatabricksFileUtilResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class C_DataDictionaryDeleteDatabricksFiles {

	public static void main(String[] args) {
		deleteFiles();
	}
	
	public static void deleteFiles() {
		String dirName;
		DatabricksFileUtilResponse resp;
		dirName = A_DataDictionaryParameters.DATABRICKS_DIR_NAME;
		log.info("Deleting from: " + dirName);
		resp = DatabricksFileUtil.rmdir(dirName);
		log.info(resp.isSuccess() + ": (" + resp.getStatusCode() + ") " + resp.getDatabricksFilePath() + "\t" + resp.getResponse());
		log.info("Done with delete");
	}

}
