package cz.muni.muniGroup.cookbook.exceptions;

public class CookbookException extends Exception {

	private static final long serialVersionUID = 8491704092501730576L;

	public CookbookException() {
		super();
	}

	public CookbookException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public CookbookException(String detailMessage) {
		super(detailMessage);
	}

	public CookbookException(Throwable throwable) {
		super(throwable);
	}

}
