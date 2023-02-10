package com.ronald.datajpa;

//import java.nio.file.Paths;
import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class MvcConfig implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		String resourcePath = Paths.get("uploads").toAbsolutePath().toUri().toString();
//
//		
//		registry.addResourceHandler("/uploads/**").addResourceLocations(resourcePath);
////		.addResourceLocations("file:/C:/Temp/uploads/");
//		
//		
		WebMvcConfigurer.super.addResourceHandlers(registry);

	}

	public void addViewControllers(ViewControllerRegistry registry){
		registry.addViewController("/error_403").setViewName("error_403");
	}
	
	//para manejar el cambio de idioma
	
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(new Locale("es", "ES"));
		return localeResolver;
	}
	
	
	public LocaleChangeInterceptor localChangeInterceptor() {
		LocaleChangeInterceptor localInterceptor = new LocaleChangeInterceptor();
		localInterceptor.setParamName("lang");
		return localInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localChangeInterceptor());
		WebMvcConfigurer.super.addInterceptors(registry);
	}
	
	
	@Bean //para convertir los objectos a xml
	public Jaxb2Marshaller jaxb2Marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(new Class[] {com.ronald.datajpa.view.xml.ClienteList.class});
//		marshaller.setPackagesToScan("com.ronald.datajpa.models.entity");
		return marshaller;
	}

	
	
}
