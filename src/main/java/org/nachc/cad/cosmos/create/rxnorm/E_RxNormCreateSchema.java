package org.nachc.cad.cosmos.create.rxnorm;

import java.sql.Connection;

import org.nachc.cad.cosmos.create.valueset.A_ParametersForValueSetSchema;
import org.nachc.cad.cosmos.util.databricks.DatabricksDbUtil;
import org.yaorma.database.Database;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class E_RxNormCreateSchema {

	public static void main(String[] args) {
		create();
	}
	
	public static void create() {
		Connection conn = null;
		try {
			log.info("Getting connection...");
			conn = DatabricksDbUtil.getConnection();
			String schemaName = A_RxNormParameters.SCHEMA_NAME;
			log.info("Dropping schema: " + schemaName);
			DatabricksDbUtil.dropDatabase(schemaName, conn);
			log.info("Creating schema: " + schemaName);
			DatabricksDbUtil.createDatabase(schemaName, conn);
			log.info("Done creating schema: " + schemaName);
		} finally {
			Database.close(conn);
		}
	}

}
