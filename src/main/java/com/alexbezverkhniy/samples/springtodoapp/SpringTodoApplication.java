package com.alexbezverkhniy.samples.springtodoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringTodoApplication {

	public static final String DEFAULT_USER = "TodoAPI";

	public static void main(String[] args) {
		SpringApplication.run(SpringTodoApplication.class, args);
	}
}
