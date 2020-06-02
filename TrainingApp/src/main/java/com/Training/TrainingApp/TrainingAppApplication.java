package com.Training.TrainingApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.Training")
@EnableJpaRepositories("com.Training.Dao")
@EntityScan("com.Training.Model")

public class TrainingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainingAppApplication.class, args);
	}

}
