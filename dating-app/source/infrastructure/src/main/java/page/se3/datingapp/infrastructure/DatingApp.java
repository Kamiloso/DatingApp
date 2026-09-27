package page.se3.datingapp.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class)
@EnableScheduling
public class DatingApp {

	public static void main(String[] args) {
		SpringApplication.run(DatingApp.class, args);
	}
}
