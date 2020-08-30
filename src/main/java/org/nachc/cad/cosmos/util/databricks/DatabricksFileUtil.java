package org.nachc.cad.cosmos.util.databricks;

import java.io.File;

import org.nachc.cad.cosmos.util.databricks.auth.DatabricksAuthUtil;
import org.nachc.cad.cosmos.util.databricks.exception.DatabricksFileException;

import com.nach.core.util.http.HttpRequestClient;
import com.nach.core.util.time.Timer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabricksFileUtil {

	/**
	 * 
	 * Query if a file exists at the given location on the server. Path can be a file or dir.  
	 * 
	 */
	public static DatabricksFileUtilResponse exists(String path) {
		Timer timer = new Timer();
		timer.start();
		DatabricksFileUtilResponse rtn = new DatabricksFileUtilResponse();
		String token = DatabricksAuthUtil.getToken();
		String url = DatabricksAuthUtil.getApiUrl();
		url = url + "/dbfs/get-status?path=" + path;
		HttpRequestClient client = new HttpRequestClient(url);
		client.setOauthToken(token);
		client.doGet();
		timer.stop();
		rtn.init(client);
		rtn.init(timer);
		rtn.init(path);
		// modify the status to reflect the file not found status
		int statusCode = client.getStatusCode();
		if (statusCode == 200) {
			rtn.setSuccess(true);
			rtn.setFileExists(true);
		} else {
			rtn.setSuccess(false);
			rtn.setFileExists(false);
		}
		if(statusCode == 404) {
			rtn.setSuccess(true);
			rtn.setFileExists(false);
		}
		return rtn;
	}

	/**
	 * 
	 * Get a directory listing of the given path.
	 * 
	 */
	public static String list(String dirPath) {
		String token = DatabricksAuthUtil.getToken();
		String url = DatabricksAuthUtil.getApiUrl();
		url = url + "/dbfs/list?path=" + dirPath;
		HttpRequestClient client = new HttpRequestClient(url);
		client.setOauthToken(token);
		client.doGet();
		String response = client.getResponse();
		return response;
	}

	/**
	 * 
	 * Method to put a file on the server. The filePath is the path with out the file name.  
	 * The file will be placed at filePath/fileName location.  
	 * 
	 */
	public static DatabricksFileUtilResponse put(String dirPath, File file) {
		Timer timer = new Timer();
		timer.start();
		String token = DatabricksAuthUtil.getToken();
		String url = DatabricksAuthUtil.getApiUrl();
		url = url + "/dbfs/put";
		HttpRequestClient client = new HttpRequestClient(url);
		client.setOauthToken(token);
		String filePath = dirPath + "/" + file.getName();
		client.addFormData("path", filePath);
		client.postFile(file, filePath);
		// create rtn object
		timer.stop();
		DatabricksFileUtilResponse rtn = new DatabricksFileUtilResponse();
		rtn.init(client, file, timer, filePath);
		if(rtn.isSuccess() == false) {
			log.info(rtn.isSuccess() + ": (" + rtn.getStatusCode() + ") " + rtn.getDatabricksFilePath() + "\t" + rtn.getResponse());
			throw new RuntimeException("Put failed for " + dirPath, new DatabricksFileException(rtn));
		}
		return rtn;
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
		rtn.init(client);
		return rtn;
	}

	/**
	 * 
	 * Remove a directory.  
	 * 
	 */
	public static DatabricksFileUtilResponse rmdir(String dirPath) {
		Timer timer = new Timer();
		timer.start();
		String token = DatabricksAuthUtil.getToken();
		String url = DatabricksAuthUtil.getApiUrl();
		url = url + "/dbfs/delete";
		HttpRequestClient client = new HttpRequestClient(url);
		client.setOauthToken(token);
		String json = "{\"path\":\"" + dirPath + "\", \"recursive\":\"true\"}";
		client.doPost(json);
		timer.stop();
		DatabricksFileUtilResponse rtn = new DatabricksFileUtilResponse();
		rtn.init(client, null, timer, dirPath);
		return rtn;
	}
	
}
