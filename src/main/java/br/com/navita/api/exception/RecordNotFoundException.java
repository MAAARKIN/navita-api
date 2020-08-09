package br.com.navita.api.exception;

public class RecordNotFoundException extends HttpException {

	private static final long serialVersionUID = 2333863350101692344L;

	public RecordNotFoundException(String message) {
		super(400, message);
	}
}
