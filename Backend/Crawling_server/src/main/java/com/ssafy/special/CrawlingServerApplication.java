package com.ssafy.special;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CrawlingServerApplication {	
	public static void main(String[] args) {
		SpringApplication.run(CrawlingServerApplication.class, args);
	}
	@PostConstruct
	public void initApplication() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}
}
