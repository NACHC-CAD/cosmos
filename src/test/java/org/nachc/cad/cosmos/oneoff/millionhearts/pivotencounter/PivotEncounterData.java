package org.nachc.cad.cosmos.oneoff.millionhearts.pivotencounter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;

import com.nach.core.util.excel.ExcelUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PivotEncounterData {

	private static final int CNT = 95;

	private static final String[] PARAMS = { "visitDate", "SysBp", "DiaBp", "Thiaz", "bb", "ace", "arb", "ccb", "alpha", "a2ra", "CenAg", "pai", "vaso" };

	public static void main(String[] args) throws Exception {
		log.info("Starting parse...");
		log.info("Getting file");
		//String srcFileName = "C:\\_WORKSPACES\\nachc\\oneoffs\\millionhearts\\Unity Encounters BPAA - Med.csv";
		//String dstFileName = "C:\\_WORKSPACES\\nachc\\oneoffs\\millionhearts\\Unity Encounters BPAA - Med PIVOT.csv";
		String srcFileName = "C:\\_WORKSPACES\\nachc\\oneoffs\\millionhearts\\Unity Encounters BPAA.csv";
		String dstFileName = "C:\\_WORKSPACES\\nachc\\oneoffs\\millionhearts\\Unity Encounters BPAA - PIVOT.csv";
		parse(srcFileName, dstFileName);
	}

	public static void parse(String srcFileName, String dstFileName) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(srcFileName)));
		Sheet dstSheet = ExcelUtil.createNewWorkbook().createSheet("pivot");
		addColHeaders(dstSheet);
		String str = reader.readLine();
		int cnt = 0;
		while (str != null) {
			str = reader.readLine();
			if(str == null) {
				break;
			}
			if (cnt % 10000 == 0) {
				log.info("Row " + cnt);
			}
			String[] tokens = str.split("\\,");
			if (tokens.length != 1236) {
				log.info("ROW: " + cnt);
				log.info("LENGTH: " + tokens.length);
			}
			parseVisits(tokens, dstSheet);
			cnt++;
		}
		log.info("Done with parse");
		log.info("Wrting file");
		File dstFile = new File(dstFileName);
		ExcelUtil.saveAsCsv(dstSheet, dstFile);
		log.info("Done.");
	}

	private static void parseVisits(String[] row, Sheet dstSheet) {
		String patientId = row[0];
		List<String> visits = getVisits(row);
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
				String val = row[srcCol];
				if (paramCnt == 0) {
					val = visit;
				}
				ExcelUtil.setStringValue(dstSheet, val, dstRow, dstCol);
			}
		}
	}

	private static void addColHeaders(Sheet dstSheet) {
		ExcelUtil.setStringValue(dstSheet, "patientId", 0, 0);
		int cnt = 0;
		for (String param : PARAMS) {
			cnt++;
			ExcelUtil.setStringValue(dstSheet, param, 0, cnt);
		}
	}

	private static List<String> getVisits(String[] row) {
		ArrayList<String> rtn = new ArrayList<String>();
		for (int i = 0; i < CNT; i++) {
			String visit = row[i + 1];
			if (StringUtils.isEmpty(visit) || visit.trim().equals("NULL")) {
				break;
			}
			rtn.add(visit);
		}
		return rtn;
	}

}
