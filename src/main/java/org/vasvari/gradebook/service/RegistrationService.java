package org.vasvari.gradebook.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.vasvari.gradebook.model.User;
import org.vasvari.gradebook.model.UserRole;
import org.vasvari.gradebook.helpers.EmailValidator;
import org.vasvari.gradebook.helpers.PasswordValidator;
import org.vasvari.gradebook.helpers.RegistrationRequest;
import org.vasvari.gradebook.helpers.UsernameValidator;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final UserService userService;
    private final PasswordValidator passwordValidator;
    private final UsernameValidator usernameValidator;
    private final EmailValidator emailValidator;

    public String register(RegistrationRequest request) {
        testUsername(request);
        testPassword(request);
        testEmail(request);

        return userService.registerUser(
                new User(
                        request.getUsername(),
                        request.getPassword(),
                        UserRole.USER,
                        false,
                        true
                )
        );
    }

    private void testUsername(RegistrationRequest request) {
        boolean isValidUsername = usernameValidator.test(request.getUsername());

        if (!isValidUsername)
            throw new IllegalStateException("username is not valid");
    }

    private void testPassword(RegistrationRequest request) {
        boolean isValidPassword = passwordValidator.test(request.getPassword());

        if (!isValidPassword)
            throw new IllegalStateException("password is not valid");
    }

    private void testEmail(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if (!isValidEmail)
            throw new IllegalStateException("email is not valid");
    }
}
