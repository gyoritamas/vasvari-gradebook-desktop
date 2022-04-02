package org.vasvari.gradebook.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.vasvari.gradebook.jwt.JwtTokenInjectionInterceptor;

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class ClientConfig {

    private final JwtTokenInjectionInterceptor interceptor;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(interceptor));

        return restTemplate;
    }
}
