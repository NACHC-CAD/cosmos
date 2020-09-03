package org.nachc.cad.cosmos.oneoff.acmeds;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.nach.core.util.excel.ExcelUtil;
import com.nach.core.util.file.FileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AcMedParser {

	public static void main(String[] args) throws Exception {
		log.info("Parsing file...");
		String dirName = "/org/nachc/cad/cosmos/oneoff/acmeds/files";
		parse(dirName + "/excel/ac-meds.xlsx", "/src/test/java/org/nachc/cad/cosmos/oneoff/acmeds/files/csv/ac-meds.csv");
		log.info("Done.");
	}

	private static void parse(String fileName, String csvFileName) throws Exception {
		Sheet csvSheet = ExcelUtil.createNewWorkbook().createSheet();
		File csvFile = FileUtil.getFromProjectRoot(csvFileName);
		InputStream in = FileUtil.getInputStream(fileName);
		Workbook book = ExcelUtil.getWorkbook(in);
		Sheet srcSheet = book.getSheetAt(0);
		int max = srcSheet.getLastRowNum();
		Row row = csvSheet.createRow(0);
		ExcelUtil.setStringValue(csvSheet, "long_name", 0, 0);
		ExcelUtil.setStringValue(csvSheet, "short_name", 0, 1);
		ExcelUtil.setStringValue(csvSheet, "freq", 0, 2);
		for (int r = 1; r < max; r++) {
			String str = ExcelUtil.getStringValue(srcSheet, r, 0);
			String freq = ExcelUtil.getStringValue(srcSheet, r, 1);
			if (StringUtils.isEmpty(str)) {
				continue;
			}
			String[] split = str.split("[^A-Za-z\\-\\s#]");
			String drugName = str;
			if (split.length > 0) {
				drugName = split[0];
			}
			if (StringUtils.isEmpty(drugName)) {
				drugName = str;
			}
			drugName = drugName.trim();
			drugName = drugName.replace(" TABLET", "");
			drugName = drugName.replace(" TAB", "");
			drugName = drugName.trim();
			csvSheet.createRow(r);
			ExcelUtil.setStringValue(csvSheet, str, r, 0);
			ExcelUtil.setStringValue(csvSheet, drugName, r, 1);
			ExcelUtil.setStringValue(csvSheet, freq, r, 2);
			log.info(drugName + ":\t" + str);
		}
		log.info("Writing file: ");
		ExcelUtil.saveAsCsv(csvSheet, csvFile);
		log.info("File saved as: " + csvFile.getCanonicalPath());
	}

}
