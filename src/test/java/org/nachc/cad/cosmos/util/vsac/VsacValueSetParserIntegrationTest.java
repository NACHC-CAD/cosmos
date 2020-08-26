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
		File file = FileUtil.getFromProjectRoot("/src/test/resources/valueset/vsac/excel/" + fileName);
		log.info("Got file: " + file.getCanonicalPath());
		assertTrue(file.exists());
		Sheet metaData = VsacValueSetParser.parseFile(file);
		File metaDataDir = new File(file.getParentFile().getParentFile(), "meta");
		VsacValueSetParser.writeMetaData(metaDataDir, metaData);
		log.info("Done.");
	}
	
}
