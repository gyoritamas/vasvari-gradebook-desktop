package org.vasvari.gradebook.controllers.entries;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.vasvari.gradebook.dto.AssignmentOutput;
import org.vasvari.gradebook.dto.StudentDto;
import org.vasvari.gradebook.dto.SubjectOutput;
import org.vasvari.gradebook.model.request.GradebookRequest;
import org.vasvari.gradebook.service.AssignmentService;
import org.vasvari.gradebook.service.StudentService;
import org.vasvari.gradebook.service.SubjectService;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@FxmlView("view/fxml/entries/entrySearch.fxml")
@Component
@RequiredArgsConstructor
@Slf4j
public class EntrySearchController implements Initializable {

    private final StudentDto STUDENT_FILTER_DEFAULT_VALUE =
            StudentDto.builder().lastname("minden").firstname("tanuló").build();
    private final SubjectOutput SUBJECT_FILTER_DEFAULT_VALUE =
            SubjectOutput.builder().name("minden tantárgy").build();
    private final AssignmentOutput ASSIGNMENT_FILTER_DEFAULT_VALUE =
            AssignmentOutput.builder().name("minden feladat").build();

    private final StudentService studentService;
    private final SubjectService subjectService;
    private final AssignmentService assignmentService;

    @FXML
    public ComboBox<StudentDto> studentFilter;
    @FXML
    public ComboBox<SubjectOutput> subjectFilter;
    @FXML
    public ComboBox<AssignmentOutput> assignmentFilter;
    @FXML
    public Button resetFiltersButton;
    @FXML
    public Button searchButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info("initialize EntrySearchController");
        initializeStudentFilter();
        initializeSubjectFilter();
        initializeAssignmentFilter();
    }

    private void initializeStudentFilter() {
        List<StudentDto> listOfOptions = new ArrayList<>();
        listOfOptions.add(STUDENT_FILTER_DEFAULT_VALUE);
        listOfOptions.addAll(studentService.findStudentsForUser());
        ObservableList<StudentDto> studentOptions = FXCollections.observableArrayList(listOfOptions);
        studentFilter.getItems().addAll(studentOptions);
        studentFilter.setValue(STUDENT_FILTER_DEFAULT_VALUE);
    }

    private void initializeSubjectFilter() {
        List<SubjectOutput> listOfOptions = new ArrayList<>();
        listOfOptions.add(SUBJECT_FILTER_DEFAULT_VALUE);
        listOfOptions.addAll(subjectService.findSubjectsForUser());
        ObservableList<SubjectOutput> subjectOptions = FXCollections.observableArrayList(listOfOptions);
        subjectFilter.getItems().addAll(subjectOptions);
        subjectFilter.setValue(SUBJECT_FILTER_DEFAULT_VALUE);
    }

    private void initializeAssignmentFilter() {
        List<AssignmentOutput> listOfOptions = new ArrayList<>();
        listOfOptions.add(ASSIGNMENT_FILTER_DEFAULT_VALUE);
        listOfOptions.addAll(assignmentService.findAssignmentsForUserIncludeExpired());
        ObservableList<AssignmentOutput> assignmentOptions = FXCollections.observableArrayList(listOfOptions);
        assignmentFilter.getItems().addAll(assignmentOptions);
        assignmentFilter.setValue(ASSIGNMENT_FILTER_DEFAULT_VALUE);
    }

    public GradebookRequest getFilters() {
        GradebookRequest request = new GradebookRequest();
        request.setStudentId(studentFilter.getSelectionModel().getSelectedItem().getId());
        request.setSubjectId(subjectFilter.getSelectionModel().getSelectedItem().getId());
        request.setAssignmentId(assignmentFilter.getSelectionModel().getSelectedItem().getId());

        return request;
    }

    public void resetFilters() {
        studentFilter.setValue(STUDENT_FILTER_DEFAULT_VALUE);
        subjectFilter.setValue(SUBJECT_FILTER_DEFAULT_VALUE);
        assignmentFilter.setValue(ASSIGNMENT_FILTER_DEFAULT_VALUE);
    }
}
