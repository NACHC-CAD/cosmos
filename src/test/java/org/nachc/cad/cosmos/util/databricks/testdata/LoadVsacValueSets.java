package org.nachc.cad.cosmos.util.databricks.testdata;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.nachc.cad.cosmos.util.databricks.DatabricksFileUtilResponse;
import org.nachc.cad.cosmos.util.databricks.DatabricksFileUtil;
import org.nachc.cad.cosmos.util.databricks.auth.DatabricksAuthUtil;

import com.nach.core.util.file.FileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoadVsacValueSets {

	public static void main(String[] args) {
		String target = "/FileStore/tables/test/demo-03/value-set";
		File dir = new File(DatabricksAuthUtil.getTestFilesDir(), "value-sets/csv");
		List<File> files = FileUtil.listFiles(dir, "*.csv");
		int cnt = 0;
		ArrayList<DatabricksFileUtilResponse> responseList = new ArrayList<DatabricksFileUtilResponse>();
		for(File file : files) {
			cnt++;
			String fileTarget = target + "/csv/" + file.getName();
			log.info("\tFile " + cnt + " of " + files.size() + ": " + file.getName());
			log.info("\tTarget: " + fileTarget);
			DatabricksFileUtilResponse resp = DatabricksFileUtil.put(fileTarget, file);
			responseList.add(resp);
		}
		String fileTarget = target + "/meta/meta.csv";
		File meta = new File(DatabricksAuthUtil.getTestFilesDir(), "value-sets/meta/meta.csv");
		DatabricksFileUtilResponse metaResp = DatabricksFileUtil.put(fileTarget, meta);
		responseList.add(metaResp);
		cnt = 0;
		for(DatabricksFileUtilResponse resp : responseList) {
			cnt++;
			log.info(cnt + " of " + responseList.size() + ":\t" + resp.isSuccess() + "\t" + resp.getStatusCode() + "\t" + resp.getResponse().trim());
		}
		log.info("Done.");
	}
	
}
