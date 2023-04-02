package com.example.demo.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dao.EmailServiceImplDao;
import com.example.demo.dto.EmailDetailsDto;
import com.example.demo.exception.CustomTemplateException;
import com.example.demo.exception.InvalidEmailException;
import com.example.demo.model.EmailDetails;

import freemarker.core.InvalidReferenceException;
import freemarker.template.TemplateException;


@Component
public class RabbitMQConsumer {
	
	@Autowired
	EmailServiceImplDao edao;
	
	@RabbitListener( id = "idOne" ,queues = "${capgeminipocemail.rabbitmq.queue}")
	public void recievedMessage(EmailDetailsDto emaildetailDto) throws InvalidEmailException, InvalidReferenceException{
		System.out.println("Recieved Message From RabbitMQ: " + emaildetailDto);
		
		edao.rabbitlistener(emaildetailDto);
		
	}
}
