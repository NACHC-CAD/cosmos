package org.nachc.cad.cosmos.util.databricks;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.nachc.cad.cosmos.util.databricks.consts.DatabricksTestFiles;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabricksUtilDeleteIntegrationTest {

	@Test
	public void shouldDeleteFile() {
		log.info("Starting test...");
		String filePath = DatabricksTestFiles.HELLO_WORLD_PATH;
		log.info("Doing delete...");
		DatabricksFileUtilResponse response = DatabricksFileUtil.delete(filePath);
		log.info("Response: " + response.getResponse().trim());
		log.info("Status: " + response.getStatusCode());
		log.info("Success: " + response.isSuccess());
		assertTrue(response.isSuccess() == true);
		log.info("Done.");
	}
	
}
