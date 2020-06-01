package com.tutorial.SpringBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@ComponentScan("com.tutorial")
@EnableJpaRepositories("com.tutorial.Dao")
@EntityScan("com.tutorial")
@EnableCaching
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800)

public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
