package org.nachc.cad.cosmos.util.databricks;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;
import org.nachc.cad.cosmos.util.databricks.consts.DatabricksTestFiles;

import com.nach.core.util.file.FileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabricksUtilPutIntegrationTest {

	@Test
	public void shouldPutFileOnServer() throws Exception {
		log.info("Starting test...");
		String filePath = DatabricksTestFiles.HELLO_WORLD_PATH;
		String fileName = "/src/test/resources/csv/dummy/hello-world.txt";
		File file = FileUtil.getFromProjectRoot(fileName);
		log.info("Got file: " + file.getCanonicalPath());
		log.info("Doing delete");
		DatabricksUtil.delete(filePath);
		DatabricksUtil.put(filePath, file);
		DatabricksResponse resp = DatabricksUtil.exists(filePath);
		log.info("Response \n" + resp.getResponse());
		log.info("Status: " + resp.getStatusCode());
		log.info("Success: " + resp.isSuccess());
		assertTrue(resp.isSuccess() == true);
		log.info("Done.");
	}

}
