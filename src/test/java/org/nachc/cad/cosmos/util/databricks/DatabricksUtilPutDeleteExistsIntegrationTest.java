package org.nachc.cad.cosmos.util.databricks;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;
import org.nachc.cad.cosmos.util.databricks.consts.DatabricksTestFiles;

import com.nach.core.util.file.FileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabricksUtilPutDeleteExistsIntegrationTest {

	@Test
	public void shouldPutFileOnServer() throws Exception {
		log.info("Starting test...");
		// get the file
		String fileName = "/src/test/resources/csv/proc/ochinproc.csv";
		File file = FileUtil.getFromProjectRoot(fileName);
		log.info("Got file: " + file.getCanonicalPath());
		// get the databricks target location
		String dirPath = DatabricksTestFiles.PROC_FILES_PATH;
		DatabricksFileUtilResponse resp;
		// delete the file if it is there
		log.info("Doing delete");
		resp = DatabricksFileUtil.rmdir(dirPath);
		log.info("Success: " + resp.isSuccess() + "\t" + resp.getStatusCode() + "\t" + resp.getResponse());
		assertTrue(resp.isSuccess() == true);
		// check to see the file is there
		log.info("Doing exists");
		resp = DatabricksFileUtil.exists(dirPath);
		log.info("Success: " + resp.isSuccess() + "\t" + resp.getStatusCode() + "\t" + resp.getResponse());
		assertTrue(resp.isSuccess() == true && resp.isFileExists() == false);
		// add the file
		log.info("Adding file");
		resp = DatabricksFileUtil.put(dirPath, file);
		log.info("Success: " + resp.isSuccess() + "\t" + resp.getStatusCode() + "\t(" + resp.getFileSize() + "M in " + resp.getElapsedSeconds() + " sec)\t" + resp.getResponse());
		// check to see the file is there
		log.info("Doing exists");
		resp = DatabricksFileUtil.exists(dirPath);
		log.info("Success: " + resp.isSuccess() + "\t" + resp.getStatusCode() + "\t" + resp.getResponse());
		assertTrue(resp.isSuccess() == true && resp.isFileExists() == true);
		log.info("Done.");
	}

}
