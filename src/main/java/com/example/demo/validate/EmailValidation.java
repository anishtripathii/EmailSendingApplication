package com.example.demo.validate;

import org.springframework.stereotype.Component;

import com.example.demo.dto.EmailDetailsDto;
import com.example.demo.exception.InvalidEmailException;


@Component
public class EmailValidation {

	public void validate (EmailDetailsDto edtao) throws InvalidEmailException{    
		String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
		boolean result = edtao.getRecipient().matches(regex);
		if(!result) {
			 throw new InvalidEmailException("This Email ID is not valid");
				 }
			 }
}
