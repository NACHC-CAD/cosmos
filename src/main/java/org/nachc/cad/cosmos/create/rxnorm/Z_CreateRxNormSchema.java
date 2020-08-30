package org.nachc.cad.cosmos.create.rxnorm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Z_CreateRxNormSchema {

	public static void main(String[] args) {
		log.info("=============================================================");
		log.info("* * * CREATING SCHEMA FOR RXNORM * * *");
		A_ParametersForRxNorm.log();
		log.info("Deleting databricks files");
		B_DeleteRxNormDatabricksFiles.delete();
		log.info("Adding files to databricks");
		C_PostRxNormFilesToDatabricks.post();
		log.info("* * * DONE CREATING SCHEMA FOR RXNORM * * *");
		log.info("=============================================================");
		log.info("Done.");
	}

}
