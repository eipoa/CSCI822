package com.bugtrack.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.bugtrack.app.util.BugTrackerProperty;


@SpringBootApplication
@EnableConfigurationProperties({BugTrackerProperty.class})
public class BugTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BugTrackerApplication.class, args);
	}
}
