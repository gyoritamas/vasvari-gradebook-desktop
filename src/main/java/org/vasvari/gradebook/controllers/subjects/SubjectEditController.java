package org.vasvari.gradebook.controllers.subjects;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.vasvari.gradebook.dto.SubjectInput;
import org.vasvari.gradebook.dto.dataTypes.SimpleTeacher;
import org.vasvari.gradebook.service.SubjectService;
import org.vasvari.gradebook.service.TeacherService;
import org.vasvari.gradebook.util.Validator;
import org.vasvari.gradebook.viewmodel.SubjectViewModel;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@FxmlView("view/fxml/subjects/subjectEdit.fxml")
@Component
@Slf4j
@RequiredArgsConstructor
public class SubjectEditController implements Initializable {

    private final SubjectService subjectService;
    private final TeacherService teacherService;
    private final Validator validator;

    private Long selectedId;

    @FXML
    public GridPane subjectEditTab;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info("initialize SubjectEditController");
        initializeTeacherComboBox();
        addEventFilterToFields();
    }

    private void initializeTeacherComboBox() {
        List<SimpleTeacher> teachers = teacherService.findAllTeachers().stream()
                .map(teacher -> new SimpleTeacher(teacher.getId(), teacher.getFirstname(), teacher.getLastname()))
                .collect(Collectors.toList());
        subjectTeacher.getItems().addAll(teachers);
    }

    private void addEventFilterToFields() {
        subjectName.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (selectedId != null && (oldValue != null && !oldValue.equals(newValue)) || (oldValue == null && newValue != null))
                validator.subjectName(subjectName, subjectNameErrorLabel);
            updateButton.setDisable(isAnyFieldInvalid());
        });
        subjectTeacher.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (selectedId != null && (oldValue != null && !oldValue.equals(newValue)) || (oldValue == null && newValue != null))
                validator.subjectTeacher(subjectTeacher, subjectTeacherErrorLabel);
            updateButton.setDisable(isAnyFieldInvalid());
        });
    }

    private boolean isAnyFieldInvalid() {
        return !subjectNameErrorLabel.getText().isEmpty() || !subjectTeacherErrorLabel.getText().isEmpty();
    }

    public void updateSubject() {
        if (selectedId == null) return;

        SubjectInput subject = SubjectInput.builder()
                .name(subjectName.getText())
                .teacherId(subjectTeacher.getValue().getId())
                .build();

        subjectService.updateSubject(selectedId, subject);
    }

    public void emptyEditForm() {
        selectedId = null;
        subjectName.setText(null);
        subjectTeacher.setValue(null);
        subjectEditTab.setDisable(true);
    }

    public void populateEditForm(SubjectViewModel selectedSubject) {
        subjectEditTab.setDisable(false);
        selectedId = selectedSubject.getId();
        subjectName.setText(selectedSubject.getName());
        subjectTeacher.setValue(selectedSubject.getTeacher());
    }

    public void deleteSubject() {
        if (selectedId == null) return;
        subjectService.deleteSubject(selectedId);
    }

}
