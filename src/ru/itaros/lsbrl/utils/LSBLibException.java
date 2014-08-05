package ru.itaros.lsbrl.utils;

public class LSBLibException extends Exception {

	public LSBLibException(String string, Exception e) {
		super(string,e);
	}

	public LSBLibException(String string) {
		super(string);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1245255616171941840L;

}
