package org.nachc.cad.cosmos.util.databricks.stress;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;
import org.nachc.cad.cosmos.util.databricks.DatabricksFileUtil;
import org.nachc.cad.cosmos.util.databricks.consts.DatabricksTestFiles;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabricksUtilPutLarge {

	@Test
	public void shouldPostFile() throws Exception {
		log.info("Starting test...");
		// get the path and the file
		String filePath = DatabricksTestFiles.TEST_FILES_PATH + "/large/OchinEncounter.csv";
		File file = new File("C:\\test\\ochin\\OchinEncounter.csv");
		log.info("Got file: " + file.getCanonicalPath());
		assertTrue(file.exists());
		// delete the existing
		DatabricksFileUtil.delete(filePath);
		// write the file
		log.info("Writing file...");
		String response = DatabricksFileUtil.putZip(filePath, file);
		log.info("Got response: \n" + response);
		log.info("Done.");
	}

}
