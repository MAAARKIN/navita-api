package br.com.navita.api.exception;

public class AlreadyExistException extends HttpException {

	private static final long serialVersionUID = 4228363011860190050L;

	public AlreadyExistException(String message) {
		super(400, message);
	}
	
	public AlreadyExistException() {
		super(400, "already exist a resource");
	}
}
