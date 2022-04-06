package org.vasvari.gradebook.controllers.students;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.vasvari.gradebook.dto.StudentDto;
import org.vasvari.gradebook.service.StudentService;
import org.vasvari.gradebook.util.Validator;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@FxmlView("view/fxml/students/studentCreate.fxml")
@Component
@Slf4j
@RequiredArgsConstructor
public class StudentCreateController implements Initializable {

    private final StudentService studentService;
    private final Validator validator;

    @FXML
    public TextField lastName;
    @FXML
    public TextField firstName;
    @FXML
    public ComboBox<String> gradeLevel;
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
    public Label gradeLevelErrorLabel;
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
        log.info("initialize StudentCreateController");
        initializeGradeLevelComboBox();
        addEventFilterToSaveButton();
        addEventListenersToFields();
    }

    private void initializeGradeLevelComboBox() {
        log.info("initialize grade filter");
        List<String> gradeOptions = IntStream
                .iterate(1, i -> i + 1)
                .limit(12)
                .mapToObj(String::valueOf)
                .collect(Collectors.toList());
        gradeLevel.getItems().addAll(gradeOptions);
    }

    private void addEventFilterToSaveButton(){
        saveButton.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            validator.lastname(lastName, lastnameErrorLabel);
            validator.firstname(firstName, firstnameErrorLabel);
            validator.gradeLevel(gradeLevel, gradeLevelErrorLabel);
            validator.email(email, emailErrorLabel);
            validator.address(address, addressErrorLabel);
            validator.phone(phone, phoneErrorLabel);
            validator.birthdate(birthdate, birthdateErrorLabel);
            saveStudent();
        });
    }

    private void addEventListenersToFields() {
        lastName.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) lastnameErrorLabel.setText("");
        });
        firstName.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) firstnameErrorLabel.setText("");
        });
        gradeLevel.selectionModelProperty().getValue().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.equals(oldValue)) gradeLevelErrorLabel.setText("");
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

    private void saveStudent() {
        if (isAnyFieldInvalid()) return;

        StudentDto student = StudentDto.builder()
                .lastname(lastName.getText())
                .firstname(firstName.getText())
                .gradeLevel(Integer.parseInt(gradeLevel.getValue()))
                .email(email.getText())
                .address(address.getText())
                .phone(phone.getText())
                .birthdate(birthdate.getValue())
                .build();

        studentService.saveStudent(student);
        deleteFormFields();
    }

    public void emptyForm(ActionEvent actionEvent) {
        deleteFormFields();
    }

    private void deleteFormFields() {
        firstName.setText(null);
        lastName.setText(null);
        gradeLevel.setValue(null);
        email.setText(null);
        address.setText(null);
        phone.setText(null);
        birthdate.setValue(null);
    }

    private boolean isAnyFieldInvalid() {
        return !lastnameErrorLabel.getText().isEmpty() || !firstnameErrorLabel.getText().isEmpty()
                || !emailErrorLabel.getText().isEmpty() || !addressErrorLabel.getText().isEmpty()
                || !phoneErrorLabel.getText().isEmpty() || !birthdateErrorLabel.getText().isEmpty()
                || !gradeLevelErrorLabel.getText().isEmpty();
    }
}
