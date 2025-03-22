package com.example.revisiones_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RevisionesMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RevisionesMsApplication.class, args);
	}

}
