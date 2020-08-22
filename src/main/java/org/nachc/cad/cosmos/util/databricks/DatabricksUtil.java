package org.nachc.cad.cosmos.util.databricks;

import org.nachc.cad.cosmos.util.databricks.auth.DatabricksAuthUtil;

import com.nach.core.util.http.HttpRequestClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabricksUtil {

	public static String list(String path) {
		String rtn = "";
		String token = DatabricksAuthUtil.getToken();
		String url = DatabricksAuthUtil.getApiUrl();
		url = url + "/dbfs/list?path=" + path;
		HttpRequestClient client = new HttpRequestClient(url);
		client.setOauthToken(token);
		client.doGet();
		String response = client.getResponse();
		return response;
	}
	
}
