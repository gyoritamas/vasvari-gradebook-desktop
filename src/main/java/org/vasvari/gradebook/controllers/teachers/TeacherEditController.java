package org.vasvari.gradebook.controllers.teachers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.vasvari.gradebook.dto.TeacherDto;
import org.vasvari.gradebook.service.TeacherService;
import org.vasvari.gradebook.util.Validator;

import java.net.URL;
import java.util.ResourceBundle;

@FxmlView("view/fxml/teachers/teacherEdit.fxml")
@Component
@Slf4j
@RequiredArgsConstructor
public class TeacherEditController implements Initializable {
    private final TeacherService teacherService;
    private final Validator validator;

    public Long selectedId;

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
    public Button updateButton;
    @FXML
    public Button deleteButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info("initialize TeacherFormController");
        addEventFilterToFields();
    }

    private void addEventFilterToFields() {
        lastName.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) validator.lastname(lastName, lastnameErrorLabel);
            updateButton.setDisable(isAnyFieldInvalid());
        });
        firstName.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) validator.firstname(firstName, firstnameErrorLabel);
            updateButton.setDisable(isAnyFieldInvalid());
        });
        email.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) validator.email(email, emailErrorLabel);
            updateButton.setDisable(isAnyFieldInvalid());
        });
        address.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) validator.address(address, addressErrorLabel);
            updateButton.setDisable(isAnyFieldInvalid());
        });
        phone.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) validator.phone(phone, phoneErrorLabel);
            updateButton.setDisable(isAnyFieldInvalid());
        });
        birthdate.getEditor().textProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) validator.birthdate(birthdate, birthdateErrorLabel);
            updateButton.setDisable(isAnyFieldInvalid());
        }));
    }

    public void updateTeacher(ActionEvent actionEvent) {
        if (selectedId == null) return;

        TeacherDto teacher = TeacherDto.builder()
                .lastname(lastName.getText())
                .firstname(firstName.getText())
                .email(email.getText())
                .address(address.getText())
                .phone(phone.getText())
                .birthdate(birthdate.getValue())
                .build();

        teacherService.updateTeacher(selectedId, teacher);
        deleteFormFields();
    }

    public void deleteTeacher(ActionEvent actionEvent) {
        if (selectedId == null) return;
        teacherService.deleteTeacher(selectedId);
    }

    private boolean isAnyFieldInvalid() {
        return !lastnameErrorLabel.getText().isEmpty() || !firstnameErrorLabel.getText().isEmpty()
                || !emailErrorLabel.getText().isEmpty() || !addressErrorLabel.getText().isEmpty()
                || !phoneErrorLabel.getText().isEmpty() || !birthdateErrorLabel.getText().isEmpty();
    }

    private void deleteFormFields() {
        firstName.setText("");
        lastName.setText("");
        email.setText("");
        address.setText("");
        phone.setText("");
        birthdate.editorProperty().getValue().setText("");
    }
}
