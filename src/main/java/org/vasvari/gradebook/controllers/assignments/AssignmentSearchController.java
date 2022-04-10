package org.vasvari.gradebook.controllers.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.vasvari.gradebook.dto.AssignmentType;
import org.vasvari.gradebook.dto.SubjectOutput;
import org.vasvari.gradebook.model.request.AssignmentRequest;
import org.vasvari.gradebook.service.SubjectService;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@FxmlView("view/fxml/assignments/assignmentSearch.fxml")
@Component
@RequiredArgsConstructor
@Slf4j
public class AssignmentSearchController implements Initializable {

    private static final String ASSIGNMENT_TYPE_DEFAULT_VALUE = "minden típus";
    private static final SubjectOutput SUBJECT_FILTER_DEFAULT_VALUE =
            SubjectOutput.builder().name("minden tantárgy").build();

    private final SubjectService subjectService;

    @FXML
    public TextField assignmentTitle;
    @FXML
    public ComboBox<String> typeFilter;
    @FXML
    public ComboBox<SubjectOutput> subjectFilter;
    @FXML
    public CheckBox expiredCheckbox;
    @FXML
    public Button resetFiltersButton;
    @FXML
    public Button searchButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info("initialize AssignmentSearchController");
        initializeTypeFilter();
        initializeSubjectFilter();
    }

    private void initializeTypeFilter() {
        typeFilter.getItems().add(ASSIGNMENT_TYPE_DEFAULT_VALUE);
        List<String> assignmentTypes = Arrays.stream(AssignmentType.values())
                .map(AssignmentType::getLocalizedName)
                .collect(Collectors.toList());

        typeFilter.getItems().addAll(assignmentTypes);
        typeFilter.setValue(ASSIGNMENT_TYPE_DEFAULT_VALUE);
    }

    private void initializeSubjectFilter() {
        List<SubjectOutput> listOfOptions = new ArrayList<>();
        listOfOptions.add(SUBJECT_FILTER_DEFAULT_VALUE);
        List<SubjectOutput> subjectsSorted = subjectService.findSubjectsForUser().stream()
                .sorted(Comparator.comparing(SubjectOutput::getName))
                .collect(Collectors.toList());
        listOfOptions.addAll(subjectsSorted);
        ObservableList<SubjectOutput> subjectOptions = FXCollections.observableArrayList(listOfOptions);
        subjectFilter.getItems().addAll(subjectOptions);
        subjectFilter.setValue(SUBJECT_FILTER_DEFAULT_VALUE);
    }

    public AssignmentRequest getFilters() {
        String title = assignmentTitle.getText();
        AssignmentType type = typeFilter.getValue().equals(ASSIGNMENT_TYPE_DEFAULT_VALUE) ?
                null : AssignmentType.getAssignmentTypeByLocalizedName(typeFilter.getValue());
        Long subjectId = subjectFilter == null ?
                null : subjectFilter.getValue().getId();

        return new AssignmentRequest(title, type, subjectId);
    }

    public void resetFilters() {
        assignmentTitle.setText(null);
        typeFilter.setValue(ASSIGNMENT_TYPE_DEFAULT_VALUE);
        subjectFilter.setValue(SUBJECT_FILTER_DEFAULT_VALUE);
        expiredCheckbox.setSelected(false);
    }
}
