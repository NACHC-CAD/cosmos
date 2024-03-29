package org.nachc.cad.cosmos.util.databricks;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;

import org.nachc.cad.cosmos.util.databricks.auth.DatabricksAuthUtil;
import org.yaorma.database.Data;
import org.yaorma.database.Database;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabricksDbUtil {

	//
	// connection
	//

	public static Connection getConnection() {
		try {
			String url = DatabricksAuthUtil.getJdbcUrl();
			Connection conn = DriverManager.getConnection(url);
			return conn;
		} catch (Exception exp) {
			throw new RuntimeException(exp);
		}
	}

	//
	// database methods
	//

	/**
	 * 
	 * Does a database exist.
	 * 
	 */
	public static boolean databaseExists(String schemaName, Connection conn) {
		Data data = showSchemas(conn);
		for (Map<String, String> row : data) {
			String namespace = row.get("namespace");
			if (schemaName.equalsIgnoreCase(namespace)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * Get a list of the existing databases.
	 * 
	 */
	public static Data showSchemas(Connection conn) {
		String sqlString = "show schemas";
		Data rtn = Database.query(sqlString, conn);
		return rtn;
	}

	/**
	 * 
	 * Drop a database.
	 * 
	 */
	public static void dropDatabase(String schemaName, Connection conn) {
		if (databaseExists(schemaName, conn) == true) {
			Data data = showTables(schemaName, conn);
			for (Map<String, String> row : data) {
				String tableName = row.get("tablename");
				dropTable(schemaName, tableName, conn);
			}
			String sqlString = "drop database if exists " + schemaName;
			Database.update(sqlString, conn);
		}
	}

	/**
	 * 
	 * Create a database.
	 * 
	 */
	public static void createDatabase(String databaseName, Connection conn) {
		String sqlString = "create database " + databaseName;
		Database.update(sqlString, conn);
	}

	//
	// table methods
	//

	/**
	 * 
	 * Get a list of tables for a given schema.
	 * 
	 */
	public static Data showTables(String schemaName, Connection conn) {
		String sqlString;
		sqlString = "show tables in " + schemaName;
		Data rtn = Database.query(sqlString, conn);
		return rtn;
	}

	/**
	 * 
	 * Drop a table.
	 * 
	 */
	public static void dropTable(String schemaName, String tableName, Connection conn) {
		String sqlString = "drop table if exists " + schemaName + "." + tableName;
		Database.update(sqlString, conn);
	}

	public static void createCsvTableForDir(String databricksPath, String schemaName, String tableName, Connection conn) {
		createCsvTableForDir(databricksPath, schemaName, tableName, ",", conn);
	}

	public static void createCsvTableForDir(String databricksPath, String schemaName, String tableName, String delim, Connection conn) {
		log.info("Dropping if exists...");
		dropTable(schemaName, tableName, conn);
		String sqlString = "\n";
		sqlString += "create table " + schemaName + "." + tableName + " \n";
		sqlString += "using csv \n";
		sqlString += "options ( \n";
		sqlString += "  header = \"true\", \n";
		if (delim == null) {
			sqlString += "  inferDelimiter = \"true\", \n";
		} else {
			sqlString += "  delimiter = \"" + delim + "\", \n";
		}
		sqlString += "  inferSchema = \"false\", \n";
		sqlString += "  path = \"" + databricksPath + "\" \n";
		sqlString += ") \n";
		log.info("\n" + sqlString);
		Database.update(sqlString, conn);
		refreshTable(schemaName, tableName, conn);
	}

	public static void refreshTable(String schemaName, String tableName, Connection conn) {
		String sqlString = "refresh table " + schemaName + "." + tableName;
		log.info("Refreshing table: " + sqlString);
		Database.update(sqlString, conn);
	}

	public static void close(Connection conn) {
		Database.close(conn);
	}

}
