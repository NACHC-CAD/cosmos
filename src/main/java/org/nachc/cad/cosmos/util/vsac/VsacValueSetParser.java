package org.nachc.cad.cosmos.util.vsac;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.nach.core.util.excel.ExcelUtil;

public class VsacValueSetParser {

	public static void parseFile(File file) {
		Workbook srcBook = ExcelUtil.getWorkbook(file);
		Workbook dstBook = ExcelUtil.createNewWorkbook();
		// get the Value Set Info sheet
		Sheet infoSheet = ExcelUtil.getSheet(srcBook, "Value Set Info");
		// get the info values
		String valueSetName = ExcelUtil.getStringValue(infoSheet, 1, 1);
		String codeSystem = ExcelUtil.getStringValue(infoSheet, 2, 1);
		String oid = ExcelUtil.getStringValue(infoSheet, 3, 1);
		Sheet srcSheet = srcBook.getSheet("Expansion List");
		Sheet dstSheet = dstBook.createSheet("Expansion");
		int srcRow = 12;
		int dstRow = 0;
		String code = ExcelUtil.getStringValue(srcSheet, 13, 0);
		while(StringUtils.isEmpty(code) == false) {
			Row row = dstSheet.createRow(dstRow);
			for(int c=0;c<5;c++) {
				row.createCell(c);
				String val = ExcelUtil.getStringValue(srcSheet, srcRow, c);
				ExcelUtil.setStringValue(dstSheet, val, dstRow, c);
			}
			row.createCell(5);
			row.createCell(6);
			row.createCell(7);
			if(dstRow == 0) {
				ExcelUtil.setStringValue(dstSheet, "Value Set Name", dstRow, 5);
				ExcelUtil.setStringValue(dstSheet, "Code System", dstRow, 6);
				ExcelUtil.setStringValue(dstSheet, "OID", dstRow, 7);
			} else {
				ExcelUtil.setStringValue(dstSheet, valueSetName, dstRow, 5);
				ExcelUtil.setStringValue(dstSheet, codeSystem, dstRow, 6);
				ExcelUtil.setStringValue(dstSheet, oid, dstRow, 7);
			}
			dstRow++;
			srcRow++;
			code = ExcelUtil.getStringValue(srcSheet, srcRow, 0);
		}
		// create the csv file
		File csvFile = new File(file.getParentFile().getParentFile(), "csv/" + file.getName() + ".csv");
		ExcelUtil.saveAsCsv(dstSheet, csvFile);
	}

}
