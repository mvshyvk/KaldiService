package com.mvshyvk.kaldi.service.api;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2020-04-23T21:31:33.644Z[GMT]")
public class ApiException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7558537689100909622L;
	private int code;

	public ApiException(int code, String msg) {
		super(msg);
		this.code = code;
	}
}
