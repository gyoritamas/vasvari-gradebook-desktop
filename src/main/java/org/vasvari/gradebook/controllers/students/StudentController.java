package org.vasvari.gradebook.controllers.students;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info("initialize StudentController");
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
        log.info("initialize student table");
        ObservableList<StudentDto> data = getStudents();
        studentsTableView.setItems(data);
    }

    private void addEventListenerToTable() {
        log.info("add event listener to table");
        studentsTableView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (studentsTableView.getSelectionModel().getSelectedItem() == null) {
                        emptyForm();
                    } else {
                        populateForm(studentsTableView.getSelectionModel().getSelectedItem());
                    }
                });
    }

    private void emptyForm() {
        studentEditController.selectedId = null;
        studentEditController.firstName.setText(null);
        studentEditController.lastName.setText(null);
        studentEditController.gradeLevel.setValue(null);
        studentEditController.email.setText(null);
        studentEditController.address.setText(null);
        studentEditController.phone.setText(null);
        studentEditController.birthdate.setValue(null);
    }

    private void populateForm(StudentDto selectedStudent) {
        studentEditController.selectedId = selectedStudent.getId();
        studentEditController.firstName.setText(selectedStudent.getFirstname());
        studentEditController.lastName.setText(selectedStudent.getLastname());
        studentEditController.gradeLevel.setValue(selectedStudent.getGradeLevel().toString());
        studentEditController.email.setText(selectedStudent.getEmail());
        studentEditController.address.setText(selectedStudent.getAddress());
        studentEditController.phone.setText(selectedStudent.getPhone());
        studentEditController.birthdate.setValue(selectedStudent.getBirthdate());
    }

    private void addEventFilterToUpdateStudentButton() {
        studentEditController.updateButton.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> refreshTableView());
    }

    private void addEventFilterToSaveStudentButton() {
        studentCreateController.saveButton.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> refreshTableView());
    }

    private void addEventFilterToDeleteStudentButton() {
        studentEditController.deleteButton.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> refreshTableView());
    }

    private void addEventListenerToSearchButton() {
        studentSearchController.searchButton.setOnAction(event -> searchStudents());
    }

    private void addEventListenerToResetFiltersButton() {
        studentSearchController.resetFiltersButton.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> resetFilters());
    }

    private ObservableList<StudentDto> getStudents() {
        List<StudentDto> students = studentService.findStudentsForUser();
        return FXCollections.observableArrayList(students);
    }

    private void searchStudents() {
        StudentRequest request = studentSearchController.getFilters();
        studentsTableView.setItems(FXCollections.observableArrayList(studentService.findStudentsForUser(request)));
    }

    private void resetFilters() {
        studentsTableView.setItems(FXCollections.observableArrayList(studentService.findStudentsForUser()));
    }

    public void refreshTableView() {
        studentsTableView.setItems(getStudents());
    }

}
