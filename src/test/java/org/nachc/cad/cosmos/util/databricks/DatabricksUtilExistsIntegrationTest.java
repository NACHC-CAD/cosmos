package org.nachc.cad.cosmos.util.databricks;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabricksUtilExistsIntegrationTest {

	@Test
	public void shouldGetStatus() {
		boolean exists;
		File file = DatabricksTestFiles.getTestFile();
		String filePath = DatabricksTestFiles.HELLO_WORLD_PATH;
		// delete the existing file and assert that it is gone
		DatabricksUtil.delete(filePath);
		exists = DatabricksUtil.exists(filePath);
		assertTrue(exists == false);
		log.info(exists + "\t" + filePath);
		// add the file and assert that it exists
		DatabricksUtil.put(filePath, file);
		exists = DatabricksUtil.exists(filePath);
		assertTrue(exists == false);
		log.info(exists + "\t" + filePath);
		// done
		log.info("Done.");
	}
	
}
