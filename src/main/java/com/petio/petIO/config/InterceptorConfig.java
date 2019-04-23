package com.petio.petIO.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.petio.petIO.web.CORSInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	@Bean
	public CORSInterceptor getCORSInterceptor() {
		return new CORSInterceptor();
	}

	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(getCORSInterceptor()).addPathPatterns("/**");
	}
}
