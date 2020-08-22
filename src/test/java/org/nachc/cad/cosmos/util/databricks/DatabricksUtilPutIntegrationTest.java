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
		
		String filePath = "/FileStore/tables/test/integration-testing/basic/FROM-JAVA.txt";
		String fileName = "/src/test/resources/csv/dummy/hello-world.txt";
		File file = FileUtil.getFromProjectRoot(fileName);
		log.info("Got file: " + file.getCanonicalPath());
		String response = DatabricksUtil.put(filePath, file);
		log.info("Got response: \n " + response);
		
		// String fileName = "/src/test/resources/csv/dummy/hello-world.txt";
		// File file = FileUtil.getFromProjectRoot(fileName);
		// postFile(file);
		log.info("Done.");
	}

	private void postFile(File file) throws Exception {
		String url = DatabricksAuthUtil.getApiUrl() + "/dbfs/put";
		String token = DatabricksAuthUtil.getToken();
		String path = "/FileStore/tables/demo/foobar/flights/2011-summary.csv";
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.addTextBody("path", path, ContentType.TEXT_PLAIN);

		httpPost.addHeader("Authorization", "Bearer " + token);
		
		// This attaches the file to the POST:

		builder.addBinaryBody(
		    "file",
		    new FileInputStream(file),
		    ContentType.APPLICATION_OCTET_STREAM,
		    file.getName()
		);

		HttpEntity multipart = builder.build();
		httpPost.setEntity(multipart);
		CloseableHttpResponse response = httpClient.execute(httpPost);
		HttpEntity responseEntity = response.getEntity();
		String str = HttpRequestClient.getResponse(responseEntity.getContent());
		log.info(response.getStatusLine().getStatusCode() + "");
		
		log.info("Got response \n" + str);
	}
	
}
