package org.vasvari.gradebook.controllers.subjects;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.vasvari.gradebook.dto.StudentDto;
import org.vasvari.gradebook.service.StudentService;
import org.vasvari.gradebook.service.SubjectService;
import org.vasvari.gradebook.viewmodel.SubjectViewModel;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@FxmlView("view/fxml/subjects/subjectStudents.fxml")
@Component
@Slf4j
@RequiredArgsConstructor
public class SubjectStudentsController implements Initializable {

    private final StudentService studentService;
    private final SubjectService subjectService;

    private Long selectedSubjectId;

    @FXML
    public GridPane subjectStudentsPane;
    @FXML
    public Label subjectName;
    @FXML
    public Label subjectTeacher;
    @FXML
    public ListView<StudentDto> studentsListView;
    @FXML
    public Button removeStudentButton;
    @FXML
    public ComboBox<StudentDto> studentComboBox;
    @FXML
    public Button addStudentButton;
    @FXML
    public ComboBox<Integer> gradeLevelComboBox;
    @FXML
    public Button addAllStudentsButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info("initialize SubjectStudentsController");
        initializeStudentsListView();
        initializeStudentComboBox();
        initializeGradeLevelComboBox();
    }

    private void initializeStudentsListView() {
        log.info("initialize studentsListView");
        if (selectedSubjectId == null) return;
        List<StudentDto> studentsOfSubject = subjectService.findStudentsOfSubject(selectedSubjectId);
        studentsListView.setItems(FXCollections.observableArrayList(studentsOfSubject));
    }

    private void initializeStudentComboBox() {
        List<StudentDto> students = studentService.findAllStudents();
        if (selectedSubjectId != null) {
            List<StudentDto> studentsOfSubject = subjectService.findStudentsOfSubject(selectedSubjectId);
            students.removeAll(studentsOfSubject);
            students.sort(Comparator.comparing(StudentDto::getName));
        }
        studentComboBox.setItems(FXCollections.observableArrayList(students));
    }

    private void initializeGradeLevelComboBox() {
        List<Integer> gradeLevelOptions = IntStream.iterate(12, i -> i - 1)
                .limit(12)
                .boxed()
                .collect(Collectors.toList());
        gradeLevelComboBox.getItems().addAll(gradeLevelOptions);
        gradeLevelComboBox.setValue(12);
    }

    public void emptyEditForm() {
        selectedSubjectId = null;
        subjectName.setText(null);
        subjectTeacher.setText(null);
        studentsListView.getItems().clear();
        studentComboBox.setValue(null);
        subjectStudentsPane.setDisable(true);
    }

    public void populateEditForm(SubjectViewModel selectedSubject) {
        subjectStudentsPane.setDisable(false);
        selectedSubjectId = selectedSubject.getId();
        subjectName.setText(selectedSubject.getName());
        subjectTeacher.setText(selectedSubject.getTeacher().getName());
        initializeStudentComboBox();
        initializeStudentsListView();
    }

    @FXML
    public void removeStudent() {
        StudentDto selectedStudent = studentsListView.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) return;
        subjectService.removeStudentFromSubject(selectedSubjectId, selectedStudent.getId());
        initializeStudentsListView();
        initializeStudentComboBox();
    }

    @FXML
    public void addStudent() {
        StudentDto selectedStudent = studentComboBox.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) return;
        subjectService.addStudentToSubject(selectedSubjectId, selectedStudent.getId());
        initializeStudentsListView();
        initializeStudentComboBox();
    }

    @FXML
    public void addAllStudents() {
        Integer selectedGradeLevel = gradeLevelComboBox.getSelectionModel().getSelectedItem();
        List<StudentDto> studentsOfSelectedGradeLevel = studentService.findAllStudents().stream()
                .filter(student -> student.getGradeLevel().equals(selectedGradeLevel))
                .collect(Collectors.toList());
        studentsOfSelectedGradeLevel
                .forEach(student -> subjectService.addStudentToSubject(selectedSubjectId, student.getId()));
        initializeStudentsListView();
        initializeStudentComboBox();
    }
}
