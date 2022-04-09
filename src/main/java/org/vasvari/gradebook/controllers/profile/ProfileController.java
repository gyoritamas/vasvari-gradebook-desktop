package org.vasvari.gradebook.controllers.profile;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.vasvari.gradebook.model.request.PasswordChangeRequest;
import org.vasvari.gradebook.service.UserService;
import org.vasvari.gradebook.util.EventListenerFactory;
import org.vasvari.gradebook.util.InternalServerErrorHandler;
import org.vasvari.gradebook.util.Validator;

import java.net.URL;
import java.util.ResourceBundle;

@FxmlView("view/fxml/profile/profile.fxml")
@Component
@RequiredArgsConstructor
@Slf4j
public class ProfileController implements Initializable {

    private final UserService userService;
    private final EventListenerFactory eventListenerFactory;
    private final Validator validator;
    private final InternalServerErrorHandler errorHandler;

    @FXML
    public PasswordField oldPassword;
    @FXML
    public Label oldPasswordErrorLabel;
    @FXML
    public PasswordField newPassword;
    @FXML
    public Label newPasswordErrorLabel;
    @FXML
    public PasswordField newPasswordRepeat;
    @FXML
    public Label newPasswordRepeatErrorLabel;

    @FXML
    public Button saveButton;
    @FXML
    public Label passwordChangeSuccessLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info("initialize ProfileController");
        addEventListenerToFields();
    }

    private void addEventListenerToFields() {
        eventListenerFactory.onTextFieldChangeDeleteErrorMessage(oldPassword, oldPasswordErrorLabel);
        eventListenerFactory.onTextFieldChangeDeleteErrorMessage(newPassword, newPasswordErrorLabel);
        eventListenerFactory.onTextFieldChangeDeleteErrorMessage(newPasswordRepeat, newPasswordRepeatErrorLabel);
    }

    @FXML
    public void changePassword() {
        validateFields();
        if (isAnyFieldInvalid()) return;

        PasswordChangeRequest request = new PasswordChangeRequest(oldPassword.getText(), newPassword.getText());
        try {
            userService.changePassword(request);
            emptyForm();
            printPasswordChangeSuccess();
        } catch (Exception ex) {
            errorHandler.printErrorToLabel(ex, oldPasswordErrorLabel);
        }
    }

    private void validateFields() {
        validator.password(newPassword, newPasswordErrorLabel);
        validator.passwordsMatch(newPassword, newPasswordRepeat, newPasswordRepeatErrorLabel);
    }

    private void printPasswordChangeSuccess() {
        passwordChangeSuccessLabel.setText("A jelszó megváltozott.");
    }

    private void emptyForm() {
        deleteFields();
        deleteErrorMessages();
    }

    private void deleteFields() {
        oldPassword.setText(null);
        newPassword.setText(null);
        newPasswordRepeat.setText(null);
    }

    private void deleteErrorMessages() {
        oldPasswordErrorLabel.setText(null);
        newPasswordErrorLabel.setText(null);
        newPasswordRepeatErrorLabel.setText(null);
        passwordChangeSuccessLabel.setText(null);
    }

    private boolean isAnyFieldInvalid() {
        return !oldPasswordErrorLabel.getText().isEmpty()
                || !newPasswordErrorLabel.getText().isEmpty()
                || !newPasswordRepeatErrorLabel.getText().isEmpty();
    }
}
