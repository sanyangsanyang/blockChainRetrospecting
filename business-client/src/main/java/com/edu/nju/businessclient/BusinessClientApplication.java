package com.edu.nju.businessclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BusinessClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusinessClientApplication.class, args);
	}
}
