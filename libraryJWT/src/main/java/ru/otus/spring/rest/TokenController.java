package ru.otus.spring.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.service.TokenService;

@RestController
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;

    @PostMapping("/token")
    public String token(Authentication authentication) {
        System.out.println("GET JWT TOKEN");
        return tokenService.token(authentication);
    }
}
