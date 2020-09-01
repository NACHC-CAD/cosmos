package org.nachc.cad.cosmos.integration.http.downloadfile;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.nachc.cad.cosmos.util.databricks.auth.DatabricksAuthUtil;

import com.nach.core.util.excel.ExcelUtil;
import com.nach.core.util.file.FileUtil;
import com.nach.core.util.http.HttpRequestClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DownloadFileExample {

	@Test
	public void shouldDownloadFile() {
		log.info("Starting test...");
		String url = "https://vsac.nlm.nih.gov/vsac/pc/vs/report/excel/multipleValueSetsDetails?download=true&release=Latest";
		String token = DatabricksAuthUtil.getVsacToken();
		HttpRequestClient client = new HttpRequestClient(url);
		String body = getValueSets();
		log.info("VALUE SETS: \n" + body);
		client.setOauthToken(token);
		log.info("Sending request...");
		client.doPost(body);
		log.info("Got response: " + client.getStatusCode());
		log.info("Writing file...");
		File file = new File("C:\\temp\\download.zip");
		if(file.exists() == true) {
			file.delete();
		} 
		FileUtil.createNewFile(file);
		client.writeResponseToFile(file);
		log.info("Done writing file: " + FileUtil.getCanonicalPath(file));
	}
	
	private String getValueSets() {
		String str = "";
		String fileName = "C:\\_WORKSPACES\\nachc\\data-dictionary\\covid\\excel\\2020-08-19 COVID 19- Data Dictionary.xlsx";
		File file = new File(fileName);
		Workbook book = ExcelUtil.getWorkbook(file);
		Sheet sheet = book.getSheet("COVID-Related Variables ");
		for(int i= 6;i<100;i++) {
			String oid = ExcelUtil.getStringValue(sheet, i, 5);
			boolean isEmpty = StringUtils.isEmpty(oid);
			if(str.length() > 0 && isEmpty == false) {
				str += ",";
			}
			if(isEmpty == false) {
				str += oid;
			}
		}
		return str;
	}
	
}
