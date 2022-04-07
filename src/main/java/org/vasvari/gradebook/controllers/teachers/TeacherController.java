package org.vasvari.gradebook.controllers.teachers;

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
import org.vasvari.gradebook.dto.TeacherDto;
import org.vasvari.gradebook.model.request.TeacherRequest;
import org.vasvari.gradebook.service.TeacherService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@FxmlView("view/fxml/teachers/teachers.fxml")
@Component
@RequiredArgsConstructor
@Slf4j
public class TeacherController implements Initializable {

    private final TeacherService teacherService;

    @FXML
    private TableView<TeacherDto> teachersTableView;
    @FXML
    public TableColumn<TeacherDto, Long> idColumn;
    @FXML
    public TableColumn<TeacherDto, String> nameColumn;
    @FXML
    public TableColumn<TeacherDto, String> emailColumn;
    @FXML
    public TableColumn<TeacherDto, String> addressColumn;
    @FXML
    public TableColumn<TeacherDto, String> phoneColumn;
    @FXML
    public TableColumn<TeacherDto, String> dateOfBirthColumn;

    @FXML
    public TeacherEditController teacherEditController;
    @FXML
    public TeacherSearchController teacherSearchController;
    @FXML
    public TeacherCreateController teacherCreateController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info("initialize TeacherController");
        teacherEditController.teacherEditTab.setDisable(true);
        initializeTableColumns();
        initializeTable();
        addEventListenerToTable();
        addEventListenerToSaveTeacherButton();
        addEventListenerToUpdateTeacherButton();
        addEventListenerToDeleteTeacherButton();
        addEventListenerToSearchButton();
        addEventListenerToResetFiltersButton();
    }

    private void initializeTableColumns() {
        log.info("initialize teachers table columns");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
    }

    private void initializeTable() {
        log.info("initialize teachers table");
        teachersTableView.setItems(getTeachers());
    }

    private void addEventListenerToTable() {
        teachersTableView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    TeacherDto selectedTeacher = teachersTableView.getSelectionModel().getSelectedItem();
                    if (selectedTeacher == null) {
                        teacherEditController.emptyEditForm();
                    } else {
                        teacherEditController.populateEditForm(selectedTeacher);
                    }
                });
    }

    private void addEventListenerToSearchButton() {
        teacherSearchController.searchButton.setOnAction(event -> searchTeachers());
    }

    private void addEventListenerToResetFiltersButton() {
        teacherSearchController.resetFiltersButton.setOnAction(actionEvent -> {
            teacherSearchController.resetFilters();
            refreshTableView();
        });
    }

    private void addEventListenerToSaveTeacherButton() {
        teacherCreateController.saveButton.setOnAction(actionEvent -> {
            teacherCreateController.saveTeacher();
            refreshTableView();
        });
    }

    private void addEventListenerToUpdateTeacherButton() {
        teacherEditController.updateButton.setOnAction(actionEvent -> {
            teacherEditController.updateTeacher();
            refreshTableView();
        });
    }

    private void addEventListenerToDeleteTeacherButton() {
        teacherEditController.deleteButton.setOnAction(actionEvent -> {
            teacherEditController.deleteTeacher();
            refreshTableView();
        });
    }

    private ObservableList<TeacherDto> getTeachers() {
        List<TeacherDto> teachers = teacherService.findAllTeachers();
        return FXCollections.observableArrayList(teachers);
    }

    private void searchTeachers() {
        TeacherRequest request = teacherSearchController.getFilters();
        teachersTableView.setItems(FXCollections.observableArrayList(teacherService.searchTeachers(request)));
    }

    public void refreshTableView() {
        teachersTableView.setItems(getTeachers());
    }
}
