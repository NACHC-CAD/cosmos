package org.nachc.cad.cosmos.create.datadictionary;

import java.sql.Connection;

import org.nachc.cad.cosmos.util.databricks.DatabricksDbUtil;
import org.yaorma.database.Database;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class H_DataDictionaryCreateDatabaseObjects {

	public static void main(String[] args) {
		create();
	}
	
	public static void create() {
		Connection conn = null;
		try {
			// get connection
			log.info("Getting connection...");
			conn = DatabricksDbUtil.getConnection();
			log.info("Got connection");
			String schemaName = A_DataDictionaryParameters.DATABRICKS_SCHEMA_NAME;
			String path; 
			String tableName;
			// create value_set table
			tableName = "data_dictionary";
			path = A_DataDictionaryParameters.DATABRICKS_DIR_NAME;
			log.info("Dropping table (" + schemaName + "." + tableName + ") for " + path);
			DatabricksDbUtil.dropTable(schemaName, tableName, conn);
			log.info("Creating table (" + schemaName + "." + tableName + ") for " + path);
			DatabricksDbUtil.createCsvTableForDir(path, schemaName, tableName, conn);
			log.info("Done creating database objects.");
			log.info("Done creating database objects.");
		} finally {
			Database.close(conn);
		}		
	}
	
}
