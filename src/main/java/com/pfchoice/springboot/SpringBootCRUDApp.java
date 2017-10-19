package com.pfchoice.springboot;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import com.pfchoice.springboot.configuration.JpaConfiguration;

@Import(JpaConfiguration.class)
@SpringBootApplication(scanBasePackages = { "com.pfchoice.springboot" }) // same
																			// as
																			// @Configuration
																			// @EnableAutoConfiguration
																			// @ComponentScan
@EnableAsync
public class SpringBootCRUDApp {

	 @PostConstruct
	  void started() {
	    TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
	  }

	 public static void main(String[] args) {
		SpringApplication.run(SpringBootCRUDApp.class, args);
		
	}

}
