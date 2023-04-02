package com.example.demo.exception;

import java.io.IOException;
import java.io.Writer;


import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class MyTemplateExceptionHandler implements TemplateExceptionHandler {

	
	@Override
	public void handleTemplateException(TemplateException te, Environment env, Writer out) throws TemplateException {
		// TODO Auto-generated method stub
		 
		StaticVariable.setTemplateException(true);
		try {
            out.write("[ERROR: " + te.getMessage() + "]");
        } catch (IOException e) {
            throw new TemplateException("Failed to print error message. Cause: " + e, env);
        }
	}

	

}
