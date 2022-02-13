package org.vasvari.gradebook.helpers;

import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.userdetails.UserDetails;

@Setter
@ToString
public class LoginResponse {
    private UserDetails userDetails;
    private Boolean loginSuccess;

    public LoginResponse() {
        setLoginSuccess(false);
    }

    public boolean isSuccess() {
        return loginSuccess;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }
}
