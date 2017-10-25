package com.gary.sideCar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableSidecar
public class SideCarApplication {

	public static void main(String[] args) {
		SpringApplication.run(SideCarApplication.class, args);

	}

}
