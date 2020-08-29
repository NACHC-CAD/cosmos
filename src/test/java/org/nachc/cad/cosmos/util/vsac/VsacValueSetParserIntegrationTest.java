package org.nachc.cad.cosmos.util.vsac;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Test;

import com.nach.core.util.file.FileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VsacValueSetParserIntegrationTest {

	@Test
	public void shouldParseFile() throws Exception {
		log.info("Starting test...");
		String fileName = "2.16.840.1.113762.1.4.1021.30.xlsx";
		File testRoot = FileUtil.getFromProjectRoot("/src/test/resources/valueset/vsac/");
		File csvDir = new File(testRoot, "csv");
		File metaDir = new File(testRoot, "meta");
		File excelFile = new File(testRoot, "excel/" + fileName);
		log.info("Got file: " + excelFile.getCanonicalPath());
		assertTrue(excelFile.exists());
		log.info("Parsing file");
		Sheet metaData = VsacValueSetParser.parseFile(excelFile, csvDir, null);
		log.info("Writing csv file");
		VsacValueSetParser.writeMetaData(metaDir, metaData);
		log.info("Done.");
	}
	
}
