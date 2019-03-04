package com.lixl.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {
	    DataSourceAutoConfiguration.class
})
public class MytestApplication {

	public static void main(String[] args) {
		SpringApplication.run(MytestApplication.class, args);
	}

}
