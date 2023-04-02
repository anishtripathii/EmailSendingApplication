package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.dao.EmailServiceImplDao;
import com.example.demo.model.EmailDetails;
import com.example.demo.repository.EmailRepository;


@SpringBootTest

class EmailSendingApplicationTests {

	@Autowired
	EmailServiceImplDao edao;
	
	@Autowired
	EmailRepository erpo;
	
	@Test
	public void testEmaiDetailsCreate() {
		EmailDetails ed = new EmailDetails(1, "anishtrips22@yahoo.com", "ttyl", "success");
		erpo.save(ed);
		assertEquals(ed,new EmailDetails(1, "anishtrips22@yahoo.com", "ttyl", "success"));
	}
	
	
}
