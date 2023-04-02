package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.EmailDetailsDto;
import com.example.demo.model.EmailDetails;

@Repository
public interface EmailRepository extends JpaRepository<EmailDetails, Integer>{

	

}
