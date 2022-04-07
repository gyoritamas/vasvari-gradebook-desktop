package org.vasvari.gradebook.controllers.subjects;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.vasvari.gradebook.dto.SubjectInput;
import org.vasvari.gradebook.dto.dataTypes.SimpleTeacher;
import org.vasvari.gradebook.service.SubjectService;
import org.vasvari.gradebook.service.TeacherService;
import org.vasvari.gradebook.util.Validator;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@FxmlView("view/fxml/subjects/subjectCreate.fxml")
@Component
@Slf4j
@RequiredArgsConstructor
public class SubjectCreateController implements Initializable {

    private final TeacherService teacherService;
    private final SubjectService subjectService;
    private final Validator validator;

    @FXML
    public TextField subjectName;
    @FXML
    public ComboBox<SimpleTeacher> subjectTeacher;
    @FXML
    public Label subjectNameErrorLabel;
    @FXML
    public Label subjectTeacherErrorLabel;
    @FXML
    public Button emptyFormButton;
    @FXML
    public Button saveButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info("initialize SubjectCreateController");
        initializeTeacherComboBox();
        addEventListenerToFields();
    }

    private void initializeTeacherComboBox() {
        List<SimpleTeacher> teachers = teacherService.findAllTeachers().stream()
                .map(teacher -> new SimpleTeacher(teacher.getId(), teacher.getFirstname(), teacher.getLastname()))
                .collect(Collectors.toList());
        subjectTeacher.getItems().addAll(teachers);
    }

    private void addEventListenerToFields() {
        subjectName.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if ((oldValue != null && !oldValue.equals(newValue)) || (oldValue == null && newValue != null))
                subjectNameErrorLabel.setText("");
        });
        subjectTeacher.selectionModelProperty().getValue().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if ((oldValue != null && !oldValue.equals(newValue)) || (oldValue == null && newValue != null))
                subjectTeacherErrorLabel.setText("");
        });
    }

    public void saveSubject() {
        validateFields();
        if (isAnyFieldInvalid()) return;

        SubjectInput subject = SubjectInput.builder()
                .name(subjectName.getText())
                .teacherId(subjectTeacher.getValue().getId())
                .build();

        subjectService.saveSubject(subject);
        deleteFormFields();
    }

    private void validateFields() {
        validator.subjectName(subjectName, subjectNameErrorLabel);
        validator.subjectTeacher(subjectTeacher, subjectTeacherErrorLabel);
    }

    @FXML
    public void emptyForm() {
        deleteFormFields();
        deleteErrorMessages();
    }

    private void deleteFormFields() {
        subjectName.setText(null);
        subjectTeacher.setValue(null);
    }

    private void deleteErrorMessages() {
        subjectNameErrorLabel.setText(null);
        subjectTeacherErrorLabel.setText(null);
    }

    private boolean isAnyFieldInvalid() {
        return !subjectNameErrorLabel.getText().isEmpty() || !subjectTeacherErrorLabel.getText().isEmpty();
    }
}
