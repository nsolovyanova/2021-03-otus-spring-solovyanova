package ru.otus.spring.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@ConfigurationProperties("application")
@Data
public class ApplicationConfigs {
    private int countSuccessfulAnswers;
    private Locale locale;
    private String csvFileName;
}
