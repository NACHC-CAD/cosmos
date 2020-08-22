package org.nachc.cad.cosmos.util.databricks;

import java.io.File;

import org.nachc.cad.cosmos.util.databricks.auth.DatabricksAuthUtil;

import com.nach.core.util.http.HttpRequestClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabricksUtil {

	/**
	 * 
	 * Get a directory listing of the given path.
	 * 
	 */
	public static String list(String path) {
		String token = DatabricksAuthUtil.getToken();
		String url = DatabricksAuthUtil.getApiUrl();
		url = url + "/dbfs/list?path=" + path;
		HttpRequestClient client = new HttpRequestClient(url);
		client.setOauthToken(token);
		client.doGet();
		String response = client.getResponse();
		return response;
	}

	/**
	 * 
	 * Method to put a file on the server.  
	 * 
	 */
	public static String put(String filePath, File file) {
		String token = DatabricksAuthUtil.getToken();
		String url = DatabricksAuthUtil.getApiUrl();
		url = url + "/dbfs/put";
		HttpRequestClient client = new HttpRequestClient(url);
		client.setOauthToken(token);
		client.addFormData("path", filePath);
		client.postFile(file, filePath);
		String response = client.getResponse();
		return response;
	}

	/**
	 * 
	 * Delete a file from the server.
	 * 
	 */
	public static String delete(String filePath) {
		String token = DatabricksAuthUtil.getToken();
		String url = DatabricksAuthUtil.getApiUrl();
		url = url + "/dbfs/delete?path=" + filePath;
		HttpRequestClient client = new HttpRequestClient(url);
		client.setOauthToken(token);
		client.doPost("");
		String response = client.getResponse();
		return response;
	}

}
