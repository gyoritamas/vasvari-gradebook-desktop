package org.vasvari.gradebook.controllers.users;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.vasvari.gradebook.dto.UserDto;
import org.vasvari.gradebook.model.request.UserRequest;
import org.vasvari.gradebook.service.UserService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@FxmlView("view/fxml/users/users.fxml")
@Component
@Slf4j
@RequiredArgsConstructor
public class UserController implements Initializable {

    private final UserService userService;

    @FXML
    public TabPane userTabPane;
    @FXML
    public TableView<UserDto> usersTableView;
    @FXML
    public TableColumn<UserDto, Long> idColumn;
    @FXML
    public TableColumn<UserDto, String> nameColumn;
    @FXML
    public TableColumn<UserDto, String> roleColumn;
    @FXML
    public TableColumn<UserDto, String> activeColumn;

    @FXML
    public UserSearchController userSearchController;
    @FXML
    public UserCreateController userCreateController;
    @FXML
    public UserEditController userEditController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info("initialize UserController");
        userEditController.userEditPane.setDisable(true);
        initializeTableColumns();
        initializeTable();
        addEventListenerToTable();
        addEventListenerToTabPane();
        addEventListenerToSearchButton();
        addEventListenerToResetFiltersButton();
        addEventListenerToSaveButton();
        addEventListenerToUpdateButton();
        addEventListenerToDeleteButton();
    }

    private void initializeTableColumns() {
        log.info("initialize users table columns");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        roleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRole().getLocalizedName()));
        activeColumn.setCellValueFactory(cellData -> {
            boolean enabled = cellData.getValue().isEnabled();
            return new SimpleStringProperty(enabled ? "igen" : "nem");
        });
    }

    private void initializeTable() {
        log.info("initialize users table");
        usersTableView.setItems(findAllUsers());
    }

    private void addEventListenerToTable() {
        usersTableView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    UserDto selectedUser = usersTableView.getSelectionModel().getSelectedItem();
                    if (selectedUser == null) {
                        userEditController.emptyEditForm();
                    } else {
                        userEditController.populateEditForm(selectedUser);
                    }
                });
    }

    private void addEventListenerToTabPane() {
        userTabPane.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.getId().equals("#userCreateTab")) userCreateController.emptyForm();
        });
    }

    private void addEventListenerToSearchButton() {
        userSearchController.searchButton.setOnAction(event -> searchUsers());
    }

    private void addEventListenerToResetFiltersButton() {
        userSearchController.resetFiltersButton.setOnAction(actionEvent -> {
            userSearchController.resetFilters();
            refreshTable();
        });
    }

    private void addEventListenerToSaveButton() {
        userCreateController.saveButton.setOnAction(actionEvent -> {
            userCreateController.saveUser();
            refreshTable();
        });
    }

    private void addEventListenerToUpdateButton() {
        userEditController.changeEnableButton.setOnAction(actionEvent -> {
            userEditController.changeEnabled();
            refreshTable();
        });
    }

    private void addEventListenerToDeleteButton() {
        userEditController.deleteButton.setOnAction(actionEvent -> {
            userEditController.deleteUser();
            refreshTable();
        });
    }

    private ObservableList<UserDto> findAllUsers() {
        List<UserDto> userList = userService.findAllUsers();
        return FXCollections.observableArrayList(userList);
    }

    private void searchUsers() {
        UserRequest request = userSearchController.getFilters();
        List<UserDto> users = userService.searchUsers(request);
        usersTableView.setItems(FXCollections.observableArrayList(users));
    }

    private void refreshTable() {
        usersTableView.setItems(findAllUsers());
    }
}
