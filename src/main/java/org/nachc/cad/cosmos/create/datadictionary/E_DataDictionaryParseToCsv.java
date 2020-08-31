package org.nachc.cad.cosmos.create.datadictionary;

import java.io.File;

import org.nachc.cad.cosmos.util.datadictionary.covid.CovidDataDictionaryParser;

import com.nach.core.util.file.FileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class E_DataDictionaryParseToCsv {

	public static void main(String[] args) {
		parse();
	}
	
	public static void parse() {
		log.info("Starting parse...");
		File excelFile = new File(A_DataDictionaryParameters.EXCEL_FILE_LOCATION);
		File csvDir = new File(A_DataDictionaryParameters.CSV_FILE_LOCATION_FOR_DD);
		File csvFile = new File(csvDir, excelFile.getName() + ".csv");
		log.info("Doing parse for: " + FileUtil.getCanonicalPath(excelFile));
		CovidDataDictionaryParser.parse(excelFile, csvFile, csvFile.getName());
		log.info("Done");
	}
	
}
