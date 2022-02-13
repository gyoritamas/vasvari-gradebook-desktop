package org.vasvari.gradebook.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.vasvari.gradebook.helpers.LoginRequest;
import org.vasvari.gradebook.helpers.LoginResponse;
import org.vasvari.gradebook.model.User;
import org.vasvari.gradebook.model.UserRole;

import java.util.Optional;

@Service
//@AllArgsConstructor
public class LoginService {
    private UserDetails activeUser;
    private final UserService userService;

    @Autowired
    public LoginService(UserService userService) {
        this.userService = userService;
    }

    public LoginResponse login(LoginRequest request) {

        String username = request.getUsername();
        String password = request.getPassword();

        LoginResponse response = new LoginResponse();

        UserDetails userDetails = userService.loadUserByUsername(username);

        if (userDetails != null && userService.isPasswordCorrect(userDetails, password)) {
            response.setLoginSuccess(true);
            response.setUserDetails(userDetails);
        }

        return response;
    }

    public void setActiveUser(UserDetails user) {
        activeUser = user;
    }

    public Optional<UserDetails> getActiveUser() {
        return Optional.ofNullable(activeUser);
    }
}
