package org.vasvari.gradebook.service.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.vasvari.gradebook.dto.LoginRequest;
import org.vasvari.gradebook.dto.LoginResponse;
import org.vasvari.gradebook.jwt.TokenRepository;

@Service
public class LoginGateway {
    @Value("${api.url}")
    private String baseUrl;

    private final TokenRepository tokenRepository;
    private final RestTemplate template;

    // not using the restTemplate bean from ClientConfig here
    // since we don't want to send any token
    public LoginGateway(TokenRepository tokenRepository, RestTemplateBuilder builder) {
        this.tokenRepository = tokenRepository;
        this.template = builder.build();
    }

    public void login(LoginRequest loginRequest) {
        LoginResponse response = template.postForObject(baseUrl + "/authenticate", loginRequest, LoginResponse.class);
        if (response == null) throw new RuntimeException("Authentication failed");
        String token = response.getJwtToken();
        tokenRepository.setToken(token);
    }

    public void logout() {
        tokenRepository.deleteToken();
    }
}
