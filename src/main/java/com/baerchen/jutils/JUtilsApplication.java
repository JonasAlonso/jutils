package com.baerchen.jutils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class JUtilsApplication {

	public static void main(String[] args) {
		SpringApplication.run(JUtilsApplication.class, args);
	}

}
