package org.nachc.cad.cosmos.util.databricks;

import java.io.File;

import org.nachc.cad.cosmos.util.databricks.auth.DatabricksAuthUtil;

import com.nach.core.util.http.HttpRequestClient;
import com.nach.core.util.time.Timer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabricksFileUtil {

	/**
	 * 
	 * Query if a file exists at the given location on the server.
	 * 
	 */
	public static DatabricksFileUtilResponse exists(String filePath) {
		DatabricksFileUtilResponse rtn = new DatabricksFileUtilResponse();
		String token = DatabricksAuthUtil.getToken();
		String url = DatabricksAuthUtil.getApiUrl();
		url = url + "/dbfs/get-status?path=" + filePath;
		HttpRequestClient client = new HttpRequestClient(url);
		client.setOauthToken(token);
		client.doGet();
		int statusCode = client.getStatusCode();
		rtn.setResponse(client.getResponse());
		rtn.setStatusCode(statusCode);
		if (statusCode == 200) {
			rtn.setSuccess(true);
		} else {
			rtn.setSuccess(false);
		}
		return rtn;
	}

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
	public static DatabricksFileUtilResponse put(String filePath, File file) {
		Timer timer = new Timer();
		timer.start();
		String token = DatabricksAuthUtil.getToken();
		String url = DatabricksAuthUtil.getApiUrl();
		url = url + "/dbfs/put";
		HttpRequestClient client = new HttpRequestClient(url);
		client.setOauthToken(token);
		client.addFormData("path", filePath);
		client.postFile(file, filePath);
		// create rtn object
		timer.stop();
		DatabricksFileUtilResponse rtn = new DatabricksFileUtilResponse();
		rtn.init(client, file, timer, filePath);
		return rtn;
	}

	/**
	 * 
	 * Method to put a file on the server.
	 * 
	 */
	public static String putZip(String filePath, File file) {
		String token = DatabricksAuthUtil.getToken();
		String url = DatabricksAuthUtil.getApiUrl();
		url = url + "/dbfs/put";
		HttpRequestClient client = new HttpRequestClient(url);
		client.setOauthToken(token);
		client.addHeader("Content-Encoding", "gzip");
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
	public static DatabricksFileUtilResponse delete(String filePath) {
		String token = DatabricksAuthUtil.getToken();
		String url = DatabricksAuthUtil.getApiUrl();
		url = url + "/dbfs/delete";
		HttpRequestClient client = new HttpRequestClient(url);
		client.setOauthToken(token);
		String json = "{\"path\":\"" + filePath + "\"}";
		client.doPost(json);
		DatabricksFileUtilResponse rtn = new DatabricksFileUtilResponse();
		rtn.setResponse(client.getResponse());
		rtn.setStatusCode(client.getStatusCode());
		rtn.setSuccess(rtn.getStatusCode() == 200);
		return rtn;
	}

	/**
	 * 
	 * Remove a directory.  
	 * 
	 */
	public static DatabricksFileUtilResponse rmdir(String filePath) {
		Timer timer = new Timer();
		timer.start();
		String token = DatabricksAuthUtil.getToken();
		String url = DatabricksAuthUtil.getApiUrl();
		url = url + "/dbfs/delete";
		HttpRequestClient client = new HttpRequestClient(url);
		client.setOauthToken(token);
		String json = "{\"path\":\"" + filePath + "\", \"recursive\":\"true\"}";
		client.doPost(json);
		timer.stop();
		DatabricksFileUtilResponse rtn = new DatabricksFileUtilResponse();
		rtn.init(client, null, timer, filePath);
		return rtn;
	}

	/**
	 * 
	 * Replace the contents of a given Databricks directory with the given file.  
	 * 
	 */
	public static DatabricksFileUtilResponse replace(String fileDirPath, File file) {
		return null;
	}
	
}
