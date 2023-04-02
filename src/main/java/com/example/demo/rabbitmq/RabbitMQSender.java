package com.example.demo.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.dto.EmailDetailsDto;
import com.example.demo.model.EmailDetails;

@Service
public class RabbitMQSender {

	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@Value("${capgeminipocemail.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${capgeminipocemail.rabbitmq.routingkey}")
	private String routingkey;	
	
	public void send(EmailDetailsDto emailDetailsDto) {
		rabbitTemplate.convertAndSend(exchange, routingkey, emailDetailsDto);
		System.out.println("Send msg = " + emailDetailsDto);
	    
	}
}
