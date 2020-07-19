package com.cloudspend.accountingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AccountingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountingSystemApplication.class, args);
	}

}
