package org.nachc.cad.cosmos.util.databricks;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;

import org.nachc.cad.cosmos.util.databricks.auth.DatabricksAuthUtil;
import org.yaorma.database.Database;

public class DatabricksDbUtil {

	//
	// connection
	//
	
	public static Connection getConnection() {
		try {
			String url = DatabricksAuthUtil.getJdbcUrl();
			Connection conn = DriverManager.getConnection(url);
			return conn;
		} catch(Exception exp) {
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
		List<Map<String, String>> data = showSchemas(conn);
		for(Map<String, String> row : data) {
			String namespace = row.get("namespace");
			if(schemaName.equalsIgnoreCase(namespace)) {
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
	public static List<Map<String, String>> showSchemas(Connection conn) {
		String sqlString = "show schemas";
		List<Map<String, String>> rtn = Database.query(sqlString, conn);
		return rtn;
	}

	/**
	 * 
	 * Drop a database.  
	 * 
	 */
	public static void dropDatabase(String schemaName, Connection conn) {
		if (databaseExists(schemaName, conn) == true) {
			List<Map<String, String>> data = showTables(schemaName, conn);
			for (Map<String, String> row : data) {
				String tableName = row.get("tablename");
				dropTable(schemaName, tableName, conn);
			}
			String sqlString = "drop database if exists " + schemaName;
			Database.update(sqlString, conn);
		}
	}

	//
	// table methods
	//
	
	/**
	 * 
	 * Get a list of tables for a given schema.  
	 * 
	 */
	public static List<Map<String, String>> showTables(String schemaName, Connection conn) {
		String sqlString;
		sqlString = "show tables in " + schemaName;
		List<Map<String, String>> rtn = Database.query(sqlString, conn);
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

}
