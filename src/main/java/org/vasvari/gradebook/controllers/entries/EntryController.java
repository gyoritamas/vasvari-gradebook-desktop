package org.vasvari.gradebook.controllers.entries;

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
import org.vasvari.gradebook.dto.GradebookOutput;
import org.vasvari.gradebook.dto.dataTypes.SimpleData;
import org.vasvari.gradebook.dto.dataTypes.SimpleStudent;
import org.vasvari.gradebook.model.request.GradebookRequest;
import org.vasvari.gradebook.service.GradebookService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@FxmlView("view/fxml/entries/entries.fxml")
@Component
@RequiredArgsConstructor
@Slf4j
public class EntryController implements Initializable {

    private final GradebookService gradebookService;

    @FXML
    public TableView<GradebookOutput> entriesTableView;
    @FXML
    public TableColumn<GradebookOutput, Long> idColumn;
    @FXML
    public TableColumn<GradebookOutput, SimpleStudent> studentColumn;
    @FXML
    public TableColumn<GradebookOutput, SimpleData> subjectColumn;
    @FXML
    public TableColumn<GradebookOutput, SimpleData> assignmentColumn;
    @FXML
    public TableColumn<GradebookOutput, Integer> gradeColumn;

    @FXML
    public EntrySearchController entrySearchController;
    @FXML
    public EntryCreateController entryCreateController;
    @FXML
    public EntryEditController entryEditController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info("initialize EntryController");
        entryEditController.entryEditPane.setDisable(true);
        initializeTableColumns();
        initializeTable();
        addEventListenerToTable();
        addEventListenerToSearchButton();
        addEventListenerToResetFiltersButton();
        addEventListenerToSaveButton();
        addEventListenerToUpdateButton();
        addEventListenerToDeleteButton();
    }

    private void initializeTableColumns() {
        log.info("initialize entry table columns");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        studentColumn.setCellValueFactory(new PropertyValueFactory<>("student"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        assignmentColumn.setCellValueFactory(new PropertyValueFactory<>("assignment"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));
    }

    private void initializeTable() {
        log.info("initialize entry table");
        ObservableList<GradebookOutput> data = getGradebookEntries();
        entriesTableView.setItems(data);
    }

    private void addEventListenerToTable() {
        entriesTableView.getSelectionModel().selectedItemProperty()
                .addListener((observableValue, oldValue, newValue) -> {
                    GradebookOutput selectedEntry = entriesTableView.getSelectionModel().getSelectedItem();
                    if (selectedEntry == null) {
                        entryEditController.emptyEditForm();
                    } else {
                        entryEditController.populateEditForm(selectedEntry);
                    }
                });
    }

    private void addEventListenerToSearchButton() {
        entrySearchController.searchButton.setOnAction(event -> searchEntries());
    }

    private void addEventListenerToResetFiltersButton() {
        entrySearchController.resetFiltersButton.setOnAction(actionEvent -> {
            entrySearchController.resetFilters();
            refreshTableView();
        });
    }

    private void addEventListenerToSaveButton() {
        entryCreateController.saveButton.setOnAction(actionEvent -> {
            entryCreateController.saveEntry();
            refreshTableView();
        });
    }

    private void addEventListenerToUpdateButton() {
        entryEditController.updateButton.setOnAction(actionEvent -> {
            if (entryEditController.updateEntry()) refreshTableView();
        });
    }

    private void addEventListenerToDeleteButton() {
        entryEditController.deleteButton.setOnAction(actionEvent -> {
            entryEditController.deleteEntry();
            refreshTableView();
        });
    }

    private ObservableList<GradebookOutput> getGradebookEntries() {
        List<GradebookOutput> gradebookEntries = gradebookService.findGradebookEntriesForUser();
        return FXCollections.observableArrayList(gradebookEntries);
    }

    private void searchEntries() {
        GradebookRequest request = entrySearchController.getFilters();
        List<GradebookOutput> gradebookEntries = gradebookService.searchGradebookEntries(request);
        entriesTableView.setItems(FXCollections.observableArrayList(gradebookEntries));
    }

    private void refreshTableView() {
        entriesTableView.setItems(getGradebookEntries());
    }
}
