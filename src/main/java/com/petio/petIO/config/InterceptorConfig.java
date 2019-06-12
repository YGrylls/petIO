package com.petio.petIO.config;

import java.io.File;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
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

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
//		String filePath = "file:"+ROOT_PATH+SON_PATH;
		String absolutePath = "file:";
		File path;
		try {
			path = new File(ResourceUtils.getURL("classpath:").getPath());
			if (!path.exists()) {
				path = new File("");
			}
			System.out.println("path:" + path.getAbsolutePath());
			File upload = new File(path.getAbsolutePath(), "/home/img/");
			if (!upload.exists()) {
				upload.mkdirs();
			}
			System.out.println("upload url:" + upload.getAbsolutePath());
			absolutePath += (upload.getAbsolutePath().replace('\\', '/') + '/');
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("absolutePath:" + absolutePath);
		registry.addResourceHandler("/image/**").addResourceLocations(absolutePath);
//		WebMvcConfigurer.super.addResourceHandlers(registry);
	}

}
