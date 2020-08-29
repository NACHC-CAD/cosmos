package org.nachc.cad.cosmos.util.databricks.testdata;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.nachc.cad.cosmos.util.databricks.DatabricksFileUtilResponse;
import org.nachc.cad.cosmos.util.databricks.DatabricksFileUtil;
import org.nachc.cad.cosmos.util.databricks.auth.DatabricksAuthUtil;

import com.nach.core.util.file.FileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoadTestData {

	private static final String DATABRICKS_FILE_PATH = "/FileStore/tables/test/demo-00";
	
	public static void main(String[] args) throws Exception {
		log.info("Starting uploads...");
		String dirName = DatabricksAuthUtil.getTestFilesDir();
		dirName = dirName + "\\_PROJECT\\projects";
		File dir = new File(dirName);
		deleteExistingProject();
		// uploadTables(dir, DATABRICKS_FILE_PATH, "million-hearts");
		uploadTables(dir, DATABRICKS_FILE_PATH, "womens-health");
		log.info("Done.");
	}

	private static void deleteExistingProject() {
		log.info("-------------------------------------------------------------------");
		log.info("* * * REMOVING DIRECTORY FROM DATABRICKS ENV * * *");
		log.info("Path: " + DATABRICKS_FILE_PATH);
		DatabricksFileUtilResponse resp = DatabricksFileUtil.rmdir(DATABRICKS_FILE_PATH);
		log.info(resp.getStatusCode() + "\t" + resp.getResponse());
		log.info("Done with delete.");
	}
	
	private static void uploadTables(File testFilesRoot, String target, String projectName) {
		log.info("-------------------------------------------------------------------");
		log.info("Starting project upload: " + projectName);
		ArrayList<DatabricksFileUtilResponse> responseList = new ArrayList<DatabricksFileUtilResponse>();
		testFilesRoot = new File(testFilesRoot, projectName);
		target = target + "/" + projectName;
		uploadFiles(testFilesRoot, target, "demo", responseList);
		uploadFiles(testFilesRoot, target, "dx", responseList);
		uploadFiles(testFilesRoot, target, "enc", responseList);
		uploadFiles(testFilesRoot, target, "obs", responseList);
		uploadFiles(testFilesRoot, target, "proc", responseList);
		uploadFiles(testFilesRoot, target, "rx", responseList);
		// log results
		String msg = "Results: \n\n";
		msg += "* ---------------------\n";
		msg += "* \n";
		msg += "* RESULTS\n";
		msg += "* \n";
		msg += "* ---------------------\n";
		msg += "\n";
		for(DatabricksFileUtilResponse response : responseList) {
			msg += response.isSuccess() + "\t" + response.getResponse().trim() + "\n";
		}
		msg += "\n\n\n";
		log.info(msg);
		log.info("Done.");
	}

	private static void uploadFiles(File src, String target, String folderName, List<DatabricksFileUtilResponse> results) {
		log.info("=========================");
		log.info("Processing directory: " + folderName);
		src = new File(src, folderName);
		target = target + "/" + folderName;
		log.info("src: " + src);
		log.info("dst: " + target);
		List<File> files = FileUtil.listFiles(src, new String[] {"*.csv"});
		for (File file : files) {
			String fileTarget = target + "/" + file.getName();
			log.info("* * * UPLOADING FILE * * *");
			log.info("\t" + file.getAbsolutePath());
			log.info("\t" + file.getName());
			log.info("\t" + fileTarget);
			log.info("\t" + "DOING UPLOAD...");
			DatabricksFileUtilResponse resp = DatabricksFileUtil.put(fileTarget, file);
			log.info("\t" + resp.getResponse().trim());
			log.info("\t" + resp.getStatusCode());
			log.info("\t" + resp.isSuccess());
			results.add(resp);
		}
	}

}
