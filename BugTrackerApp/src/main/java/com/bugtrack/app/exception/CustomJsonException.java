package com.bugtrack.app.exception;

/**
 * @author Baoxing Li
 * @version 1.0.0
 * Extend SpringWeb Exception Class to support ajax exception
 */
public class CustomJsonException extends Exception {
	private static final long serialVersionUID = 962694786640024741L;

	/**
	 * Constructor
	 * @param message in exception message
	 */
	public CustomJsonException(String message) {
        super(message);
    }

}
