package org.nachc.cad.cosmos.create.datadictionary;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Z_CreateValueSetSchema {

	public static void main(String[] args) {
		log.info("=============================================================");
		log.info("* * * CREATING SCHEMA FOR DATA_DICTIONARY * * *");
		A_DataDictionaryParameters.logParameters();
		B_DataDictionaryDeleteParsedFiles.deleteFiles();
		C_DataDictionaryDeleteDatabricksFiles.deleteFiles();
		D_DataDictionaryDeleteDatabaseObjects.delete();
		E_DataDictionaryParseExcelToCsv.parse();
		F_DataDictionaryPostToDatabricks.postCsvFiles();
		G_DataDictionaryCreateSchema.create();
		H_DataDictionaryCreateDatabaseObjects.create();
		log.info("* * * DONE CREATING SCHEMA FOR DATA_DICTIONARY * * *");
		log.info("=============================================================");
		log.info("Done.");
	}
	
}
