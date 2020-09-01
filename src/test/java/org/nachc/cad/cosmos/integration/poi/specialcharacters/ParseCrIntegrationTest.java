package org.nachc.cad.cosmos.integration.poi.specialcharacters;

import java.io.InputStream;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import com.nach.core.util.excel.ExcelUtil;
import com.nach.core.util.file.FileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParseCrIntegrationTest {

	@Test
	public void shouldParseNewLineCharacters() {
		log.info("Starting test...");
		InputStream in = FileUtil.getInputStream("/org/nachc/cad/cosmos/integration/poi/specialcharacters/testfiles/cr-test.xlsx");
		Workbook book = ExcelUtil.getWorkbook(in);
		Sheet sheet = book.getSheetAt(0);
		String val = ExcelUtil.getStringValue(sheet, 0, 0);
		log.info("Got val: " + val);
		log.info("Done.");
	}
	
}
