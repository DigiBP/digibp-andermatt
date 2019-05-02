package ch.fhnw.digibp;

import org.apache.ibatis.logging.LogFactory;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProcessApplication
public class ProcessApplication {

	public static void main(String[] args) {
		LogFactory.useSlf4jLogging();
		SpringApplication.run(ProcessApplication.class, args);
	}

}