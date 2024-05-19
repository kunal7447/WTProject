package com.example.wtProject1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.wtProject1")
public class WtProject1Application {

	public static void main(String[] args) {
		SpringApplication.run(WtProject1Application.class, args);
	}


}
