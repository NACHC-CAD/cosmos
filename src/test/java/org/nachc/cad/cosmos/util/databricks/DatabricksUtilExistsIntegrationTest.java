package org.nachc.cad.cosmos.util.databricks;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;
import org.nachc.cad.cosmos.util.databricks.consts.DatabricksTestFiles;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabricksUtilExistsIntegrationTest {

	@Test
	public void shouldGetStatus() {
		// get the test files
		File file = DatabricksTestFiles.getTestFile();
		String filePath = DatabricksTestFiles.HELLO_WORLD_PATH;
		// delete the existing file and assert that it is gone
		log.info("Doing delete");
		DatabricksUtil.delete(filePath);
		DatabricksResponse resp = DatabricksUtil.exists(filePath);
		log.info("Response \n" + resp.getResponse());
		log.info("Status: " + resp.getStatusCode());
		log.info("Success: " + resp.isSuccess());
		assertTrue(resp.isSuccess() == false);
		// add the file and assert that it exists
		log.info("Doing put");
		DatabricksUtil.put(filePath, file);
		resp = DatabricksUtil.exists(filePath);
		log.info("Response \n" + resp.getResponse());
		log.info("Status: " + resp.getStatusCode());
		log.info("Success: " + resp.isSuccess());
		assertTrue(resp.isSuccess() == true);
		// done
		log.info("Done.");
	}
	
}
