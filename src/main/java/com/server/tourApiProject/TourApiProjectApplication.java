package com.server.tourApiProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableScheduling
public class TourApiProjectApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(TourApiProjectApplication.class);
		application.addListeners(new ApplicationPidFileWriter());
		application.run(args);
	}

}
