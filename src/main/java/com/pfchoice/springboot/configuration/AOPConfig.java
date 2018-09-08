package com.pfchoice.springboot.configuration;

import java.util.Properties;

import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.pfchoice.springboot.security.LeadAccessDeniedHandler;

@Configuration
@ComponentScan(basePackages = {"com.pfchoice.springboot.model.advisor"})
public class AOPConfig {
	
	
	@Bean
	@Primary
	@ConfigurationProperties(prefix = "spring.mail")
	public MailProperties mailProperties() {
		return new MailProperties();
	}

	@Bean
	public JavaMailSender getMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		// Using gmail.
		mailSender.setHost(mailProperties().getHost());
		mailSender.setPort(mailProperties().getPort());
		mailSender.setUsername(mailProperties().getUsername());
		mailSender.setPassword(mailProperties().getPassword());

		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.starttls.enable",
				mailProperties().getProperties().get("mail.smtp.starttls.enable"));
		javaMailProperties.put("mail.smtp.auth", mailProperties().getProperties().get("mail.smtp.auth"));
		javaMailProperties.put("mail.transport.protocol",
				mailProperties().getProperties().get("mail.transport.protocol"));
		javaMailProperties.put("mail.debug", mailProperties().getProperties().get("mail.debug"));

		mailSender.setJavaMailProperties(javaMailProperties);
		return mailSender;
	}
	
	
	@Bean("leadAccessDeniedHandler")
	public LeadAccessDeniedHandler leadAccessDeniedHandler() {
		return new LeadAccessDeniedHandler();
	}
}