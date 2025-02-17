package com.springboot.booking;

import com.springboot.booking.service.FileService;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import vn.library.common.utils.FileUtil;

import java.nio.file.Paths;

import static com.springboot.booking.constant.AppConst.UPLOAD_PATH;

@SpringBootApplication
@EnableScheduling
public class BookingWebsiteFullstackSpringAngularApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(BookingWebsiteFullstackSpringAngularApplication.class, args);
  }

  @Override
  public void run(String... arg) {
    FileUtil.createStorageDirectory(Paths.get(UPLOAD_PATH).toAbsolutePath().normalize());
  }
}
