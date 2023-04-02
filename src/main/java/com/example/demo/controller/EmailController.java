package com.example.demo.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.EmailServiceImplDao;
import com.example.demo.model.EmailDetails;
import com.example.demo.rabbitmq.RabbitMQSender;
import com.example.demo.services.EmailService;

@RestController
public class EmailController {
	@Autowired 
	EmailService emailService;
	
	@Autowired
	EmailServiceImplDao edao;
	
	@Autowired
	RabbitMQSender rabbitMQSender;
	
	@GetMapping(value = "/producer")
	public String producer(@RequestBody  EmailDetails emailDetails) {
	
	return edao.rabbitsender(emailDetails);
	}
	
	@GetMapping("/")
	public String home() {
		return "This is the home Page";
	}
	
	
		
}
