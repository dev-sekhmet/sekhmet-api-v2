package com.sekhmet.api.sekhmetapi;

import com.sekhmet.api.sekhmetapi.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class})
public class SekhmetApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SekhmetApiApplication.class, args);
	}

}
