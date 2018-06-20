package com.pfchoice.springboot.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@ConfigurationProperties("envProperties")
public class ConfigProperties {

	@NonNull private  String coordinatorEmail;
	
	@NonNull private  String coordinatorName;


}