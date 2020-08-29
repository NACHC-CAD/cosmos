package org.nachc.cad.cosmos.util.databricks;

import org.junit.Test;
import org.nachc.cad.cosmos.util.databricks.consts.DatabricksTestFiles;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeleteTestFilesIntegrationTestSetup {

	@Test
	public void shouldDeleteTestFilesDir() {
		log.info("-------------------------------------------------------------");
		log.info("* * * DELETEING TEST FILE PATH * * *");
		DatabricksFileUtil.rmdir(DatabricksTestFiles.TEST_FILES_PATH);
		log.info("Testing that file has been deleted");
		DatabricksFileUtilResponse resp = DatabricksFileUtil.exists(DatabricksTestFiles.TEST_FILES_PATH);
		log.info("File deleted: " + resp.isSuccess() + "\t" + resp.getDatabricksFilePath() + "\n" + resp.getResponse());
		log.info("Done.");
		log.info("-------------------------------------------------------------");
	}
	
}
