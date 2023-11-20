package com.springboot.booking;

import com.springboot.booking.service.FileService;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BookingWebsiteFullstackSpringAngularApplication implements CommandLineRunner {

	@Resource
	FileService fileService;

	public static void main(String[] args) {
		SpringApplication.run(BookingWebsiteFullstackSpringAngularApplication.class, args);
	}

	@Override
	public void run(String... arg) throws Exception {
		fileService.init();
	}
}
