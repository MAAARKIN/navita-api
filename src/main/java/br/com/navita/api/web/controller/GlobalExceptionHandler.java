package br.com.navita.api.web.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.navita.api.exception.HttpException;
import br.com.navita.api.util.UrlUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ErroInfo> handleUsernameNotFound(HttpServletResponse response, HttpServletRequest request,
			Exception exception) {

		UsernameNotFoundException ex = (UsernameNotFoundException) exception;
		ErroInfo erroInfo = new ErroInfo(LocalDateTime.now(), HttpStatus.UNAUTHORIZED.value(),
				ex.getClass().getSimpleName(), ex.getMessage(), UrlUtil.getCurrentUrl(request));
		return new ResponseEntity<>(erroInfo, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(HttpException.class)
	public ResponseEntity<ErroInfo> handleExceptionAlreadyExist(HttpServletResponse response,
			HttpServletRequest request, Exception exception) {

		ErroInfo erroInfo = buildErrorInfo(request, exception);
		return new ResponseEntity<>(erroInfo, HttpStatus.BAD_REQUEST);
	}

	private ErroInfo buildErrorInfo(HttpServletRequest request, Exception exception) {

		HttpException ex = (HttpException) exception;
		ErroInfo erroInfo = new ErroInfo(LocalDateTime.now(), ex.getCode(), ex.getClass().getSimpleName(),
				ex.getMessage(), UrlUtil.getCurrentUrl(request));
		return erroInfo;
	}

	@AllArgsConstructor
	@Getter
	public class ErroInfo {

		private LocalDateTime timestamp;
		private Integer code;
		private String exception;
		private String message;
		private String path;

	}
}
