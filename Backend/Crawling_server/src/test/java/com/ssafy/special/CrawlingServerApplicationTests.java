package com.ssafy.special;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.special.main.MainApplication;
import com.ssafy.special.service.DaangnCrawlingService;
import com.ssafy.special.service.JoongnaCrawlingService;

@SpringBootTest
class CrawlingServerApplicationTests {

	@Autowired
	DaangnCrawlingService daangnCrawlingService;
	@Autowired
	JoongnaCrawlingService joongnaCrawlingService;
	@Autowired
	MainApplication mainApplication;
	
	@Test
	void contextLoads() {
		daangnCrawlingService.crawlingProducts();
//		joongnaCrawlingService.joongnainit();
//		mainApplication.crawlingStart();
	}

}
