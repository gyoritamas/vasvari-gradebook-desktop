package org.vasvari.gradebook.controllers.students;

import javafx.event.ActionEvent;
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

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@FxmlView("view/fxml/students/studentForm.fxml")
@Component
@Slf4j
@RequiredArgsConstructor
public class StudentFormController implements Initializable {

    private final StudentService studentService;

    @FXML
    public GridPane studentForm;
    @FXML
    public Label studentFormTitle;
    @FXML
    public Button saveButton;

    public Long selectedId;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info("initialize StudentFormController");
        initializeGradeLevelComboBox();
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

    public void save(ActionEvent actionEvent) {
        // TODO: validation
        StudentDto student = StudentDto.builder()
                .lastname(lastName.getText())
                .firstname(firstName.getText())
                .gradeLevel(Integer.parseInt(gradeLevel.getValue()))
                .email(email.getText())
                .address(address.getText())
                .phone(phone.getText())
                .birthdate(birthdate.getValue())
                .build();
        if (selectedId == null) saveStudent(student);
        else updateStudent(student);

    }

    private void saveStudent(StudentDto student) {
        studentService.saveStudent(student);
    }

    private void updateStudent(StudentDto student) {
        studentService.updateStudent(selectedId, student);
    }
}
