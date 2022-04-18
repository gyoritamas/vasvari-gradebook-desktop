package org.vasvari.gradebook.controllers.entries;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.vasvari.gradebook.dto.AssignmentOutput;
import org.vasvari.gradebook.dto.GradebookInput;
import org.vasvari.gradebook.dto.GradebookOutput;
import org.vasvari.gradebook.dto.StudentDto;
import org.vasvari.gradebook.model.request.AssignmentRequest;
import org.vasvari.gradebook.model.request.StudentRequest;
import org.vasvari.gradebook.service.AssignmentService;
import org.vasvari.gradebook.service.GradebookService;
import org.vasvari.gradebook.service.StudentService;
import org.vasvari.gradebook.util.InternalServerErrorHandler;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@FxmlView("view/fxml/entries/entryEditController.fxml")
@Component
@RequiredArgsConstructor
@Slf4j
public class EntryEditController implements Initializable {

    private Long selectedEntryId;
    private Long selectedSubjectId;

    private final StudentService studentService;
    private final AssignmentService assignmentService;
    private final GradebookService gradebookService;
    private final InternalServerErrorHandler errorHandler;

    @FXML
    public GridPane entryEditPane;
    @FXML
    public Label entrySubject;
    @FXML
    public ComboBox<AssignmentOutput> entryAssignment;
    @FXML
    public ComboBox<StudentDto> entryStudent;
    @FXML
    public ComboBox<Integer> grade;
    @FXML
    public Label errorLabel;
    @FXML
    public Button updateButton;
    @FXML
    public Button deleteButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info("initialize EntryEditController");
        initializeAssignmentComboBox();
        initializeStudentComboBox();
        initializeGradeComboBox();
        addEventListenerToComboBoxes();
    }

    private void initializeAssignmentComboBox() {
        if (selectedSubjectId == null) return;

        // search assignments with selected subject
        AssignmentRequest assignmentRequest = new AssignmentRequest();
        assignmentRequest.setSubjectId(selectedSubjectId);
        List<AssignmentOutput> assignmentsOfSubject = assignmentService.findAssignmentsForUserIncludeExpired(assignmentRequest).stream()
                .sorted(Comparator.comparing(AssignmentOutput::getName))
                .collect(Collectors.toList());

        ObservableList<AssignmentOutput> assignmentOptions = FXCollections.observableArrayList(assignmentsOfSubject);
        entryAssignment.setItems(assignmentOptions);
    }

    private void initializeStudentComboBox() {
        if (selectedSubjectId == null) return;

        // search students enrolled to selected subject
        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setSubjectId(selectedSubjectId);
        List<StudentDto> studentsOfSubject = studentService.findStudentsForUser(studentRequest).stream()
                .sorted(Comparator.comparing(StudentDto::getName))
                .collect(Collectors.toList());

        ObservableList<StudentDto> studentOptions = FXCollections.observableArrayList(studentsOfSubject);
        entryStudent.setItems(studentOptions);
    }

    private void initializeGradeComboBox() {
        List<Integer> gradeOptions = IntStream
                .iterate(5, i -> i - 1)
                .limit(5)
                .boxed()
                .collect(Collectors.toList());
        grade.getItems().addAll(gradeOptions);
    }

    private void addEventListenerToComboBoxes() {
        entryAssignment.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (selectedEntryId != null && (oldValue != null && !oldValue.equals(newValue)) || (oldValue == null && newValue != null))
                updateButton.setDisable(isAnyFieldInvalid());
        });
        entryStudent.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (selectedEntryId != null && (oldValue != null && !oldValue.equals(newValue)) || (oldValue == null && newValue != null))
                updateButton.setDisable(isAnyFieldInvalid());
        });
        grade.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (selectedEntryId != null && (oldValue != null && !oldValue.equals(newValue)) || (oldValue == null && newValue != null))
                updateButton.setDisable(isAnyFieldInvalid());
        });
    }

    public boolean updateEntry() {
        if (selectedEntryId == null) return false;

        GradebookInput entry = GradebookInput.builder()
                .subjectId(selectedSubjectId)
                .assignmentId(entryAssignment.getSelectionModel().getSelectedItem().getId())
                .studentId(entryStudent.getSelectionModel().getSelectedItem().getId())
                .grade(grade.getValue())
                .build();

        try {
            gradebookService.updateEntry(selectedEntryId, entry);
        } catch (Exception ex) {
            errorHandler.printErrorToLabel(ex, errorLabel);
            return false;
        }

        return true;
    }

    public void emptyEditForm() {
        selectedEntryId = null;
        deleteFormFields();
        deleteErrorMessages();
        entryEditPane.setDisable(true);
    }

    public void populateEditForm(GradebookOutput entry) {
        entryEditPane.setDisable(false);
        selectedEntryId = entry.getId();
        selectedSubjectId = entry.getSubject().getId();
        entrySubject.setText(entry.getSubject().getName());

        initializeAssignmentComboBox();
        long assignmentId = entry.getAssignment().getId();
        AssignmentOutput assignmentOfEntry = entryAssignment.getItems().stream()
                .filter(assignment -> assignment.getId().equals(assignmentId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not find assignment with ID " + assignmentId));
        entryAssignment.setValue(assignmentOfEntry);

        initializeStudentComboBox();
        long studentId = entry.getStudent().getId();
        StudentDto studentOfEntry = entryStudent.getItems().stream()
                .filter(student -> student.getId().equals(studentId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not find student with ID " + studentId));
        entryStudent.setValue(studentOfEntry);

        grade.setValue(entry.getGrade());
    }

    private void deleteFormFields() {
        entrySubject.setText(null);
        entryAssignment.setValue(null);
        entryStudent.setValue(null);
        grade.setValue(null);
    }

    private void deleteErrorMessages() {
        errorLabel.setText(null);
    }

    private boolean isAnyFieldInvalid() {
        return selectedSubjectId == null || entryAssignment.getValue() == null
                || entryStudent.getValue() == null || grade.getValue() == null;
    }

    public void deleteEntry() {
        if (selectedEntryId == null) return;
        gradebookService.deleteGradebookEntry(selectedEntryId);
    }

}
