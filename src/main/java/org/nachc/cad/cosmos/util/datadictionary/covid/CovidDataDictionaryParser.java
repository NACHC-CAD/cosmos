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
		ExcelUtil.addCol(row, "file_name", 1);
		ExcelUtil.addCol(row, "group", 2);
		ExcelUtil.addCol(row, "row_id", 3);
		ExcelUtil.addCol(row, "section", 4);
		ExcelUtil.addCol(row, "name", 5);
		ExcelUtil.addCol(row, "description", 6);
		ExcelUtil.addCol(row, "data_type", 7);
		ExcelUtil.addCol(row, "value_set", 8);
		// get the source workbook and sheet
		Workbook book = ExcelUtil.getWorkbook(excel);
		Sheet sheet = ExcelUtil.getSheet(book, "COVID-Related Variables ");
		int srcRow = 2;
		int dstRow = 1;
		String name = ExcelUtil.getStringValue(sheet, srcRow, 1);
		int cnt = 0;
		while (StringUtils.isEmpty(name) == false) {
			cnt++;
			row = csvSheet.createRow(dstRow);
			ExcelUtil.addCol(row, dictionaryName, 0);
			ExcelUtil.addCol(row, excel.getName(), 1);
			ExcelUtil.addCol(row, sheet.getSheetName(), 2);
			ExcelUtil.addCol(row, cnt + "", 3);
			ExcelUtil.addCol(row, getSection(cnt), 4);
			ExcelUtil.addCol(row, ExcelUtil.getStringValue(sheet, srcRow, 1), 5);
			ExcelUtil.addCol(row, ExcelUtil.getStringValue(sheet, srcRow, 2), 6);
			ExcelUtil.addCol(row, ExcelUtil.getStringValue(sheet, srcRow, 3), 7);
			ExcelUtil.addCol(row, ExcelUtil.getStringValue(sheet, srcRow, 5), 8);
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

	private static String getSection(int cnt) {
		cnt = cnt - 2;
		if(cnt < 2) {
			return "Vital Signs";
		} else if(cnt < 42) {
			return "Signs and Simptoms";
		} else if(cnt < 52) {
			return "Exposure";
		} else if (cnt < 93) {
			return "Testing";
		} else if (cnt < 114) {
			return "Diagnosis/Case Status";
		} else if (cnt < 135) {
			return "Case Mgmt";
		} else if (cnt < 140) {
			return "Case Reporting";
		} else if (cnt < 144) {
			return "Virtual Health Services";
		}
		return "Other";
	}
	
}
