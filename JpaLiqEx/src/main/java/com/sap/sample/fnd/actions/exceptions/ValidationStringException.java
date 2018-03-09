/**
 * 
 */
package com.sap.sample.fnd.actions.exceptions;

/**
 * @author I070883
 *
 */
public class ValidationStringException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public String currentMessage = null;

	/**
	 * 
	 */
	public ValidationStringException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public ValidationStringException(String message) {
		super(message);
		//this.currentMessage = message;
	}

	/**
	 * @param cause
	 */
	public ValidationStringException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ValidationStringException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ValidationStringException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
