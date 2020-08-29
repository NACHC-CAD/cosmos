package org.nachc.cad.cosmos.util.databricks;

import java.util.List;

import org.junit.Test;

import com.nach.core.util.json.JsonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabricksUtilListIntegrationTest {

	@Test
	public void shouldGetFileList() {
		String path = "/";
		String fileListJson = DatabricksFileUtil.list(path);
		fileListJson = JsonUtil.prettyPrint(fileListJson);
		log.info("Got file list: \n" + fileListJson);
		List<String> files = JsonUtil.getJsonArray(fileListJson, "files");
		log.info("Got " + files.size() + " files");
		for(int i=0;i<files.size();i++) {
			String json = files.get(i);
			String name = JsonUtil.getString(json, "path");
			log.info("\t" + name);
		}
		log.info("Done.");
	}
	
}
