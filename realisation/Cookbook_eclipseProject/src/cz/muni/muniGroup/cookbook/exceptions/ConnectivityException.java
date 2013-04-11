package cz.muni.muniGroup.cookbook.exceptions;

/**
 * When error with network connection is appeared. Mainly during working with database.
 * @author Jan Kucera
 *
 */
public class ConnectivityException extends Exception {

	private static final long serialVersionUID = -3904512971258194635L;

	public ConnectivityException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}
}
