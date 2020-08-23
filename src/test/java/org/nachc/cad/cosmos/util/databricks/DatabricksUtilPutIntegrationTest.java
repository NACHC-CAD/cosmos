package org.nachc.cad.cosmos.util.databricks;

import java.io.File;
import java.io.FileInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.nachc.cad.cosmos.util.databricks.auth.DatabricksAuthUtil;

import com.nach.core.util.file.FileUtil;
import com.nach.core.util.http.HttpRequestClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabricksUtilPutIntegrationTest {

	@Test
	public void shouldPutFileOnServer() throws Exception {
		log.info("Starting test...");
		String filePath = DatabricksTestFiles.HELLO_WORLD_PATH;
		String fileName = "/src/test/resources/csv/dummy/hello-world.txt";
		File file = FileUtil.getFromProjectRoot(fileName);
		log.info("Got file: " + file.getCanonicalPath());
		String response = DatabricksUtil.put(filePath, file);
		log.info("Got response: \n " + response);
		log.info("Done.");
	}

}
