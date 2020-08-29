package org.nachc.cad.cosmos.util.databricks.consts;

import java.io.File;

import com.nach.core.util.file.FileUtil;

public class DatabricksTestFiles {

	public static final String TEST_FILES_PATH = "/FileStore/tables/test/integration-testing";
	
	public static final String DEMO_FILES_PATH = TEST_FILES_PATH + "/demo";
	
	public static final String PROC_FILES_PATH = TEST_FILES_PATH + "/proc";
	
	public static File getTestFile() {
		String fileName = "/src/test/resources/csv/dummy/hello-world.txt";
		File file = FileUtil.getFromProjectRoot(fileName);
		return file;
	}
	
}
