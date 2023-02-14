package com.douzone.mysite.config.web;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageResourceConfig {
	
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("com/douzone/mysite/web/messages/messages_ko","com/douzone/mysite/web/messages/messages_ko");
		messageSource.setDefaultEncoding("utf-8");
		
		return messageSource;
	}

}
