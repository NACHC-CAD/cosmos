package org.nachc.cad.cosmos.create.datadictionary;

import java.io.File;

import com.nach.core.util.file.FileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class B_DataDictionaryDeleteParsedFiles {

	public static void deleteFiles() {
		try {
			File file;
			// delete csv files
			file = new File(A_DataDictionaryParameters.CSV_FILE_LOCATION_FOR_DD);
			log.info("Clearing contents of: " + A_DataDictionaryParameters.CSV_FILE_LOCATION_FOR_DD);
			FileUtil.clearContents(file);
			log.info("File exists: " + file.exists() + "\t" + file.getCanonicalPath());
			// done
			log.info("Done deleting files");
		} catch (Exception exp) {
			throw new RuntimeException(exp);
		}
	}

}
