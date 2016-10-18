package com.bugtrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.bugtrack.util.BugTrackerProperty;


@SpringBootApplication
@EnableConfigurationProperties({BugTrackerProperty.class})
public class BugTrackApplication {

	public static void main(String[] args) {
		SpringApplication.run(BugTrackApplication.class, args);
	}
}
