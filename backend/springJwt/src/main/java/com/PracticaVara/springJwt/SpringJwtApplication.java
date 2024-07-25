package com.PracticaVara.springJwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringJwtApplication {


	public static void main(String[] args) {

		SpringApplication.run(SpringJwtApplication.class, args);
	}

}
