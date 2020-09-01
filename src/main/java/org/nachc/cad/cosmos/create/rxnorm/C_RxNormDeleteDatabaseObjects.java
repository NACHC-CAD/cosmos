package org.nachc.cad.cosmos.create.rxnorm;

import java.sql.Connection;

import org.nachc.cad.cosmos.create.datadictionary.A_DataDictionaryParameters;
import org.nachc.cad.cosmos.util.databricks.DatabricksDbUtil;
import org.yaorma.database.Database;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class C_RxNormDeleteDatabaseObjects {

	public static void delete() {
		Connection conn = null;
		try {
			log.info("Getting connection...");
			conn = DatabricksDbUtil.getConnection();
			String schemaName =  A_RxNormParameters.SCHEMA_NAME;
			log.info("Dropping database: " + schemaName);
			DatabricksDbUtil.dropDatabase(schemaName, conn);
			log.info("Done dropping: " + schemaName);
		} finally {
			Database.close(conn);
		}

	}
}
