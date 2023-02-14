package com.douzone.mysite.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class FileuploadConfig implements WebMvcConfigurer {
	
	// Multipart Resolver
	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		// 최대업로드 가능한 바이트크기
		multipartResolver.setMaxUploadSize(52428800);
		// 디스크에 임시 파일을 생성하기 전에 메모리에 보관할수있는 최대 바이트 크기
		multipartResolver.setMaxInMemorySize(52428800);
		// defaultEncoding
		multipartResolver.setDefaultEncoding("utf-8");
		
		return multipartResolver;
	}
	
	// mvc url-resource mapping
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
			.addResourceHandler("/assets/upload-images/**")
			.addResourceLocations("file:/Users/nam-woogeun/mysite-uploads/");
	}
}
