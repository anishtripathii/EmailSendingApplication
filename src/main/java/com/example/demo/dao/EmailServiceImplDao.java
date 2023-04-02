package com.example.demo.dao;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.example.demo.dto.EmailDetailsDto;
import com.example.demo.exception.CustomTemplateException;
import com.example.demo.exception.InvalidEmailException;
import com.example.demo.exception.MyTemplateExceptionHandler;
import com.example.demo.exception.StaticVariable;
import com.example.demo.model.EmailDetails;
import com.example.demo.model.EmailSentStatus;
import com.example.demo.rabbitmq.RabbitMQSender;
import com.example.demo.repository.EmailRepository;
import com.example.demo.repository.EmailStatusRepository;
import com.example.demo.services.EmailService;
import com.example.demo.validate.EmailValidation;

import freemarker.core.InvalidReferenceException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service

public class EmailServiceImplDao implements EmailService {

	@Autowired
	private EmailValidation evalidate;

	@Autowired
	private RabbitMQSender rsender;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private Configuration config;

	@Autowired
	private EmailRepository eresp;

	@Autowired
	private EmailStatusRepository essresp;

	@Value("${spring.mail.username}")
	private String sender;

	public void rabbitlistener(EmailDetailsDto detailsDto) throws InvalidEmailException, InvalidReferenceException {

		sendSimpleMail(detailsDto);
	}

	public String rabbitsender(EmailDetails details) {
		ModelMapper modelMapper = new ModelMapper();
		EmailDetailsDto edto = modelMapper.map(details, EmailDetailsDto.class);
		rsender.send(edto);
		return "Message sent to RabbitMQ successfully";
	}

	@Override
	public void sendSimpleMail(EmailDetailsDto detailsDto) throws InvalidEmailException, InvalidReferenceException {
		// First Saving the entity object in the database
		String temp;
		String temp2 = null;
		ModelMapper modelMapper = new ModelMapper();
		EmailDetails details = modelMapper.map(detailsDto, EmailDetails.class);
		temp = saveMailRecord(details);

		// Now Process of sending the mail and save the status in another
		// table(EmailSentStatus)

		// Map object for injecting variable data in freemarker template
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("mssg", detailsDto.getMsgBody());
		model.put("status", detailsDto.getStatus());
		EmailSentStatus ess = new EmailSentStatus();
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			Template t = config.getTemplate("emailTemplate.ftl");
			config.setTemplateExceptionHandler(new MyTemplateExceptionHandler());
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
			// Setting details for sending the mail
			MimeMessageHelper mailMessage = new MimeMessageHelper(message,
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
			evalidate.validate(detailsDto);
			mailMessage.setFrom(sender);
			mailMessage.setTo(detailsDto.getRecipient());
			mailMessage.setText(html, true);
			if (detailsDto.getStatus().equals("success")) {
				mailMessage.setSubject("Email Received from SpringBoot and RabbitMQ Application");
			} else {
				mailMessage.setSubject("Error in Mail receiving");
			}
			javaMailSender.send(message); // actually sending the mail
			// Saving details of Email Status in another table.
			if (StaticVariable.isTemplateException()) {
				ess.setSender(sender);
				ess.setMessageStatus("Sent Successfully But with Template Error");
				ess.setReceiver(detailsDto.getRecipient());
				ess.setRemarks("Template Error");
				temp2 = saveSentRecord(ess);

			} else {
				ess.setSender(sender);
				ess.setMessageStatus("Sent Successfully");
				ess.setReceiver(detailsDto.getRecipient());
				ess.setRemarks(detailsDto.getStatus() + " message");
				temp2 = saveSentRecord(ess);
			}
		}
		// If any kind of exception occurs
		catch (MessagingException | InvalidEmailException | IOException | TemplateException e) {
			ess.setSender(sender);
			ess.setMessageStatus("Error Occured");
			ess.setReceiver(detailsDto.getRecipient());
			ess.setRemarks(e.getMessage());
			temp2 = saveSentRecord(ess);
		}
		System.out.println("Message Sent to RabbitMQ, then received in the Spring boot application\n"
				+ "The mail sent status is: " + ess.getMessageStatus() + " \nRemarks: " + ess.getRemarks());
	}
	
	public String saveMailRecord(EmailDetails details) {
		return eresp.save(details).toString();
	}
	
	public String saveSentRecord(EmailSentStatus ess) {
		return essresp.save(ess).toString();
	}

}
