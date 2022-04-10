package org.vasvari.gradebook.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.vasvari.gradebook.jwt.TokenRepository;

import java.io.Serializable;
import java.util.function.Function;

@Component
@ConfigurationProperties(prefix = "application.jwt")
public class JwtTokenUtil implements Serializable {

    @Value("${jwt.secret}")
    private String secret;

    private final TokenRepository tokenRepository;

    public JwtTokenUtil(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public String getUserRoleFromToken(String token) {
        JSONObject claims = new JSONObject(getAllClaimsFromToken(token));
        try {
            JSONArray authorities = claims.getJSONArray("authorities");
            String fullRoleName = (String) authorities.getJSONObject(0).get("authority");

            return fullRoleName.substring("ROLE_".length());
        } catch (JSONException e) {
            throw new RuntimeException("Error reading token: " + e.getMessage());
        }
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public HttpHeaders getAuthorizationHeaderWithToken() {
        HttpHeaders headers = new HttpHeaders();
        if (tokenRepository.getToken() != null) {
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + tokenRepository.getToken().getTokenString());
        }
        return headers;
    }
}