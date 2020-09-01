package org.nachc.cad.cosmos.create.rxnorm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Z_RxNormBuild {

	public static void main(String[] args) {
		log.info("=============================================================");
		log.info("* * * CREATING SCHEMA FOR RXNORM * * *");
		A_RxNormParameters.log();
		log.info("Deleting databricks files");
		B_RxNormDeleteDatabricksFiles.delete();
		log.info("Adding files to databricks");
		D_RxNormPostFilesToDatabricks.post();
		log.info("* * * DONE CREATING SCHEMA FOR RXNORM * * *");
		log.info("=============================================================");
		log.info("Done.");
	}

}
