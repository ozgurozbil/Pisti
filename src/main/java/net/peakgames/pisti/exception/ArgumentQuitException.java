package net.peakgames.pisti.exception;

public class ArgumentQuitException extends Exception {

	private static final long serialVersionUID = 6742597458487760019L;

	public ArgumentQuitException() {
	}

	/**
	 * Constructor that accepts a message
	 * 
	 * @author ozgur.ozbil
	 * @param message
	 */
	public ArgumentQuitException(String message) {
		super(message);
	}
}
