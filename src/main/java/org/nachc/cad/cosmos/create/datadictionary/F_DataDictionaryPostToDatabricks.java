package org.nachc.cad.cosmos.create.datadictionary;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.nachc.cad.cosmos.util.databricks.DatabricksFileUtil;
import org.nachc.cad.cosmos.util.databricks.DatabricksFileUtilResponse;

import com.nach.core.util.file.FileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class F_DataDictionaryPostToDatabricks {

	public static void main(String[] args) {
		postCsvFiles();
	}
	
	public static void postCsvFiles() {
		String databricksTargetDir = A_DataDictionaryParameters.DATABRICKS_DIR_NAME;
		File csvFilesDir = new File(A_DataDictionaryParameters.CSV_FILE_LOCATION_FOR_DD);
		List<File> files = FileUtil.listFiles(csvFilesDir, "*.csv");
		int cnt = 0;
		ArrayList<DatabricksFileUtilResponse> responseList = new ArrayList<DatabricksFileUtilResponse>();
		for (File file : files) {
			cnt++;
			log.info("\tFile " + cnt + " of " + files.size() + ": " + file.getName());
			log.info("\tTarget: " + databricksTargetDir);
			DatabricksFileUtilResponse resp = DatabricksFileUtil.put(databricksTargetDir, file);
			responseList.add(resp);
		}
		cnt = 0;
		for (DatabricksFileUtilResponse resp : responseList) {
			cnt++;
			log.info(cnt + " of " + responseList.size() + ":\t" + resp.isSuccess() + "\t" + resp.getStatusCode() + "\t" + resp.getResponse().trim());
		}
		log.info("Done.");
	}

}
