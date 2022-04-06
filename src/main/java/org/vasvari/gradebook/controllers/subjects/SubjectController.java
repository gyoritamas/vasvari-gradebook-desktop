package org.vasvari.gradebook.controllers.subjects;

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
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.vasvari.gradebook.model.request.SubjectRequest;
import org.vasvari.gradebook.service.SubjectService;
import org.vasvari.gradebook.viewmodel.SubjectViewModel;
import org.vasvari.gradebook.viewmodel.mapper.SubjectViewModelMapper;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@FxmlView("view/fxml/subjects/subjects.fxml")
@Component
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
    public TableColumn<SubjectViewModel, String> teacherColumn;
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
        initializeTableColumns();
        initializeTable();
        addEventListenerToRefreshButton();
        addEventListenerToSearchButton();
        addEventFiltersToResetFiltersButton();
    }

    private void initializeTable() {
        subjectsTableView.setItems(findAllSubjects());
    }

    private void initializeTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        teacherColumn.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        studentsColumn.setCellValueFactory(new PropertyValueFactory<>("studentsCount"));
        assignmentsColumn.setCellValueFactory(new PropertyValueFactory<>("assignmentsCount"));
    }

    public void addEventListenerToRefreshButton() {
        subjectEditController.refreshButton.setOnAction(event -> refreshTableView());
    }

    public void addEventListenerToSearchButton() {
        subjectSearchController.searchButton.setOnAction(event -> searchSubjects());
    }

    private void addEventFiltersToResetFiltersButton() {
        subjectSearchController.resetFiltersButton.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> resetFilters());
        subjectSearchController.resetFiltersButton.addEventFilter(KeyEvent.KEY_RELEASED,mouseEvent -> resetFilters());
    }

    private ObservableList<SubjectViewModel> findAllSubjects() {
        List<SubjectViewModel> subjects = mapper.mapAll(subjectService.findSubjectsForUser());
        return FXCollections.observableArrayList(subjects);
    }

    private void searchSubjects() {
        SubjectRequest request = subjectSearchController.getFilters();
        List<SubjectViewModel> subjects = mapper.mapAll(subjectService.searchSubjects(request));
        subjectsTableView.setItems(FXCollections.observableArrayList(subjects));
    }

    private void resetFilters() {
        List<SubjectViewModel> subjects = mapper.mapAll(subjectService.findSubjectsForUser());
        subjectsTableView.setItems(FXCollections.observableArrayList(subjects));
    }

    public void refreshTableView() {
        subjectsTableView.getItems().clear();
        subjectsTableView.setItems(findAllSubjects());
    }

}
