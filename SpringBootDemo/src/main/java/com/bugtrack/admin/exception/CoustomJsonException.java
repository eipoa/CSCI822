package com.bugtrack.admin.exception;

/**
 * @author Baoxing Li
 * @version 1.0.0
 * Extend SpringWeb Exception Class to support ajax exception
 */
public class CoustomJsonException extends Exception {
	private static final long serialVersionUID = 962694786640024741L;

	/**
	 * Constructor
	 * @param message in exception message
	 */
	public CoustomJsonException(String message) {
        super(message);
    }

}
