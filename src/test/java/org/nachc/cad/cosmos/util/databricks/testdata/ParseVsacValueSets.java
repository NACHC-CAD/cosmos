package org.nachc.cad.cosmos.util.databricks.testdata;

import java.io.File;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.nachc.cad.cosmos.util.databricks.auth.DatabricksAuthUtil;
import org.nachc.cad.cosmos.util.vsac.VsacValueSetParser;

import com.nach.core.util.excel.ExcelUtil;
import com.nach.core.util.file.FileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParseVsacValueSets {

	public static void main(String[] args) {
		log.info("Starting parse...");
		File dir = new File(DatabricksAuthUtil.getTestFilesDir(), "value-sets/excel");
		List<File> files = FileUtil.listFiles(dir, "*.xlsx");
		Workbook book = ExcelUtil.createNewWorkbook();
		Sheet metaData = book.createSheet();
		log.info("Parsing " + files.size() + " files...");
		int cnt = 0;
		for(File file : files) {
			cnt++;
			log.info("\tFile " + cnt + " of " + files.size() + ": " + file.getName());
			VsacValueSetParser.parseFile(file, metaData);
		}
		log.info("Writing meta data");
		File metaDir = new File(dir.getParentFile(), "meta");
		VsacValueSetParser.writeMetaData(metaDir, metaData);
		log.info("Done.");
	}
	
}
