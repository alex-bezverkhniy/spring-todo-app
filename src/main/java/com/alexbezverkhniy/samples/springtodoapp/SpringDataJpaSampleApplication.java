package com.alexbezverkhniy.samples.springtodoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringDataJpaSampleApplication {

	public static final String DEFAULT_USER = "TodoAPI";

	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpaSampleApplication.class, args);
	}
}
