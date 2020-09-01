package org.nachc.cad.cosmos.create.datadictionary;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class A_DataDictionaryParameters {

	public static final String EXCEL_FILE_LOCATION = "C:\\_WORKSPACES\\nachc\\data-dictionary\\covid\\excel\\2020-08-19 COVID 19- Data Dictionary.xlsx";
	
	public static final String CSV_FILE_LOCATION_FOR_DD = "C:\\_WORKSPACES\\nachc\\data-dictionary\\covid\\csv\\data-dictionary";
	
	public static final String DATABRICKS_DIR_NAME = "/FileStore/tables/prod/global/data_dictionary";
	
	public static final String DATABRICKS_SCHEMA_NAME = "data_dictionary";
	
	public static void logParameters() {
		log.info("-------------------------------------------------------------");
		log.info("Parameters: ");
		log.info("EXCEL_FILE_LOCATION      " + EXCEL_FILE_LOCATION);
		log.info("CSV_FILE_LOCATION_FOR_DD " + CSV_FILE_LOCATION_FOR_DD);
		log.info("DATABRICKS_DIR_NAME      " + DATABRICKS_DIR_NAME);
		log.info("DATABRICKS_SCHEMA_NAME   " + DATABRICKS_SCHEMA_NAME);
		log.info("End parameters. ");
		log.info("-------------------------------------------------------------");
	}

}
