package com.example.demo.exception;

public class InvalidEmailException extends RuntimeException{

	/**
	 * Exception handling for Invalid EmailID
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidEmailException(String str)
	{
		super(str);
	}
	
}
