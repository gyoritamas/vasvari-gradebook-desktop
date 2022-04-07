package org.vasvari.gradebook.controllers.teachers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
        initializeTableColumns();
        initializeTable();
        addEventListenerToTable();
        addEventFilterToSaveTeacherButton();
        addEventFilterToUpdateTeacherButton();
        addEventFilterToDeleteTeacherButton();
        addEventListenerToSearchButton();
        addEventListenerToResetFiltersButton();
    }

    private void initializeTableColumns() {
        log.info("initialize table columns");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
    }

    private void initializeTable() {
        log.info("initialize table");
        ObservableList<TeacherDto> data = getTeachers();
        teachersTableView.setItems(data);
    }

    private void addEventListenerToTable() {
        log.info("add event listener to table");
        teachersTableView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (teachersTableView.getSelectionModel().getSelectedItem() == null) {
                        emptyEditForm();
                    } else {
                        populateEditForm(teachersTableView.getSelectionModel().getSelectedItem());
                    }
                });
    }

    private void emptyEditForm() {
        teacherEditController.selectedId = null;
        teacherEditController.firstName.setText("");
        teacherEditController.lastName.setText("");
        teacherEditController.email.setText("");
        teacherEditController.address.setText("");
        teacherEditController.phone.setText("");
        teacherEditController.birthdate.editorProperty().getValue().setText("");
    }

    private void populateEditForm(TeacherDto selectedTeacher) {
        teacherEditController.selectedId = selectedTeacher.getId();
        teacherEditController.firstName.setText(selectedTeacher.getFirstname());
        teacherEditController.lastName.setText(selectedTeacher.getLastname());
        teacherEditController.email.setText(selectedTeacher.getEmail());
        teacherEditController.address.setText(selectedTeacher.getAddress());
        teacherEditController.phone.setText(selectedTeacher.getPhone());
        teacherEditController.birthdate.setValue(selectedTeacher.getBirthdate());
    }

    private void addEventFilterToUpdateTeacherButton() {
        teacherEditController.updateButton.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> refreshTableView());
        teacherEditController.updateButton.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent -> refreshTableView());
    }

    private void addEventFilterToSaveTeacherButton() {
        teacherCreateController.saveButton.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> refreshTableView());
        teacherCreateController.saveButton.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent -> refreshTableView());
    }

    private void addEventFilterToDeleteTeacherButton() {
        teacherEditController.deleteButton.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> refreshTableView());
        teacherEditController.deleteButton.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent -> refreshTableView());
    }

    private void addEventListenerToSearchButton() {
        teacherSearchController.searchButton.setOnAction(event -> searchTeachers());
    }

    private void addEventListenerToResetFiltersButton() {
        teacherSearchController.resetFiltersButton.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> resetFilters());
    }

    private ObservableList<TeacherDto> getTeachers() {
        List<TeacherDto> teachers = teacherService.findAllTeachers();
        return FXCollections.observableArrayList(teachers);
    }

    private void searchTeachers() {
        TeacherRequest request = teacherSearchController.getFilters();
        teachersTableView.setItems(FXCollections.observableArrayList(teacherService.searchTeachers(request)));
    }

    private void resetFilters() {
        teachersTableView.setItems(FXCollections.observableArrayList(teacherService.findAllTeachers()));
    }

    public void refreshTableView() {
        teachersTableView.setItems(getTeachers());
    }
}
