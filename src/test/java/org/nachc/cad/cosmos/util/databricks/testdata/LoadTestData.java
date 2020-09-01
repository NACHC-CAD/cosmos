package org.nachc.cad.cosmos.util.databricks.testdata;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.nachc.cad.cosmos.util.databricks.DatabricksDbUtil;
import org.nachc.cad.cosmos.util.databricks.DatabricksFileUtil;
import org.nachc.cad.cosmos.util.databricks.DatabricksFileUtilResponse;
import org.nachc.cad.cosmos.util.databricks.auth.DatabricksAuthUtil;
import org.yaorma.database.Database;

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
		uploadTables(dirName, DATABRICKS_FILE_PATH, "womens-health/oc");
		uploadTables(dirName, DATABRICKS_FILE_PATH, "womens-health/ac");
		createSchema();
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
	
	private static void uploadTables(String dirName, String target, String projectName) {
		log.info("-------------------------------------------------------------------");
		log.info("Starting project upload: " + projectName);
		File testFilesRoot = new File(dirName);
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

	private static void uploadFiles(File src, String dirPath, String folderName, List<DatabricksFileUtilResponse> results) {
		log.info("=========================");
		log.info("Processing directory: " + folderName);
		src = new File(src, folderName);
		dirPath = dirPath + "/" + folderName;
		log.info("src: " + src);
		log.info("dst: " + dirPath);
		List<File> files = FileUtil.listFiles(src, new String[] {"*.csv"});
		for (File file : files) {
			log.info("* * * UPLOADING FILE * * *");
			log.info("\t" + file.getAbsolutePath());
			log.info("\t" + file.getName());
			log.info("\t" + dirPath);
			log.info("\t" + "DOING UPLOAD...");
			DatabricksFileUtilResponse resp = DatabricksFileUtil.put(dirPath, file);
			log.info("\t" + resp.getResponse().trim());
			log.info("\t" + resp.getStatusCode());
			log.info("\t" + resp.isSuccess());
			results.add(resp);
		}
	}

	private static void createSchema() {
		log.info("Creating schema...");
		Connection conn = DatabricksDbUtil.getConnection();
		try {
			String schemaName = "demo_00";
			String fileRoot = DATABRICKS_FILE_PATH + "/womens-health";
			log.info("Dropping schema");
			DatabricksDbUtil.dropDatabase(schemaName, conn);
			log.info("Creating schema");
			DatabricksDbUtil.createDatabase(schemaName, conn);
			// create tables AC
			log.info("Creating AC-DEMO");
			DatabricksDbUtil.createCsvTableForDir(fileRoot + "/ac/demo", schemaName, "demo_ac", conn);
			log.info("Creating AC-DX");
			DatabricksDbUtil.createCsvTableForDir(fileRoot + "/ac/dx", schemaName, "dx_ac", conn);
			log.info("Creating AC-ENC");
			DatabricksDbUtil.createCsvTableForDir(fileRoot + "/ac/enc", schemaName, "enc_ac", conn);
			log.info("Creating AC-OBS");
			DatabricksDbUtil.createCsvTableForDir(fileRoot + "/ac/obs", schemaName, "obs_ac", conn);
			log.info("Creating AC-PROC");
			DatabricksDbUtil.createCsvTableForDir(fileRoot + "/ac/proc", schemaName, "proc_ac", conn);
			log.info("Creating AC-RX");
			DatabricksDbUtil.createCsvTableForDir(fileRoot + "/ac/rx", schemaName, "rx_ac", conn);
			// create tables OC
			log.info("Creating OC-DEMO");
			DatabricksDbUtil.createCsvTableForDir(fileRoot + "/oc/demo", schemaName, "demo_oc", conn);
			log.info("Creating OC-DX");
			DatabricksDbUtil.createCsvTableForDir(fileRoot + "/oc/dx", schemaName, "dx_oc", conn);
			log.info("Creating OC-ENC");
			DatabricksDbUtil.createCsvTableForDir(fileRoot + "/oc/enc", schemaName, "enc_oc", conn);
			log.info("Creating OC-OBS");
			DatabricksDbUtil.createCsvTableForDir(fileRoot + "/oc/obs", schemaName, "obs_oc", conn);
			log.info("Creating OC-PROC");
			DatabricksDbUtil.createCsvTableForDir(fileRoot + "/oc/proc", schemaName, "proc_oc", conn);
			log.info("Creating OC-RX");
			DatabricksDbUtil.createCsvTableForDir(fileRoot + "/oc/rx", schemaName, "rx_oc", conn);
		} finally {
			DatabricksDbUtil.close(conn);
		}
	}

}
