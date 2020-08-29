package org.nachc.cad.cosmos.create.valueset;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Z_CreateValueSetSchema {

	public static void main(String[] args) {
		log.info("=============================================================");
		log.info("* * * CREATING SCHEMA FOR VALUE_SET * * *");
		A_PametersForValueSetSchema.logParameters();
		B_DeleteValueSetParsedFiles.deleteFiles();
		C_ParseValueSetFiles.parse();
		log.info("* * * DONE CREATING SCHEMA FOR VALUE_SET * * *");
		log.info("=============================================================");
		log.info("Done.");
	}
	
}
