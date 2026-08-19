package sptech.school.BACK_END_JAVA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BackEndJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackEndJavaApplication.class, args);
	}
}