package com.PracticaVara.springJwt;

import com.PracticaVara.springJwt.config.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class SpringJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringJwtApplication.class, args);
	}

}
