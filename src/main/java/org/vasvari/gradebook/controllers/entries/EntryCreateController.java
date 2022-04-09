package org.vasvari.gradebook.controllers.entries;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.vasvari.gradebook.dto.AssignmentOutput;
import org.vasvari.gradebook.dto.GradebookInput;
import org.vasvari.gradebook.dto.StudentDto;
import org.vasvari.gradebook.dto.SubjectOutput;
import org.vasvari.gradebook.model.request.AssignmentRequest;
import org.vasvari.gradebook.model.request.StudentRequest;
import org.vasvari.gradebook.service.AssignmentService;
import org.vasvari.gradebook.service.GradebookService;
import org.vasvari.gradebook.service.StudentService;
import org.vasvari.gradebook.service.SubjectService;
import org.vasvari.gradebook.util.EventListenerFactory;
import org.vasvari.gradebook.util.InternalServerErrorHandler;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@FxmlView("view/fxml/entries/entryCreate.fxml")
@Component
@RequiredArgsConstructor
@Slf4j
public class EntryCreateController implements Initializable {

    private static final SubjectOutput SUBJECT_DEFAULT_VALUE =
            SubjectOutput.builder().name("-- válasszon tantárgyat --").build();
    private static final AssignmentOutput ASSIGNMENT_DEFAULT_VALUE =
            AssignmentOutput.builder().name("-- válasszon feladatot --").build();
    private static final StudentDto STUDENT_DEFAULT_VALUE =
            StudentDto.builder().lastname("-- válasszon").firstname("tanulót --").build();
    private static final SubjectOutput NO_SUBJECT_FOUND =
            SubjectOutput.builder().name("-- nincsenek tantárgyak --").build();
    private static final AssignmentOutput NO_ASSIGNMENT_FOUND =
            AssignmentOutput.builder().name("-- nincsenek feladatok --").build();
    private static final StudentDto NO_STUDENT_FOUND =
            StudentDto.builder().lastname("-- nincsenek").firstname("tanulók --").build();

    private Long selectedSubjectId;

    private final StudentService studentService;
    private final SubjectService subjectService;
    private final AssignmentService assignmentService;
    private final GradebookService gradebookService;
    private final InternalServerErrorHandler errorHandler;
    private final EventListenerFactory eventListenerFactory;

    @FXML
    public ComboBox<SubjectOutput> entrySubject;
    @FXML
    public ComboBox<AssignmentOutput> entryAssignment;
    @FXML
    public ComboBox<StudentDto> entryStudent;
    @FXML
    public ComboBox<Integer> grade;
    @FXML
    public Label errorLabel;
    @FXML
    public Button emptyFormButton;
    @FXML
    public Button saveButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info("initialize EntryCreateController");
        initializeSubjectComboBox();
        initializeAssignmentComboBox();
        initializeStudentComboBox();
        initializeGradeComboBox();
        disableComboBoxes();
        addEventListenerToSubjectComboBox();
        addEventListenerToComboBoxes();
    }

    private void initializeSubjectComboBox() {
        List<SubjectOutput> listOfOptions = new ArrayList<>();
        List<SubjectOutput> subjectsFound = subjectService.findSubjectsForUser().stream()
                .sorted(Comparator.comparing(SubjectOutput::getName))
                .collect(Collectors.toList());

        if (subjectsFound.isEmpty()) {
            listOfOptions.add(NO_SUBJECT_FOUND);
        } else {
            listOfOptions.add(SUBJECT_DEFAULT_VALUE);
            listOfOptions.addAll(subjectsFound);
        }

        ObservableList<SubjectOutput> subjectOptions = FXCollections.observableArrayList(listOfOptions);
        entrySubject.setItems(subjectOptions);
        entrySubject.getSelectionModel().select(0);
    }

    private void initializeAssignmentComboBox() {
        if (selectedSubjectId == null) return;

        List<AssignmentOutput> listOfOptions = new ArrayList<>();

        // search assignments with selected subject
        AssignmentRequest assignmentRequest = new AssignmentRequest();
        assignmentRequest.setSubjectId(selectedSubjectId);
        List<AssignmentOutput> assignmentsFound = assignmentService.findAssignmentsForUser(assignmentRequest).stream()
                .sorted(Comparator.comparing(AssignmentOutput::getName))
                .collect(Collectors.toList());

        if (assignmentsFound.isEmpty()) {
            listOfOptions.add(NO_ASSIGNMENT_FOUND);
        } else {
            listOfOptions.add(ASSIGNMENT_DEFAULT_VALUE);
            listOfOptions.addAll(assignmentsFound);
        }
        ObservableList<AssignmentOutput> assignmentOptions = FXCollections.observableArrayList(listOfOptions);
        entryAssignment.setItems(assignmentOptions);
        entryAssignment.getSelectionModel().select(0);
    }

    private void initializeStudentComboBox() {
        if (selectedSubjectId == null) return;

        List<StudentDto> listOfOptions = new ArrayList<>();

        // search students enrolled to selected subject
        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setSubjectId(selectedSubjectId);
        List<StudentDto> studentsFound = studentService.findStudentsForUser(studentRequest).stream()
                .sorted(Comparator.comparing(StudentDto::getName))
                .collect(Collectors.toList());

        if (studentsFound.isEmpty()) {
            listOfOptions.add(NO_STUDENT_FOUND);
        } else {
            listOfOptions.add(STUDENT_DEFAULT_VALUE);
            listOfOptions.addAll(studentsFound);
        }
        ObservableList<StudentDto> studentOptions = FXCollections.observableArrayList(listOfOptions);
        entryStudent.setItems(studentOptions);
        entryStudent.getSelectionModel().select(0);
    }

    private void initializeGradeComboBox() {
        List<Integer> gradeOptions = IntStream
                .iterate(5, i -> i - 1)
                .limit(5)
                .boxed()
                .collect(Collectors.toList());
        grade.getItems().addAll(gradeOptions);
        grade.setValue(5);
    }

    private void addEventListenerToSubjectComboBox() {
        entrySubject.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setSelectedSubjectId(newValue.getId());
            initializeAssignmentComboBox();
            initializeStudentComboBox();
            if (selectedSubjectId != null) {
                enableComboBoxes();
            } else {
                disableComboBoxes();
            }
        });
    }

    private void addEventListenerToComboBoxes() {
        eventListenerFactory.onComboBoxChangeDeleteErrorMessage(entrySubject, errorLabel);
        eventListenerFactory.onComboBoxChangeDeleteErrorMessage(entryAssignment, errorLabel);
        eventListenerFactory.onComboBoxChangeDeleteErrorMessage(entryStudent, errorLabel);
        eventListenerFactory.onComboBoxChangeDeleteErrorMessage(grade, errorLabel);
    }

    public void saveEntry() {
        if (isAnyFieldInvalid()) return;

        GradebookInput entry = GradebookInput.builder()
                .subjectId(entrySubject.getSelectionModel().getSelectedItem().getId())
                .assignmentId(entryAssignment.getSelectionModel().getSelectedItem().getId())
                .studentId(entryStudent.getSelectionModel().getSelectedItem().getId())
                .grade(grade.getValue())
                .build();

        try {
            gradebookService.saveGradebookEntry(entry);
            deleteFormFields();
        } catch (Exception ex) {
            errorHandler.printErrorToLabel(ex, errorLabel);
        }
    }

    @FXML
    public void emptyForm() {
        deleteFormFields();
        deleteErrorMessages();
    }

    private void deleteFormFields() {
        entrySubject.getSelectionModel().select(0);
        disableComboBoxes();
    }

    private void deleteErrorMessages() {
        errorLabel.setText(null);
    }

    private boolean isAnyFieldInvalid() {
        return entrySubject.getSelectionModel().getSelectedItem().getId() == null
                || entryAssignment.getSelectionModel().getSelectedItem().getId() == null
                || entryStudent.getSelectionModel().getSelectedItem().getId() == null
                || grade.getSelectionModel().getSelectedItem() == null;
    }

    public void setSelectedSubjectId(Long selectedSubjectId) {
        this.selectedSubjectId = selectedSubjectId;
    }

    private void enableComboBoxes() {
        entryAssignment.setDisable(false);
        entryStudent.setDisable(false);
        grade.setDisable(false);
    }

    private void disableComboBoxes() {
        entryAssignment.setDisable(true);
        entryStudent.setDisable(true);
        grade.setDisable(true);
    }


}
