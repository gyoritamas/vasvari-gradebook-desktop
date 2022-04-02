package org.vasvari.gradebook.controllers.contentarea;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vasvari.gradebook.dto.StudentDto;
import org.vasvari.gradebook.service.StudentService;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@FxmlView("view/fxml/contentarea/paneA.fxml")
@Component
public class PaneA implements Initializable {

    private final StudentService studentService;

    @Autowired
    public PaneA(StudentService studentService) {
        this.studentService = studentService;
    }

    @FXML
    private TableView<StudentDto> studentsTableView;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<StudentDto> data = getStudents();
        setTableColumns();
        studentsTableView.setItems(data);
    }

    private void setTableColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        gradeLevelColumn.setCellValueFactory(new PropertyValueFactory<>("gradeLevel"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
    }

    private ObservableList<StudentDto> getStudents() {
        List<StudentDto> students = studentService.findAllStudents().stream()
                .map(student -> new StudentDto(
                        student.getId(),
                        student.getFirstname(),
                        student.getLastname(),
                        student.getGradeLevel(),
                        student.getEmail(),
                        student.getAddress(),
                        student.getPhone(),
                        student.getBirthdate()
                ))
                .collect(Collectors.toList());
        return FXCollections.observableArrayList(students);
    }

    public void refreshTableView() {
        studentsTableView.getItems().clear();
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
                LocalDate.of(2000,1,1));

        studentService.saveStudent(student);
        refreshTableView();
    }
}
