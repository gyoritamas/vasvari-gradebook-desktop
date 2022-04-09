package org.vasvari.gradebook.controllers.users;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.vasvari.gradebook.dto.dataTypes.InitialCredentials;
import org.vasvari.gradebook.dto.dataTypes.UsernameInput;
import org.vasvari.gradebook.service.UserService;
import org.vasvari.gradebook.util.EventListenerFactory;
import org.vasvari.gradebook.util.InternalServerErrorHandler;
import org.vasvari.gradebook.util.Validator;

import java.net.URL;
import java.util.ResourceBundle;

@FxmlView("view/fxml/users/userCreate.fxml")
@Component
@Slf4j
@RequiredArgsConstructor
public class UserCreateController implements Initializable {

    private final UserService userService;
    private final Validator validator;
    private final EventListenerFactory eventListenerFactory;
    private final InternalServerErrorHandler errorHandler;

    @FXML
    public TextField username;
    @FXML
    public Label usernameErrorLabel;
    @FXML
    public Button saveButton;

    @FXML
    public Label credentialsLabel;
    @FXML
    public Label usernameLabel;
    @FXML
    public TextField usernameField;
    @FXML
    public Label passwordLabel;
    @FXML
    public TextField passwordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info("initialize UserCreateController");
        addEventListenerToFields();
        hideCredentials();
    }

    private void addEventListenerToFields() {
        eventListenerFactory.onTextFieldChangeDeleteErrorMessage(username, usernameErrorLabel);
    }

    public void saveUser() {
        validateFields();
        if (isAnyFieldInvalid()) return;

        UsernameInput usernameInput = new UsernameInput(username.getText());

        try {
            InitialCredentials credentials = userService.createAdminUser(usernameInput);
            showCredentials(credentials);
        } catch (Exception e) {
            errorHandler.printErrorToLabel(e, usernameErrorLabel);
        }
    }

    private void validateFields() {
        validator.username(username, usernameErrorLabel);
    }

    private boolean isAnyFieldInvalid() {
        return !usernameErrorLabel.getText().isEmpty();
    }

    public void emptyForm(){
        deleteFormFields();
        deleteErrorMessages();
        hideCredentials();
    }

    private void deleteFormFields() {
        username.setText(null);
        usernameField.setText(null);
        passwordField.setText(null);
    }

    private void deleteErrorMessages() {
        usernameErrorLabel.setText(null);
    }

    private void hideCredentials() {
        credentialsLabel.setVisible(false);
        usernameLabel.setVisible(false);
        usernameField.setVisible(false);
        passwordLabel.setVisible(false);
        passwordField.setVisible(false);
    }

    private void showCredentials(InitialCredentials credentials) {
        credentialsLabel.setVisible(true);
        usernameLabel.setVisible(true);
        usernameField.setVisible(true);
        passwordLabel.setVisible(true);
        passwordField.setVisible(true);
        usernameField.setText(credentials.getUsername());
        passwordField.setText(credentials.getPassword());
    }
}
