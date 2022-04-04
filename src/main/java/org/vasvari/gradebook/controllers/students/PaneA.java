package org.vasvari.gradebook.controllers.students;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.vasvari.gradebook.dto.StudentDto;
import org.vasvari.gradebook.dto.SubjectOutput;
import org.vasvari.gradebook.model.request.StudentRequest;
import org.vasvari.gradebook.service.StudentService;
import org.vasvari.gradebook.service.SubjectService;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@FxmlView("view/fxml/contentarea/paneA.fxml")
@Component
@RequiredArgsConstructor
@Slf4j
public class PaneA implements Initializable {

    private static final String GRADE_LEVEL_FILTER_DEFAULT_VALUE = "minden évfolyam";
    private static final SubjectOutput SUBJECT_FILTER_DEFAULT_VALUE = SubjectOutput.builder().name("minden tantárgy").build();

    private final StudentService studentService;
    private final SubjectService subjectService;

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

    @FXML
    public StudentFormController studentFormController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info("initialize PaneA");
        initializeTableColumns();
        initializeTable();
        initializeFilters();
        addEventListenerToTable();
        addEventListenerToSaveButton();
    }

    private void addEventListenerToTable() {
        log.info("add eventlistener to table");
        studentsTableView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (studentsTableView.getSelectionModel().getSelectedItem() == null) {
                        studentFormController.id = null;
                        studentFormController.firstName.setText(null);
                        studentFormController.lastName.setText(null);
                        studentFormController.gradeLevel.setValue(null);
                        studentFormController.email.setText(null);
                        studentFormController.address.setText(null);
                        studentFormController.phone.setText(null);
                        studentFormController.birthdate.setValue(null);
                    } else {
                        studentFormController.id = studentsTableView.getSelectionModel().getSelectedItem().getId();
                        studentFormController.firstName.setText(
                                studentsTableView.getSelectionModel().getSelectedItem().getFirstname()
                        );
                        studentFormController.lastName.setText(
                                studentsTableView.getSelectionModel().getSelectedItem().getLastname()
                        );
                        studentFormController.gradeLevel.setValue(
                                studentsTableView.getSelectionModel().getSelectedItem().getGradeLevel().toString()
                        );
                        studentFormController.email.setText(
                                studentsTableView.getSelectionModel().getSelectedItem().getEmail()
                        );
                        studentFormController.address.setText(
                                studentsTableView.getSelectionModel().getSelectedItem().getAddress()
                        );
                        studentFormController.phone.setText(
                                studentsTableView.getSelectionModel().getSelectedItem().getPhone()
                        );
                        studentFormController.birthdate.setValue(
                                studentsTableView.getSelectionModel().getSelectedItem().getBirthdate()
                        );
                    }
                });
    }

    private void addEventListenerToSaveButton() {
//        studentFormController.saveButton.setOnAction(event -> refreshTableView());
    }

    private void initializeTableColumns() {
        log.info("initialize table columns");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        gradeLevelColumn.setCellValueFactory(new PropertyValueFactory<>("gradeLevel"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
    }

    private void initializeTable() {
        log.info("initialize table");
        ObservableList<StudentDto> data = getStudents();
        studentsTableView.setItems(data);
    }

    private void initializeFilters() {
        log.info("initialize filters");
        initializeSubjectFilter();
        initializeGradeFilter();
    }

    private void initializeSubjectFilter() {
        log.info("initialize subject filter");
        List<SubjectOutput> listOfOptions = new ArrayList<>();
        listOfOptions.add(SUBJECT_FILTER_DEFAULT_VALUE);
        listOfOptions.addAll(subjectService.findSubjectsForUser());
        ObservableList<SubjectOutput> subjectOptions = FXCollections.observableArrayList(listOfOptions);
        subjectFilter.getItems().addAll(subjectOptions);
        subjectFilter.setValue(SUBJECT_FILTER_DEFAULT_VALUE);
    }

    private void initializeGradeFilter() {
        log.info("initialize grade filter");
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
        studentsTableView.setItems(FXCollections.observableArrayList(studentService.findStudentsForUser(request)));
    }

    public void resetFilters() {
        studentName.setText(null);
        gradeLevelFilter.setValue(GRADE_LEVEL_FILTER_DEFAULT_VALUE);
        subjectFilter.setValue(SUBJECT_FILTER_DEFAULT_VALUE);
        studentsTableView.setItems(FXCollections.observableArrayList(studentService.findStudentsForUser()));
    }

    public void refreshTableView() {
        studentsTableView.setItems(getStudents());
    }

}
