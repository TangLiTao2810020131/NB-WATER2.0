package com.ets.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:shiro.xml")
public class ShiroConfig {

}