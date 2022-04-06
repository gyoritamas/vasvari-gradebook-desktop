package org.vasvari.gradebook.controllers.teachers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.vasvari.gradebook.dto.TeacherDto;
import org.vasvari.gradebook.service.TeacherService;
import org.vasvari.gradebook.util.Validator;

import java.net.URL;
import java.util.ResourceBundle;

@FxmlView("view/fxml/teachers/teacherCreate.fxml")
@Component
@Slf4j
@RequiredArgsConstructor
public class TeacherCreateController implements Initializable {
    private final TeacherService teacherService;
    private final Validator validator;

    @FXML
    public TextField lastName;
    @FXML
    public TextField firstName;
    @FXML
    public TextField email;
    @FXML
    public TextArea address;
    @FXML
    public TextField phone;
    @FXML
    public DatePicker birthdate;
    @FXML
    public Label lastnameErrorLabel;
    @FXML
    public Label firstnameErrorLabel;
    @FXML
    public Label emailErrorLabel;
    @FXML
    public Label addressErrorLabel;
    @FXML
    public Label phoneErrorLabel;
    @FXML
    public Label birthdateErrorLabel;
    @FXML
    public Button saveButton;
    @FXML
    public Button emptyFormButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info("initialize TeacherCreateController");
        addEventFilterToSaveButton();
        addEventListenersToFields();
    }

    private void addEventFilterToSaveButton() {
        saveButton.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            validator.lastname(lastName, lastnameErrorLabel);
            validator.firstname(firstName, firstnameErrorLabel);
            validator.email(email, emailErrorLabel);
            validator.address(address, addressErrorLabel);
            validator.phone(phone, phoneErrorLabel);
            validator.birthdate(birthdate, birthdateErrorLabel);
            saveTeacher();
        });
    }

    private void addEventListenersToFields() {
        lastName.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) lastnameErrorLabel.setText("");
        });
        firstName.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) firstnameErrorLabel.setText("");
        });
        email.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) emailErrorLabel.setText("");
        });
        address.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) addressErrorLabel.setText("");
        });
        phone.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) phoneErrorLabel.setText("");
        });
        birthdate.getEditor().textProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) birthdateErrorLabel.setText("");
        }));
    }

    private void saveTeacher() {
        if (isAnyFieldInvalid()) return;

        TeacherDto teacher = TeacherDto.builder()
                .lastname(lastName.getText())
                .firstname(firstName.getText())
                .email(email.getText())
                .address(address.getText())
                .phone(phone.getText())
                .birthdate(birthdate.getValue())
                .build();

        teacherService.saveTeacher(teacher);
        deleteFormFields();
    }

    public void emptyForm(ActionEvent actionEvent) {
        deleteFormFields();
        deleteErrorMessages();
    }

    private void deleteFormFields() {
        firstName.setText(null);
        lastName.setText(null);
        email.setText(null);
        address.setText(null);
        phone.setText(null);
        birthdate.setValue(null);
    }

    private void deleteErrorMessages() {
        firstnameErrorLabel.setText(null);
        lastnameErrorLabel.setText(null);
        emailErrorLabel.setText(null);
        addressErrorLabel.setText(null);
        phoneErrorLabel.setText(null);
        birthdateErrorLabel.setText(null);
    }

    private boolean isAnyFieldInvalid() {
        return !lastnameErrorLabel.getText().isEmpty() || !firstnameErrorLabel.getText().isEmpty()
                || !emailErrorLabel.getText().isEmpty() || !addressErrorLabel.getText().isEmpty()
                || !phoneErrorLabel.getText().isEmpty() || !birthdateErrorLabel.getText().isEmpty();
    }
}
