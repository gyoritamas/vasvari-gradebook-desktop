package org.vasvari.gradebook.jwt;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtTokenInjectionInterceptor implements ClientHttpRequestInterceptor {

    private final TokenRepository tokenRepository;

    public JwtTokenInjectionInterceptor(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request,
                                        byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();

        if (tokenRepository.getToken() != null) {
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + tokenRepository.getToken().getTokenString());
        }

        return execution.execute(request, body);
    }
}
