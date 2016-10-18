package com.bugtrack.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.bugtrack.admin.util.BugTrackerProperty;


@SpringBootApplication
@EnableConfigurationProperties({BugTrackerProperty.class})
public class BugTrackAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(BugTrackAdminApplication.class, args);
	}
}
