package org.nachc.cad.cosmos.oneoff.millionhearts.pivotencounter;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.monitorjbl.xlsx.StreamingReader;
import com.nach.core.util.excel.ExcelUtil;
import com.nach.core.util.time.TimeUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PivotEncounterData_OLD {

	private static final int CNT = 95;

	private static final String[] PARAMS = { "visitDate", "SysBp", "DiaBp", "Thiaz", "bb", "ace", "arb", "ccb", "alpha", "a2ra", "CenAg", "pai", "vaso" };

	public static void main(String[] args) {
		log.info("Starting parse...");
		log.info("Getting file");
		// String srcFileName = "C:\\_WORKSPACES\\nachc\\oneoffs\\millionhearts\\Unity Encounters BPAA - Short.xlsx";
		// String dstFileName = "C:\\_WORKSPACES\\nachc\\oneoffs\\millionhearts\\Unity Encounters BPAA - Short PIVOT.csv";
		String srcFileName = "C:\\_WORKSPACES\\nachc\\oneoffs\\millionhearts\\Unity Encounters BPAA - Med.xlsx";
		String dstFileName = "C:\\_WORKSPACES\\nachc\\oneoffs\\millionhearts\\Unity Encounters BPAA - Med PIVOT.csv";
		parse(srcFileName, dstFileName);
	}

	private static void parse(String srcFileName, String dstFileName) {
		File srcFile = new File(srcFileName);
		Iterator<Row> rows = getReader(srcFile);
		// skip the header row
		// Workbook book = ExcelUtil.getWorkbook(srcFile);
		// Sheet srcSheet = book.getSheetAt(0);
		Sheet dstSheet = ExcelUtil.createNewWorkbook().createSheet("pivot");
		addColHeaders(dstSheet);
		// int lastRow = srcSheet.getLastRowNum();
		log.info("Row 1");
		// for (int r = 1; r <= lastRow; r++) {
		// int r = -1;
		// skip the header
		if(rows.hasNext()) {
			Row header = rows.next();
		}
		while(rows.hasNext()) {
			// Row row = srcSheet.getRow(r);
			Row row = rows.next();
			if(row.getRowNum() % 10000 == 0) {
				log.info("Row " + row.getRowNum());
			}
			ArrayList<String> visits = new ArrayList<String>();
			// String patientId = ExcelUtil.getStringValue(row, 0);
			if(row == null || row.getCell(0) == null) {
				log.info("Blank row: " + row.getRowNum());
				
				continue;
			}
			String patientId = row.getCell(0).getStringCellValue();
			for (int c = 1; c <= CNT; c++) {
				// String cellString = ExcelUtil.getStringValue(row, c);
				String cellString = row.getCell(c).getStringCellValue();
				if (StringUtils.isEmpty(cellString) || "NULL".equals(cellString.trim())) {
					break;
				}
				Date date = row.getCell(c).getDateCellValue();
				String visitDate = TimeUtil.getDateAsYyyyMmDd(date);
				visits.add(visitDate);
			}
			parseVisits(patientId, visits, row, dstSheet);
		}
		log.info("Done with parse");
		log.info("Wrting file");
		File dstFile = new File(dstFileName);
		ExcelUtil.saveAsCsv(dstSheet, dstFile);
		log.info("Done.");
	}

	private static void parseVisits(String patientId, List<String> visits, Row row, Sheet dstSheet) {
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
				// String val = ExcelUtil.getStringValue(row, srcCol);
				String val = row.getCell(srcCol).getStringCellValue();
				if(paramCnt == 0) {
					val = visit;
				}
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
	
	
	private static Iterator<Row> getReader(File srcFile) {
		Iterator<Row> rtn = StreamingReader.builder().sheetIndex(0).read(srcFile).iterator();
		return rtn;
	}
}
