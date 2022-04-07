package org.vasvari.gradebook.controllers.subjects;

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
import org.vasvari.gradebook.dto.dataTypes.SimpleTeacher;
import org.vasvari.gradebook.model.request.SubjectRequest;
import org.vasvari.gradebook.service.SubjectService;
import org.vasvari.gradebook.viewmodel.SubjectViewModel;
import org.vasvari.gradebook.viewmodel.mapper.SubjectViewModelMapper;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@FxmlView("view/fxml/subjects/subjects.fxml")
@Component
@Slf4j
@RequiredArgsConstructor
public class SubjectController implements Initializable {

    private final SubjectService subjectService;
    private final SubjectViewModelMapper mapper;

    @FXML
    private TableView<SubjectViewModel> subjectsTableView;
    @FXML
    public TableColumn<SubjectViewModel, Long> idColumn;
    @FXML
    public TableColumn<SubjectViewModel, String> nameColumn;
    @FXML
    public TableColumn<SubjectViewModel, SimpleTeacher> teacherColumn;
    @FXML
    public TableColumn<SubjectViewModel, String> studentsColumn;
    @FXML
    public TableColumn<SubjectViewModel, String> assignmentsColumn;

    @FXML
    public SubjectEditController subjectEditController;
    @FXML
    public SubjectSearchController subjectSearchController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        subjectEditController.subjectEditTab.setDisable(true);
        initializeTableColumns();
        initializeTable();
        addEventListenerToTable();
        addEventListenerToSearchButton();
        addEventListenerToResetFiltersButton();
        addEventListenerToUpdateButton();
        addEventListenerToDeleteButton();
    }

    private void initializeTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        teacherColumn.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        studentsColumn.setCellValueFactory(new PropertyValueFactory<>("studentsCount"));
        assignmentsColumn.setCellValueFactory(new PropertyValueFactory<>("assignmentsCount"));
    }

    private void initializeTable() {
        log.info("initialize subject table");
        subjectsTableView.setItems(findAllSubjects());
    }

    private void addEventListenerToTable() {
        log.info("add event listener to subject table");
        subjectsTableView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    SubjectViewModel selectedSubject = subjectsTableView.getSelectionModel().getSelectedItem();
                    if (selectedSubject == null) {
                        subjectEditController.emptyEditForm();
                    } else {
                        subjectEditController.populateEditForm(selectedSubject);
                    }
                });
    }

    private ObservableList<SubjectViewModel> findAllSubjects() {
        List<SubjectViewModel> subjects = mapper.mapAll(subjectService.findSubjectsForUser());
        return FXCollections.observableArrayList(subjects);
    }

    private void addEventListenerToSearchButton() {
        subjectSearchController.searchButton.setOnAction(event -> searchSubjects());
    }

    private void searchSubjects() {
        SubjectRequest request = subjectSearchController.getFilters();
        List<SubjectViewModel> subjects = mapper.mapAll(subjectService.searchSubjects(request));
        subjectsTableView.setItems(FXCollections.observableArrayList(subjects));
    }

    private void addEventListenerToUpdateButton() {
        subjectEditController.updateButton.setOnAction(actionEvent -> {
            subjectEditController.updateSubject();
            refreshTable();
        });
    }

    private void addEventListenerToResetFiltersButton() {
        subjectSearchController.resetFiltersButton.setOnAction(actionEvent -> {
            subjectSearchController.resetFilters();
            refreshTable();
        });
    }

    private void addEventListenerToDeleteButton() {
        subjectEditController.deleteButton.setOnAction(actionEvent -> {
            subjectEditController.deleteSubject();
            refreshTable();
        });
    }

    private void refreshTable() {
        subjectsTableView.setItems(findAllSubjects());
    }

}
