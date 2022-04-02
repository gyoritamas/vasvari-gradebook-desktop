package org.vasvari.gradebook.jwt;

import org.springframework.stereotype.Repository;

@Repository
public class TokenRepository {
    private JwtToken jwtToken;

    public JwtToken getToken() {
        return this.jwtToken;
    }

    public void setToken(String token) {
        this.jwtToken = new JwtToken(token);
    }

    public void deleteToken(){
        this.jwtToken = null;
    }
}
