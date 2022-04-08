package org.vasvari.gradebook.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.vasvari.gradebook.JavaFxApplication;
import org.vasvari.gradebook.dto.LoginRequest;
import org.vasvari.gradebook.service.gateway.LoginGateway;
import org.vasvari.gradebook.util.EventListenerFactory;
import org.vasvari.gradebook.util.InternalServerErrorHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("../view/fxml/login.fxml")
@Slf4j
@RequiredArgsConstructor
public class LoginController implements Initializable {
    private final LoginGateway loginService;
    private final EventListenerFactory eventListenerFactory;
    private final InternalServerErrorHandler errorHandler;

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JavaFxApplication.getTheStage().setTitle("E-napló bejelentkezés");
        JavaFxApplication.getTheStage().setHeight(250);
        JavaFxApplication.getTheStage().setWidth(325);
        addEventListenerToFields();
    }

    private void addEventListenerToFields() {
        eventListenerFactory.onTextFieldChangeDeleteErrorMessage(username, errorLabel);
        eventListenerFactory.onTextFieldChangeDeleteErrorMessage(password, errorLabel);
    }

    @FXML
    private void login() {
        String usernameInput = username.getText();
        String passwordInput = password.getText();

        LoginRequest loginRequest = new LoginRequest(usernameInput, passwordInput);

        try {
            loginService.login(loginRequest);
            JavaFxApplication.setRoot(MainController.class);
        } catch (HttpClientErrorException ex) {
            errorHandler.printErrorToLabel(ex, errorLabel);
        } catch (RuntimeException ex) {
            if (ex.getMessage().contains("Connection refused"))
                errorLabel.setText("Sikertelen kapcsolódás.");
        } catch (Exception ex) {
            errorHandler.printErrorToLabel(ex, errorLabel);
        }
    }
}