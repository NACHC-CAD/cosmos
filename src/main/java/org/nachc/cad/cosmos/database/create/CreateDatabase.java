package org.nachc.cad.cosmos.database.create;

import java.io.InputStream;
import java.sql.Connection;

import org.nachc.cad.cosmos.database.drop.DropDatabase;
import org.nachc.cad.cosmos.util.databricks.DatabricksDbUtil;
import org.yaorma.database.Database;

import com.nach.core.util.file.FileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateDatabase {

	public static void main(String[] args) throws Exception {
		Connection conn = null;
		try {
			log.info("* * * GETTING CONNECTION * * *");
			conn = DatabricksDbUtil.getConnection();
			log.info("* * * DOING DROP * * *");
			DropDatabase.drop(conn);
			log.info("* * * DOING CREATE * * *");
			CreateDatabase.create(conn);
			log.info("Done.");
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	public static void create(Connection conn) {
		InputStream in = FileUtil.getInputStream("/org/nachc/cad/cosmos/sql/databricks/000-create-database.sql");
		Database.executeSqlScript(in, conn);
	}

}
