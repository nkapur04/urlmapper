package com.sample.url.utility.mapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableRetry
@EnableCaching
public class MapperApplication {

	public static void main(String[] args) {
		SpringApplication.run(MapperApplication.class, args);
	}

}
