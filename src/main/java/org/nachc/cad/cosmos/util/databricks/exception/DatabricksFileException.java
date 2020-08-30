package org.nachc.cad.cosmos.util.databricks.exception;

import org.nachc.cad.cosmos.util.databricks.DatabricksFileUtilResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatabricksFileException extends Exception {

	private DatabricksFileUtilResponse resp;

	public DatabricksFileException(DatabricksFileUtilResponse resp) {
		this.resp = resp;
	}

}
