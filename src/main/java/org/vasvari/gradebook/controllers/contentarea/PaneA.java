package org.vasvari.gradebook.controllers.contentarea;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vasvari.gradebook.dto.StudentDto;
import org.vasvari.gradebook.dto.SubjectOutput;
import org.vasvari.gradebook.model.request.StudentRequest;
import org.vasvari.gradebook.service.StudentService;
import org.vasvari.gradebook.service.SubjectService;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@FxmlView("view/fxml/contentarea/paneA.fxml")
@Component
public class PaneA implements Initializable {

    private static final String GRADE_LEVEL_FILTER_DEFAULT_VALUE = "minden évfolyam";
    private static final SubjectOutput SUBJECT_FILTER_DEFAULT_VALUE = SubjectOutput.builder().name("minden tantárgy").build();

    private static boolean isInitializationComplete;

    private final StudentService studentService;
    private final SubjectService subjectService;

    @Autowired
    public PaneA(StudentService studentService, SubjectService subjectService) {
        this.studentService = studentService;
        this.subjectService = subjectService;
    }

    @FXML
    private TableView<StudentDto> studentsTableView;
    @FXML
    public TableColumn<StudentDto, Long> idColumn;
    @FXML
    public TableColumn<StudentDto, String> nameColumn;
    @FXML
    public TableColumn<StudentDto, String> gradeLevelColumn;
    @FXML
    public TableColumn<StudentDto, String> emailColumn;
    @FXML
    public TableColumn<StudentDto, String> addressColumn;
    @FXML
    public TableColumn<StudentDto, String> phoneColumn;
    @FXML
    public TableColumn<StudentDto, String> dateOfBirthColumn;
    @FXML
    public TextField studentName;
    @FXML
    public ComboBox<String> gradeLevelFilter;
    @FXML
    public ComboBox<SubjectOutput> subjectFilter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (isInitializationComplete) return;
        initializeTableColumns();
        initializeTable();
        initializeFilters();
        isInitializationComplete = true;
    }

    private void initializeTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        gradeLevelColumn.setCellValueFactory(new PropertyValueFactory<>("gradeLevel"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
    }

    private void initializeTable() {
        ObservableList<StudentDto> data = getStudents();
        studentsTableView.setItems(data);
    }

    private void initializeFilters() {
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

    private ObservableList<StudentDto> getStudents() {
        List<StudentDto> students = studentService.findStudentsForUser();
        return FXCollections.observableArrayList(students);
    }

    public void searchStudents() {
        String name = studentName.getText();
        Integer gradeLevel = gradeLevelFilter.getValue().equals(GRADE_LEVEL_FILTER_DEFAULT_VALUE) ? null : Integer.parseInt(gradeLevelFilter.getValue());
        Long subjectId = subjectFilter == null ? null : subjectFilter.getValue().getId();
        StudentRequest request = new StudentRequest(name, gradeLevel, subjectId);
//        studentsTableView.getItems().clear();
        studentsTableView.setItems(FXCollections.observableArrayList(studentService.findStudentsForUser(request)));
    }

    public void resetFilters() {
        studentName.setText(null);
        gradeLevelFilter.setValue(GRADE_LEVEL_FILTER_DEFAULT_VALUE);
        subjectFilter.setValue(SUBJECT_FILTER_DEFAULT_VALUE);
        studentsTableView.setItems(FXCollections.observableArrayList(studentService.findStudentsForUser()));
    }

    public void refreshTableView() {
//        studentsTableView.getItems().clear();
        studentsTableView.setItems(getStudents());
    }

    public void addStudent(ActionEvent actionEvent) {
        StudentDto student = new StudentDto(
                1L,
                "FIRSTNAME",
                "LASTNAME",
                10,
                "test@example.org",
                "EXAMPLE ADDRESS",
                "+12345678",
                LocalDate.of(2000, 1, 1));

        studentService.saveStudent(student);
        refreshTableView();
    }
}
