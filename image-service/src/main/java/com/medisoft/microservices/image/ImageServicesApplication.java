package com.medisoft.microservices.image;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ImageServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImageServicesApplication.class, args);
	}
}
