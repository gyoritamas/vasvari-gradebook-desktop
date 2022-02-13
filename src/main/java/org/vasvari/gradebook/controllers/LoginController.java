package org.vasvari.gradebook.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.vasvari.gradebook.JavaFxApplication;
import org.vasvari.gradebook.helpers.LoginRequest;
import org.vasvari.gradebook.helpers.LoginResponse;
import org.vasvari.gradebook.service.LoginService;

import java.io.IOException;

@Component
@FxmlView("../view/fxml/login.fxml")
public class LoginController {
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @FXML
    private TextField username;

    @FXML
    // TODO: change to passwordfield
    private TextField password;

    @FXML
    private Label resultLabel;

    @FXML
    private void login(ActionEvent actionEvent) throws IOException {
        String usernameInput = username.getText();
        String passwordInput = password.getText();

        LoginRequest loginRequest = new LoginRequest(usernameInput, passwordInput);
        //this.personLabel.setText(personService.findPersonById(1L).toString());
        //this.personLabel.setText(personService.findPersonByName(nameToSearch).toString());

        LoginResponse loginResponse = loginResult(loginRequest);
        if (!loginResponse.isSuccess()) {
            resultLabel.setText("Invalid username or password.");
        } else {
            loginService.setActiveUser(loginResponse.getUserDetails());
            JavaFxApplication.setRoot(MainController.class);
        }
    }

    @FXML
    private void loadRegistration(ActionEvent actionEvent) throws IOException {
        JavaFxApplication.setRoot(RegistrationController.class);
    }

    private LoginResponse loginResult(LoginRequest loginRequest) {
        return loginService.login(loginRequest);
    }
}
