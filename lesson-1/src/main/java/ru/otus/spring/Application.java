package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import ru.otus.spring.config.ApplicationConfigs;
import ru.otus.spring.service.TestService;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfigs.class)
public class Application {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);
		TestService service = ctx.getBean(TestService.class);
		service.startTest();
	}

}
