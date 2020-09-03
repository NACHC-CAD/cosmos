package org.nachc.cad.cosmos.oneoff.millionhearts.pivotencounter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.nach.core.util.excel.ExcelUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PivotEncounterData {

	private static final int CNT = 95;

	private static final String[] PARAMS = { "visitDate", "SysBp", "DiaBp", "Thiaz", "bb", "ace", "arb", "ccb", "alpha", "a2ra", "CenAg", "pai", "vaso" };

	public static void main(String[] args) {
		log.info("Starting parse...");
		log.info("Getting file");
		String srcFileName = "C:\\_WORKSPACES\\nachc\\oneoffs\\millionhearts\\Unity Encounters BPAA - Short.xlsx";
		String dstFileName = "C:\\_WORKSPACES\\nachc\\oneoffs\\millionhearts\\Unity Encounters BPAA PIVOT - Short.csv";
		parse(srcFileName, dstFileName);
	}

	private static void parse(String srcFileName, String dstFileName) {
		File file = new File(srcFileName);
		Workbook book = ExcelUtil.getWorkbook(file);
		Sheet srcSheet = book.getSheetAt(0);
		Sheet dstSheet = ExcelUtil.createNewWorkbook().createSheet("pivot");
		addColHeaders(dstSheet);
		int lastRow = srcSheet.getLastRowNum();
		for (int r = 1; r <= lastRow; r++) {
			Row row = srcSheet.getRow(r);
			ArrayList<String> visits = new ArrayList<String>();
			String patientId = ExcelUtil.getStringValue(srcSheet, r, 0);
			for (int c = 1; c <= c; c++) {
				String visitDate = ExcelUtil.getStringValue(srcSheet, r, c);
				if (StringUtils.isEmpty(visitDate) || "NULL".equals(visitDate)) {
					break;
				}
				visits.add(visitDate);
			}
			parseVisits(patientId, visits, srcSheet, dstSheet, r);
		}
		log.info("Done with parse");
		log.info("Wrting file");
		File dstFile = new File(dstFileName);
		ExcelUtil.saveAsCsv(dstSheet, dstFile);
	}

	private static void parseVisits(String patientId, List<String> visits, Sheet srcSheet, Sheet dstSheet, int srcRow) {
		// for each visit
		int visitCnt = 0;
		for (String visit : visits) {
			visitCnt++;
			// for each param
			int dstCol = 0;
			int dstRow = dstSheet.getLastRowNum() + 1;
			ExcelUtil.setStringValue(dstSheet, patientId, dstRow, 0);
			int paramCnt = -1;
			for (String param : PARAMS) {
				paramCnt++;
				dstCol++;
				int srcCol = visitCnt + (paramCnt * CNT);
				String val = ExcelUtil.getStringValue(srcSheet, srcRow, srcCol);
				ExcelUtil.setStringValue(dstSheet, val, dstRow, dstCol);
			}
		}
	}

	private static void addColHeaders(Sheet dstSheet) {
		ExcelUtil.setStringValue(dstSheet, "patientId", 0, 0);
		int cnt = 0;
		for(String param : PARAMS) {
			cnt++;
			ExcelUtil.setStringValue(dstSheet, param, 0, cnt);
		}
	}
	
}
