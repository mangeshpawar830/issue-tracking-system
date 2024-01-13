package com.demo.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		java.security.Security.setProperty("jdk.tls.disabledAlgorithms", "");
		SpringApplication.run(DemoApplication.class, args);
	}

}
