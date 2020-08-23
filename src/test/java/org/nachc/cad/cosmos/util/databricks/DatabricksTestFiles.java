package org.nachc.cad.cosmos.util.databricks;

import java.io.File;

import com.nach.core.util.file.FileUtil;

public class DatabricksTestFiles {

	public static final String HELLO_WORLD_PATH = "/FileStore/tables/test/integration-testing/basic/hello-world.txt";
	
	public static File getTestFile() {
		String fileName = "/src/test/resources/csv/dummy/hello-world.txt";
		File file = FileUtil.getFromProjectRoot(fileName);
		return file;
	}
	
}
