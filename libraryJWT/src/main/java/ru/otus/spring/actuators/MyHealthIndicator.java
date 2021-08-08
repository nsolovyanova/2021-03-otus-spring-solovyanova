package ru.otus.spring.actuators;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class MyHealthIndicator implements HealthIndicator {

    private final Random random = new Random();

    @Override
    public Health health() {
        boolean isEmptyLibrary = random.nextBoolean();
        if (isEmptyLibrary) {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "Библиотека почему то пустая!")
                    .build();
        } else {
            return Health.up().withDetail("message", "Можно и почитать!").build();
        }
    }
}
