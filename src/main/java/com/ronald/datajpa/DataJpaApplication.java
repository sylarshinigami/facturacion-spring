package com.ronald.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ronald.datajpa.models.service.IUploadFileService;

@SpringBootApplication
public class DataJpaApplication implements CommandLineRunner{

	
	@Autowired
	IUploadFileService uploadService;
//	
//	@Autowired
//	private BCryptPasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(DataJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		uploadService.deleteAll();
//		uploadService.init();
		
//		String password = "12345";
//		for(int i= 0; i < 2; i++) {
//			String bcryptPassword = passwordEncoder.encode(password);
//			System.out.println(bcryptPassword);
//		}
	}

}
