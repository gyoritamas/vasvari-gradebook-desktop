package org.vasvari.gradebook.controllers.assignments;

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
import org.vasvari.gradebook.dto.AssignmentOutput;
import org.vasvari.gradebook.model.request.AssignmentRequest;
import org.vasvari.gradebook.service.AssignmentService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@FxmlView("view/fxml/assignments/assignments.fxml")
@Component
@RequiredArgsConstructor
@Slf4j
public class AssignmentController implements Initializable {

    private final AssignmentService assignmentService;

    @FXML
    public TableView<AssignmentOutput> assignmentsTableView;
    @FXML
    public TableColumn<AssignmentOutput, Long> idColumn;
    @FXML
    public TableColumn<AssignmentOutput, String> titleColumn;
    @FXML
    public TableColumn<AssignmentOutput, String> subjectColumn;
    @FXML
    public TableColumn<AssignmentOutput, String> typeColumn;
    @FXML
    public TableColumn<AssignmentOutput, String> descriptionColumn;
    @FXML
    public TableColumn<AssignmentOutput, String> deadlineColumn;

    @FXML
    public AssignmentSearchController assignmentSearchController;
    @FXML
    public AssignmentCreateController assignmentCreateController;
    @FXML
    public AssignmentEditController assignmentEditController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info("initialize AssignmentController");
        assignmentEditController.assignmentEditTab.setDisable(true);
        initializeTableColumns();
        initializeTable();
        addEventListenerToTable();
        addEventListenerToSearchButton();
        addEventListenerToResetFiltersButton();
//        addEventListenerToSaveButton();
        addEventListenerToUpdateButton();
        addEventListenerToDeleteButton();
    }

    private void initializeTableColumns() {
        log.info("initialize assignment table columns");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
    }

    private void initializeTable() {
        log.info("initialize assignment table");
        ObservableList<AssignmentOutput> data = getAssignments();
        assignmentsTableView.setItems(data);
    }

    private void addEventListenerToTable() {
        assignmentsTableView.getSelectionModel().selectedItemProperty()
                .addListener((observableValue, oldValue, newValue) -> {
                    AssignmentOutput selectedAssignment = assignmentsTableView.getSelectionModel().getSelectedItem();
                    if (selectedAssignment == null) {
                        assignmentEditController.emptyEditForm();
                    } else {
                        assignmentEditController.populateEditForm(selectedAssignment);
                    }
                });
    }

    private void addEventListenerToSearchButton() {
        assignmentSearchController.searchButton.setOnAction(event -> searchAssignments());
    }

    private void addEventListenerToResetFiltersButton() {
        assignmentSearchController.resetFiltersButton.setOnAction(actionEvent -> {
            assignmentSearchController.resetFilters();
            refreshTableView();
        });
    }

    private void addEventListenerToSaveButton() {
        assignmentCreateController.saveButton.setOnAction(actionEvent -> {
            assignmentCreateController.saveAssignment();
            refreshTableView();
        });
    }

    private void addEventListenerToUpdateButton() {
        assignmentEditController.updateButton.setOnAction(actionEvent -> {
            assignmentEditController.updateAssignment();
            refreshTableView();
        });
    }

    private void addEventListenerToDeleteButton() {
        assignmentEditController.deleteButton.setOnAction(actionEvent -> {
            assignmentEditController.deleteAssignment();
            refreshTableView();
        });
    }

    private ObservableList<AssignmentOutput> getAssignments() {
        List<AssignmentOutput> assignments = assignmentService.findAssignmentsForUser();
        return FXCollections.observableArrayList(assignments);
    }

    private void searchAssignments() {
        AssignmentRequest request = assignmentSearchController.getFilters();
        assignmentsTableView.setItems(FXCollections.observableArrayList(assignmentService.findAssignmentsForUser(request)));
    }

    private void refreshTableView() {
        assignmentsTableView.setItems(getAssignments());
    }


}
