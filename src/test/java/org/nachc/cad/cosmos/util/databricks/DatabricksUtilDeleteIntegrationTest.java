package org.nachc.cad.cosmos.util.databricks;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabricksUtilDeleteIntegrationTest {

	@Test
	public void shouldDeleteFile() {
		log.info("Starting test...");
		String filePath = DatabricksTestFiles.HELLO_WORLD_PATH;
		String response = DatabricksUtil.delete(filePath);
		log.info("Deleted file: \n" + response);
		log.info("Done.");
	}
	
}
