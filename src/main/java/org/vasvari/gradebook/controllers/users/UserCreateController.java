package org.vasvari.gradebook.controllers.users;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.vasvari.gradebook.dto.dataTypes.UsernameInput;
import org.vasvari.gradebook.service.UserService;
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
    private final InternalServerErrorHandler errorHandler;

    @FXML
    public TextField username;
    @FXML
    public Label usernameErrorLabel;
    @FXML
    public Button emptyFormButton;
    @FXML
    public Button saveButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info("initialize UserCreateController");
        addEventListenerToFields();
    }

    private void addEventListenerToFields() {
        username.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if ((oldValue != null && !oldValue.equals(newValue)) || (oldValue == null && newValue != null))
                usernameErrorLabel.setText("");
        });
    }

    public void saveUser() {
        validateFields();
        if (isAnyFieldInvalid()) return;

        UsernameInput usernameInput = new UsernameInput(username.getText());

        try {
            userService.createAdminUser(usernameInput);
            deleteFormFields();
        } catch (Exception e) {
            errorHandler.printErrorToLabel(e, usernameErrorLabel);
        }
    }

    private void validateFields() {
        validator.username(username, usernameErrorLabel);
    }

    @FXML
    public void emptyForm() {
        deleteFormFields();
        deleteErrorMessages();
    }

    private void deleteFormFields() {
        username.setText(null);
    }

    private void deleteErrorMessages() {
        usernameErrorLabel.setText(null);
    }

    private boolean isAnyFieldInvalid() {
        return !usernameErrorLabel.getText().isEmpty();
    }
}
