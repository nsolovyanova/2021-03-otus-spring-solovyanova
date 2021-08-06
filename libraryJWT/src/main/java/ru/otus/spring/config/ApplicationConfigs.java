package ru.otus.spring.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;


@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "application")
public class ApplicationConfigs {
    private Locale locale;

    public void setDefaultLocaleTag(String localeTag) {
        setLocale(localeTag);
    }

    public void setLocale(String localeTag) {
        locale = Locale.forLanguageTag(localeTag);
    }
}
