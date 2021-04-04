package ru.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.service.QuestionService;

public class Main {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");

        QuestionService service = context.getBean(QuestionService.class);
        service.runTest();
        context.close();
    }
}
