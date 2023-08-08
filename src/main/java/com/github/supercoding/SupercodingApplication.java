package com.github.supercoding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching //캐싱 가능하도록 설정
public class SupercodingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupercodingApplication.class, args);
	}

}
