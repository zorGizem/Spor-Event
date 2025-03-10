package com.mehmetvasfi.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EntityScan(basePackages = {"com.mehmetvasfi"})
@ComponentScan(basePackages = {"com.mehmetvasfi"})
@EnableJpaRepositories(basePackages = {"com.mehmetvasfi"} )
public class SporEventApplication {

	public static void main(String[] args) {
		SpringApplication.run(SporEventApplication.class, args);
	}

}
