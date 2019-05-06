package com.petio.petIO.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.petio.petIO.web.CORSInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Value("${file.rootPath}")
    private String ROOT_PATH;

	@Bean
	public CORSInterceptor getCORSInterceptor() {
		return new CORSInterceptor();
	}

	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(getCORSInterceptor()).addPathPatterns("/**");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
//		String filePath = "file:"+ROOT_PATH+SON_PATH;
//		registry.addResourceHandler("/img/**").addResourceLocations("file:F:/Document/petIO/target/classes/static/images/upload/");
//		registry.addResourceHandler("img/**").addResourceLocations("classpath:/static/images/upload/");
//		WebMvcConfigurer.super.addResourceHandlers(registry);
	}
	
}
