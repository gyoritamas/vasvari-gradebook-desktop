package org.vasvari.gradebook.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.vasvari.gradebook.JavaFxApplication;
import org.vasvari.gradebook.dto.LoginRequest;
import org.vasvari.gradebook.service.gateway.LoginGateway;

import java.io.IOException;

@Component
@FxmlView("../view/fxml/login.fxml")
@RequiredArgsConstructor
public class LoginController {
    private final LoginGateway loginService;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label errorLabel;

    @FXML
    private void login(ActionEvent actionEvent) throws IOException {
        String usernameInput = username.getText();
        String passwordInput = password.getText();

        LoginRequest loginRequest = new LoginRequest(usernameInput, passwordInput);

        try {
            loginService.login(loginRequest);
            JavaFxApplication.setRoot(MainController.class);
        } catch (HttpClientErrorException ex) {
            if (ex.getResponseBodyAsString().contains("Bad credentials")) {
                errorLabel.setText("Hibás felhasználónév vagy jelszó.");
            }
        }
    }
}
