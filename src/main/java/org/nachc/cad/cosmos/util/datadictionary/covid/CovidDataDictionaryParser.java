package org.nachc.cad.cosmos.util.datadictionary.covid;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.nach.core.util.excel.ExcelUtil;
import com.nach.core.util.file.FileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CovidDataDictionaryParser {

	public static void parse(File excel, File csvTarget, String dictionaryName) {
		// create the target workbook
		log.info("Creating csv workbook");
		Workbook csvBook = ExcelUtil.createNewWorkbook();
		Sheet csvSheet = csvBook.createSheet("csv");
		// add headers to the csv book
		log.info("Parsing file");
		Row row;
		row = csvSheet.createRow(0);
		ExcelUtil.addCol(row, "dictionary_name", 0);
		ExcelUtil.addCol(row, "group", 1);
		ExcelUtil.addCol(row, "section", 2);
		ExcelUtil.addCol(row, "name", 3);
		ExcelUtil.addCol(row, "description", 4);
		ExcelUtil.addCol(row, "data_type", 5);
		ExcelUtil.addCol(row, "value_set", 6);
		// get the source workbook and sheet
		Workbook book = ExcelUtil.getWorkbook(excel);
		Sheet sheet = ExcelUtil.getSheet(book, "COVID-Related Variables ");
		int srcRow = 2;
		int dstRow = 1;
		String name = ExcelUtil.getStringValue(sheet, srcRow, 1);
		while (StringUtils.isEmpty(name) == false) {
			row = csvSheet.createRow(dstRow);
			ExcelUtil.addCol(row, dictionaryName, 0);
			ExcelUtil.addCol(row, sheet.getSheetName(), 1);
			ExcelUtil.addCol(row, "", 2);
			ExcelUtil.addCol(row, ExcelUtil.getStringValue(sheet, srcRow, 1), 3);
			ExcelUtil.addCol(row, ExcelUtil.getStringValue(sheet, srcRow, 2), 4);
			ExcelUtil.addCol(row, ExcelUtil.getStringValue(sheet, srcRow, 3), 5);
			ExcelUtil.addCol(row, ExcelUtil.getStringValue(sheet, srcRow, 4), 6);
			// get the next row
			srcRow++;
			dstRow++;
			name = ExcelUtil.getStringValue(sheet, srcRow, 1);
		}
		log.info("About to save file: " + FileUtil.getCanonicalPath(csvTarget));
		log.info("Exists: " + csvTarget.exists());
		if (csvTarget.exists()) {
			log.info("Deleting");
			csvTarget.delete();
			log.info("Done with delete");
		}
		log.info("Saving");
		ExcelUtil.saveAsCsv(csvSheet, csvTarget);
		log.info("Saved as: " + FileUtil.getCanonicalPath(csvTarget));
		log.info("Done with parsing");
	}

}
