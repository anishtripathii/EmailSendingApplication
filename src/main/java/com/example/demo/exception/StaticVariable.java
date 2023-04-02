package com.example.demo.exception;

public class StaticVariable {
	private static boolean isTemplateException;

	public static boolean isTemplateException() {
		return isTemplateException;
	}

	public static void setTemplateException(boolean isTemplateException) {
		StaticVariable.isTemplateException = isTemplateException;
	}
	
	
}
