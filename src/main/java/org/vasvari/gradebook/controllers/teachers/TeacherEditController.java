package org.vasvari.gradebook.controllers.teachers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
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

    private Long selectedId;

    @FXML
    public GridPane teacherEditPane;
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
        log.info("initialize TeacherEditController");
        addEventFilterToFields();
    }

    private void addEventFilterToFields() {
        lastName.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (selectedId != null && (oldValue != null && !oldValue.equals(newValue)) || (oldValue == null && newValue != null))
                validator.lastname(lastName, lastnameErrorLabel);
            updateButton.setDisable(isAnyFieldInvalid());
        });
        firstName.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (selectedId != null && (oldValue != null && !oldValue.equals(newValue)) || (oldValue == null && newValue != null))
                validator.firstname(firstName, firstnameErrorLabel);
            updateButton.setDisable(isAnyFieldInvalid());
        });
        email.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (selectedId != null && (oldValue != null && !oldValue.equals(newValue)) || (oldValue == null && newValue != null))
                validator.email(email, emailErrorLabel);
            updateButton.setDisable(isAnyFieldInvalid());
        });
        address.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (selectedId != null && (oldValue != null && !oldValue.equals(newValue)) || (oldValue == null && newValue != null))
                validator.address(address, addressErrorLabel);
            updateButton.setDisable(isAnyFieldInvalid());
        });
        phone.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (selectedId != null && (oldValue != null && !oldValue.equals(newValue)) || (oldValue == null && newValue != null))
                validator.phone(phone, phoneErrorLabel);
            updateButton.setDisable(isAnyFieldInvalid());
        });
        birthdate.getEditor().textProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (selectedId != null && (oldValue != null && !oldValue.equals(newValue)) || (oldValue == null && newValue != null))
                validator.birthdate(birthdate, birthdateErrorLabel);
            updateButton.setDisable(isAnyFieldInvalid());
        }));
    }

    private boolean isAnyFieldInvalid() {
        return !lastnameErrorLabel.getText().isEmpty() || !firstnameErrorLabel.getText().isEmpty()
                || !emailErrorLabel.getText().isEmpty() || !addressErrorLabel.getText().isEmpty()
                || !phoneErrorLabel.getText().isEmpty() || !birthdateErrorLabel.getText().isEmpty();
    }

    public void updateTeacher() {
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
    }

    public void emptyEditForm() {
        selectedId = null;
        firstName.setText(null);
        lastName.setText(null);
        email.setText(null);
        address.setText(null);
        phone.setText(null);
        birthdate.setValue(null);
        teacherEditPane.setDisable(true);
    }

    public void populateEditForm(TeacherDto selectedTeacher) {
        teacherEditPane.setDisable(false);
        selectedId = selectedTeacher.getId();
        firstName.setText(selectedTeacher.getFirstname());
        lastName.setText(selectedTeacher.getLastname());
        email.setText(selectedTeacher.getEmail());
        address.setText(selectedTeacher.getAddress());
        phone.setText(selectedTeacher.getPhone());
        birthdate.setValue(selectedTeacher.getBirthdate());
    }

    public void deleteTeacher() {
        if (selectedId == null) return;
        teacherService.deleteTeacher(selectedId);
    }

}
