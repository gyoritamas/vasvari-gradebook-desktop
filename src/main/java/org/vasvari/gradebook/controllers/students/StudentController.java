package org.vasvari.gradebook.controllers.students;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.vasvari.gradebook.dto.StudentDto;
import org.vasvari.gradebook.model.request.StudentRequest;
import org.vasvari.gradebook.service.StudentService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@FxmlView("view/fxml/students/students.fxml")
@Component
@RequiredArgsConstructor
@Slf4j
public class StudentController implements Initializable {

    private final StudentService studentService;

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
    public StudentEditController studentEditController;
    @FXML
    public StudentSearchController studentSearchController;
    @FXML
    public StudentCreateController studentCreateController;
    @FXML
    public StudentUserController studentUserController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info("initialize StudentController");
        studentEditController.studentEditTab.setDisable(true);
        studentUserController.studentUserTab.setDisable(true);
        initializeTableColumns();
        initializeTable();
        addEventListenerToTable();
        addEventFilterToSaveStudentButton();
        addEventFilterToUpdateStudentButton();
        addEventFilterToDeleteStudentButton();
        addEventListenerToSearchButton();
        addEventListenerToResetFiltersButton();
    }

    private void initializeTableColumns() {
        log.info("initialize student table columns");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        gradeLevelColumn.setCellValueFactory(new PropertyValueFactory<>("gradeLevel"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
    }

    private void initializeTable() {
        log.info("initialize student table");
        ObservableList<StudentDto> data = getStudents();
        studentsTableView.setItems(data);
    }

    private void addEventListenerToTable() {
        studentsTableView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    StudentDto selectedStudent = studentsTableView.getSelectionModel().getSelectedItem();
                    if (selectedStudent == null) {
                        studentEditController.emptyEditForm();
                        studentUserController.emptyForm();
                    } else {
                        studentEditController.populateEditForm(selectedStudent);
                        studentUserController.populateForm(selectedStudent);
                    }
                });
    }

    private void addEventListenerToSearchButton() {
        studentSearchController.searchButton.setOnAction(event -> searchStudents());
    }

    private void addEventListenerToResetFiltersButton() {
        studentSearchController.resetFiltersButton.setOnAction(actionEvent -> {
            studentSearchController.resetFilters();
            refreshTableView();
        });
    }

    private void addEventFilterToSaveStudentButton() {
        studentCreateController.saveButton.setOnAction(actionEvent -> {
            studentCreateController.saveStudent();
            refreshTableView();
        });
    }

    private void addEventFilterToUpdateStudentButton() {
        studentEditController.updateButton.setOnAction(actionEvent -> {
            studentEditController.updateStudent();
            refreshTableView();
        });
    }

    private void addEventFilterToDeleteStudentButton() {
        studentEditController.deleteButton.setOnAction(actionEvent -> {
            studentEditController.deleteStudent();
            refreshTableView();
        });
    }

    private ObservableList<StudentDto> getStudents() {
        List<StudentDto> students = studentService.findStudentsForUser();
        return FXCollections.observableArrayList(students);
    }

    private void searchStudents() {
        StudentRequest request = studentSearchController.getFilters();
        studentsTableView.setItems(FXCollections.observableArrayList(studentService.findStudentsForUser(request)));
    }

    public void refreshTableView() {
        studentsTableView.setItems(getStudents());
    }

}
