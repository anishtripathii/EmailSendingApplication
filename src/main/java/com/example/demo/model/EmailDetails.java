package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.demo.exception.InvalidEmailException;

@Entity
@Table(name = "Email_Details")
public class EmailDetails {
		@Id
		@GeneratedValue(strategy = GenerationType.TABLE)
	    private long sNo; 
		private String recipient;
		private String msgBody;
		private String status;
		
		public EmailDetails(long sNo, String recipient, String msgBody, String status) {
			super();
			this.sNo = sNo;
			this.recipient = recipient;
			this.msgBody = msgBody;
			this.status = status;
		}

		public long getsNo() {
			return sNo;
		}

		public void setsNo(long sNo) {
			this.sNo = sNo;
		}

		public String getRecipient() {
			return recipient;
		}

		public void setRecipient(String recipient) {
			this.recipient = recipient;
		}

		public String getMsgBody() {
			return msgBody;
		}

		public void setMsgBody(String msgBody) {
			this.msgBody = msgBody;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		@Override
		public String toString() {
			return "EmailDetails [sNo=" + sNo + ", recipient=" + recipient + ", msgBody=" + msgBody + ", status="
					+ status + "]";
		}

		public EmailDetails() {
			super();
		}
		
	    
	}

