package com.ahhmet.bankingSystem.kafka;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ahhmet.bankingSystem.service.IAccountService;

@Component
public class Consumer {
	
	@Autowired
	private IAccountService service;
	
	@KafkaListener(topics = "logs", groupId = "logs_consumer_group")
	public void listenTransfer(
			  @Payload String message) throws IOException {
		System.out.println(message);
		service.saveLogs(message);
	}
}

