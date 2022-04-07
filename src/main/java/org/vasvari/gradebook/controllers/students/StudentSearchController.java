package org.vasvari.gradebook.controllers.students;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.vasvari.gradebook.dto.SubjectOutput;
import org.vasvari.gradebook.model.request.StudentRequest;
import org.vasvari.gradebook.service.SubjectService;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@FxmlView("view/fxml/students/studentSearch.fxml")
@Component
@Slf4j
@RequiredArgsConstructor
public class StudentSearchController implements Initializable {

    private static final String GRADE_LEVEL_FILTER_DEFAULT_VALUE = "minden évfolyam";
    private static final SubjectOutput SUBJECT_FILTER_DEFAULT_VALUE = SubjectOutput.builder().name("minden tantárgy").build();

    private final SubjectService subjectService;

    @FXML
    public TextField studentName;
    @FXML
    public ComboBox<String> gradeLevelFilter;
    @FXML
    public ComboBox<SubjectOutput> subjectFilter;
    @FXML
    public Button searchButton;
    @FXML
    public Button resetFiltersButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeSubjectFilter();
        initializeGradeFilter();
    }

    private void initializeSubjectFilter() {
        List<SubjectOutput> listOfOptions = new ArrayList<>();
        listOfOptions.add(SUBJECT_FILTER_DEFAULT_VALUE);
        listOfOptions.addAll(subjectService.findSubjectsForUser());
        ObservableList<SubjectOutput> subjectOptions = FXCollections.observableArrayList(listOfOptions);
        subjectFilter.getItems().addAll(subjectOptions);
        subjectFilter.setValue(SUBJECT_FILTER_DEFAULT_VALUE);
    }

    private void initializeGradeFilter() {
        List<String> gradeOptions = new ArrayList<>();
        gradeOptions.add(GRADE_LEVEL_FILTER_DEFAULT_VALUE);
        List<String> oneToTwelve = IntStream.iterate(1, i -> i + 1).limit(12).mapToObj(String::valueOf).collect(Collectors.toList());
        gradeOptions.addAll(oneToTwelve);
        gradeLevelFilter.getItems().addAll(gradeOptions);
        gradeLevelFilter.setValue(GRADE_LEVEL_FILTER_DEFAULT_VALUE);
    }

    public StudentRequest getFilters() {
        String name = studentName.getText();
        Integer gradeLevel = gradeLevelFilter.getValue().equals(GRADE_LEVEL_FILTER_DEFAULT_VALUE) ? null : Integer.parseInt(gradeLevelFilter.getValue());
        Long subjectId = subjectFilter == null ? null : subjectFilter.getValue().getId();
        return new StudentRequest(name, gradeLevel, subjectId);
    }

    public void resetFilters() {
        studentName.setText(null);
        gradeLevelFilter.setValue(GRADE_LEVEL_FILTER_DEFAULT_VALUE);
        subjectFilter.setValue(SUBJECT_FILTER_DEFAULT_VALUE);
    }
}
