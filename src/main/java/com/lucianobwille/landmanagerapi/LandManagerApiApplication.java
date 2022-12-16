package com.lucianobwille.landmanagerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/")
public class LandManagerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LandManagerApiApplication.class, args);
	}

	@GetMapping
	public String hello() {
		return "Hello, the server is up!";
	}

}
