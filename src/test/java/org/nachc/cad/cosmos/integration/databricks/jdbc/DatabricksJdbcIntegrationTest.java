package org.nachc.cad.cosmos.integration.databricks.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;
import org.nachc.cad.cosmos.util.databricks.DatabricksDbUtil;
import org.nachc.cad.cosmos.util.databricks.auth.DatabricksAuthUtil;
import org.yaorma.database.Database;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabricksJdbcIntegrationTest {

	@Test
	public void shouldGetConnecton() throws Exception {
		log.info("Getting connection...");
		// get the connection parameters
		String url = "jdbc:spark://dbc-d8b275e5-4908.cloud.databricks.com:443/default;transportMode=http;ssl=1;httpPath=sql/protocolv1/o/6721863645546406/0822-135801-cadre203;AuthMech=3;UID=token;PWD=";
		String token = DatabricksAuthUtil.getToken();
		String schemaName = "integration_test";
		url = url + token;
		// get the connection
		Connection conn = DriverManager.getConnection(url);
		log.info("Got connection");
		// drop the schema if it exists
		log.info("Dropping database");
		DatabricksDbUtil.dropDatabase(schemaName, conn);
		// create the schema
		log.info("Creating database");
		Database.update("create database " + schemaName, conn);
		log.info("Done.");
	}
	
}
