package org.nachc.cad.cosmos.util.databricks;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatabricksResponse {

	private String response;
	
	private int statusCode;
	
	private boolean success = false;
	
}
