package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class ShellService {
    private final TestService testService;

    @ShellMethod(value = "start", key = "start")
    public void startTesting()  {
        testService.startTest();
    }
}
