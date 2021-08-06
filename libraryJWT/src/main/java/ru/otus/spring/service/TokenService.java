package ru.otus.spring.service;

import org.springframework.security.core.Authentication;

public interface TokenService {
    String token(Authentication authentication);

}
