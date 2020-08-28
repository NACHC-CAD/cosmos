package org.nachc.cad.cosmos.database.drop;

import java.sql.Connection;

import org.nachc.cad.cosmos.util.databricks.DatabricksDbUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DropDatabase {

	public static void main(String[] args) throws Exception {
		log.info("Starting drop database...");
		Connection conn = null;
		try {
			log.info("Getting connection");
			conn = DatabricksDbUtil.getConnection();
			log.info("Doing drop");
			drop(conn);
			log.info("Done.");
		} finally {
			if(conn != null) {
				conn.close();
			}
		}
	}
	
	public static void drop(Connection conn) {
		log.info("Droping databases...");
		DatabricksDbUtil.dropDatabase("cosmos_meta", conn);
		log.info("Done with drop.");
	}
	
}
