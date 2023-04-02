package com.example.demo.services;



import org.springframework.stereotype.Service;

import com.example.demo.dto.EmailDetailsDto;
import com.example.demo.exception.CustomTemplateException;
import com.example.demo.exception.InvalidEmailException;

import freemarker.core.InvalidReferenceException;
import freemarker.template.TemplateException;

@Service

public interface EmailService {
	public void sendSimpleMail(EmailDetailsDto detailsDto) throws InvalidEmailException, InvalidReferenceException;
	
}
