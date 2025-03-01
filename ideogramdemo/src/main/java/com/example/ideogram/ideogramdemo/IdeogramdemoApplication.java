package com.example.ideogram.ideogramdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"com.example.ideogram"})
public class IdeogramdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdeogramdemoApplication.class, args);
	}

}
