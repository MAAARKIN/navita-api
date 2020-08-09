package br.com.navita.api.exception;

import lombok.Getter;
import lombok.Setter;

public class HttpException extends RuntimeException {

	private static final long serialVersionUID = 1479167339047464601L;

	@Getter
	@Setter
	private Integer code;

	@Getter
	@Setter
	private String message;
	
	public HttpException(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
}
