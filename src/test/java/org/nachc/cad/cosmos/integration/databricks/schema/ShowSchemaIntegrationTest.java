package org.nachc.cad.cosmos.integration.databricks.schema;

import java.sql.Connection;
import java.util.Map;

import org.junit.Test;
import org.nachc.cad.cosmos.util.databricks.DatabricksDbUtil;
import org.yaorma.database.Data;
import org.yaorma.database.Database;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ShowSchemaIntegrationTest {

	@Test
	public void shouldShowSchema() {
		log.info("Starting test...");
		Connection conn = DatabricksDbUtil.getConnection();
		log.info("Getting databases");
		String sqlString = "show databases";
		Data data = Database.query(sqlString, conn);
		String msg = "";
		msg += "\n\n";
		msg += "DATABASES: \n";
		msg += "---------- \n";
		for (Map<String, String> row : data) {
			String name = row.get("namespace");
			msg += "  " + name + "\n";
		}
		msg += "\n\n";
		msg += "TABLES: \n";
		msg += "------- \n";
		for (Map<String, String> row : data) {
			String schemaName = row.get("namespace");
			log.info("Getting tables for " + schemaName);
			boolean hasTables = false;
			Data tables = DatabricksDbUtil.showTables(schemaName, conn);
			msg += schemaName + "\n";
			for (Map<String, String> table : tables) {
				hasTables = true;
				msg += "  " + table.get("tablename") + "\n";
			}
			if (hasTables == false) {
				msg += "  (empty)\n";
			}
			msg += "\n";
		}
		log.info(msg);
		log.info("Done.");
	}

}
