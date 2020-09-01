package org.nachc.cad.cosmos.create.snomed;

import java.io.File;
import java.sql.Connection;

import org.nachc.cad.cosmos.util.databricks.DatabricksDbUtil;
import org.nachc.cad.cosmos.util.databricks.DatabricksFileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Z_SnomedBuild {

	public static void main(String[] args) {
		Connection conn = DatabricksDbUtil.getConnection();
		try {
			log.info("Starting upload");
			String csvDirName = "C:\\_WORKSPACES\\nachc\\terminology\\snomed\\SnomedCT_USEditionRF2_PRODUCTION_20200301T120000Z\\Full\\Terminology";
			File csvDir = new File(csvDirName);
			String snomedDatabricksDir = "/FileStore/tables/prod/global/terminololgy/snomed";
			String schemaName = "snomed";
			/*
			// delete files
			log.info("Deleting existing files...");
			DatabricksFileUtil.rmdir(snomedDatabricksDir);
			// drop schema
			log.info("Deleteing schema...");
			DatabricksDbUtil.dropDatabase(schemaName, conn);
			// add files
			log.info("Adding CONSO...");
			DatabricksFileUtil.put(snomedDatabricksDir + "/conso", csvDir, "sct2_Concept*.txt");
			// create schema
			log.info("Creating schema...");
			DatabricksDbUtil.createDatabase(schemaName, conn);
			// create database objects
			*/
			log.info("Creating tables...");
			log.info("CONSO");
			DatabricksDbUtil.createCsvTableForDir(snomedDatabricksDir + "/conso", schemaName, "conso", "\t", conn);
			log.info("Done.");
		} finally {
			DatabricksDbUtil.close(conn);
		}
	}
	

}
