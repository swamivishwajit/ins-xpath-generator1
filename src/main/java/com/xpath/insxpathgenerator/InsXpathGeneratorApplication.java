package com.xpath.insxpathgenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = "com")
public class InsXpathGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsXpathGeneratorApplication.class, args);
	}

}
