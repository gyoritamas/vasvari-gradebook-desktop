package org.vasvari.gradebook.controllers.subjects;

import javafx.event.ActionEvent;
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

@FxmlView("view/fxml/subjects/subjectEdit.fxml")
@Component
@Slf4j
@RequiredArgsConstructor
public class SubjectEditController implements Initializable {

    private final TeacherService teacherService;
    private final SubjectService subjectService;
    private final Validator validator;

    public Long selectedId;

    @FXML
    public TextField subjectName;
    @FXML
    public ComboBox<SimpleTeacher> subjectTeacher;
    @FXML
    public Label subjectNameErrorLabel;
    @FXML
    public Label subjectTeacherErrorLabel;
    @FXML
    public Button updateButton;
    @FXML
    public Button deleteButton;
    @FXML
    public Button refreshButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info("initialize SubjectEditController");
        initializeTeacherComboBox();
        addEventFilterToFields();
    }

    private void initializeTeacherComboBox() {
        log.info("initialize teacher combo box");
        List<SimpleTeacher> teachers = teacherService.findAllTeachers().stream()
                .map(teacher -> new SimpleTeacher(teacher.getId(), teacher.getFirstname(), teacher.getLastname()))
                .collect(Collectors.toList());
        subjectTeacher.getItems().addAll(teachers);
    }

    private void addEventFilterToFields() {
        subjectName.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) validator.subjectName(subjectName, subjectNameErrorLabel);
            updateButton.setDisable(isAnyFieldInvalid());
        });
        subjectTeacher.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if(!oldValue.equals(newValue)) validator.subjectTeacher(subjectTeacher, subjectTeacherErrorLabel);
            updateButton.setDisable(isAnyFieldInvalid());
        });
    }

    private boolean isAnyFieldInvalid() {
        return !subjectNameErrorLabel.getText().isEmpty() || !subjectTeacherErrorLabel.getText().isEmpty();
    }

    public void updateSubject(ActionEvent actionEvent) {
        if (selectedId == null) return;

        SubjectInput subject = SubjectInput.builder()
                .name(subjectName.getText())
                .teacherId(subjectTeacher.getValue().getId())
                .build();

        subjectService.updateSubject(selectedId, subject);
        deleteFormFields();
    }

    private void deleteFormFields() {
        subjectName.setText(null);
        subjectTeacher.setValue(null);
    }

    public void deleteSubject(ActionEvent actionEvent) {
        if (selectedId == null) return;
        subjectService.deleteSubject(selectedId);
    }
}
