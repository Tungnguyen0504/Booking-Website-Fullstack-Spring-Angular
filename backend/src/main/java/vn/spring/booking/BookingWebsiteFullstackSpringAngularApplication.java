package vn.spring.booking;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import vn.spring.common.utils.FileUtil;

import java.nio.file.Paths;

import static vn.spring.booking.constant.AppConst.UPLOAD_PATH;

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
