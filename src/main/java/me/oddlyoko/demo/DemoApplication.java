package me.oddlyoko.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class DemoApplication {
	public static final String DEFAULT_PAGE_SIZE = "30";

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
