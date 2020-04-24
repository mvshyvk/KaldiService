package com.mvshyvk.kaldi.service.api;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2020-04-23T21:31:33.644Z[GMT]")
public class NotFoundException extends ApiException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5701367828138035020L;
	private int code;

	public NotFoundException(int code, String msg) {
		super(code, msg);
		this.code = code;
	}
}
