package org.vasvari.gradebook.controllers.students;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
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

@FxmlView("view/fxml/students/studentEdit.fxml")
@Component
@Slf4j
@RequiredArgsConstructor
public class StudentEditController implements Initializable {

    private final StudentService studentService;
    private final Validator validator;

    private Long selectedId;

    @FXML
    public GridPane studentEditTab;
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
        log.info("initialize StudentEditController");
        initializeGradeLevelComboBox();
        addEventFilterToFields();
    }

    private void initializeGradeLevelComboBox() {
        List<String> gradeOptions = IntStream
                .iterate(1, i -> i + 1)
                .limit(12)
                .mapToObj(String::valueOf)
                .collect(Collectors.toList());
        gradeLevel.getItems().addAll(gradeOptions);
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

    public void updateStudent() {
        if (selectedId == null) return;

        StudentDto student = StudentDto.builder()
                .lastname(lastName.getText())
                .firstname(firstName.getText())
                .gradeLevel(Integer.parseInt(gradeLevel.getValue()))
                .email(email.getText())
                .address(address.getText())
                .phone(phone.getText())
                .birthdate(birthdate.getValue())
                .build();

        studentService.updateStudent(selectedId, student);
    }

    public void emptyEditForm() {
        setSelectedId(null);
        firstName.setText(null);
        lastName.setText(null);
        gradeLevel.setValue(null);
        email.setText(null);
        address.setText(null);
        phone.setText(null);
        birthdate.setValue(null);
        studentEditTab.setDisable(true);
    }

    public void populateEditForm(StudentDto selectedStudent) {
        studentEditTab.setDisable(false);
        setSelectedId(selectedStudent.getId());
        firstName.setText(selectedStudent.getFirstname());
        lastName.setText(selectedStudent.getLastname());
        gradeLevel.setValue(selectedStudent.getGradeLevel().toString());
        email.setText(selectedStudent.getEmail());
        address.setText(selectedStudent.getAddress());
        phone.setText(selectedStudent.getPhone());
        birthdate.setValue(selectedStudent.getBirthdate());
    }

    public void deleteStudent() {
        if (selectedId == null) return;
        studentService.deleteStudent(selectedId);
    }

    public void setSelectedId(Long id) {
        this.selectedId = id;
    }
}
