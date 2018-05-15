package com.pfchoice.springboot.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.pfchoice.springboot.model.advisor"})
public class AOPConfig {
}