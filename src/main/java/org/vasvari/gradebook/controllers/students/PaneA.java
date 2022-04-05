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

@FxmlView("view/fxml/contentarea/paneA.fxml")
@Component
@RequiredArgsConstructor
@Slf4j
public class PaneA implements Initializable {

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
    public StudentFormController studentFormController;
    @FXML
    public StudentSearchController studentSearchController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info("initialize PaneA");
        initializeTableColumns();
        initializeTable();
        addEventListenerToTable();
        addEventFilterToSaveButton();
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
        log.info("initialize table");
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
        studentFormController.studentFormTitle.setText("Új tanuló");
        studentFormController.selectedId = null;
        studentFormController.firstName.setText(null);
        studentFormController.lastName.setText(null);
        studentFormController.gradeLevel.setValue(null);
        studentFormController.email.setText(null);
        studentFormController.address.setText(null);
        studentFormController.phone.setText(null);
        studentFormController.birthdate.setValue(null);
    }

    private void populateForm(StudentDto selectedStudent) {
        studentFormController.studentFormTitle.setText("Tanuló adatainak módosítása");
        studentFormController.selectedId = selectedStudent.getId();
        studentFormController.firstName.setText(selectedStudent.getFirstname());
        studentFormController.lastName.setText(selectedStudent.getLastname());
        studentFormController.gradeLevel.setValue(selectedStudent.getGradeLevel().toString());
        studentFormController.email.setText(selectedStudent.getEmail());
        studentFormController.address.setText(selectedStudent.getAddress());
        studentFormController.phone.setText(selectedStudent.getPhone());
        studentFormController.birthdate.setValue(selectedStudent.getBirthdate());
    }

    private void addEventFilterToSaveButton() {
        studentFormController.saveButton.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> refreshTableView());
    }

    private void addEventListenerToSearchButton() {
        studentSearchController.searchButton.setOnAction(event -> searchStudents());
    }

    private void addEventListenerToResetFiltersButton() {
        //studentSearchController.resetFiltersButton.setOnAction(event -> resetFilters());
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
