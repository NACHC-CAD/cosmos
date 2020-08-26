package org.nachc.cad.cosmos.integration.databricks.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;
import org.nachc.cad.cosmos.util.databricks.auth.DatabricksAuthUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabricksJdbcIntegrationTest {

	@Test
	public void shouldGetConnecton() throws Exception {
		log.info("Getting connection...");
		String url = "jdbc:spark://dbc-d8b275e5-4908.cloud.databricks.com:443/default;transportMode=http;ssl=1;httpPath=sql/protocolv1/o/6721863645546406/0822-135801-cadre203;AuthMech=3;UID=token;PWD=";
		String token = DatabricksAuthUtil.getToken();
		url = url + token;
		Connection conn = DriverManager.getConnection(url);
		log.info("Done.");
	}
	
}
