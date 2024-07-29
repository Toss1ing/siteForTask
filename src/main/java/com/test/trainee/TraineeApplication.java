package com.test.trainee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.test.trainee.repository")
public class TraineeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TraineeApplication.class, args);
	}

}
