package org.vasvari.gradebook.controllers.users;

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
import org.vasvari.gradebook.model.request.UserRequest;
import org.vasvari.gradebook.service.UserService;
import org.vasvari.gradebook.viewmodel.UserViewModel;
import org.vasvari.gradebook.viewmodel.mapper.UserViewModelMapper;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@FxmlView("view/fxml/users/users.fxml")
@Component
@Slf4j
@RequiredArgsConstructor
public class UserController implements Initializable {

    private final UserService userService;
    private final UserViewModelMapper mapper;

    @FXML
    public TableView<UserViewModel> usersTableView;
    @FXML
    public TableColumn<UserViewModel, Long> idColumn;
    @FXML
    public TableColumn<UserViewModel, String> nameColumn;
    @FXML
    public TableColumn<UserViewModel, String> roleColumn;
    @FXML
    public TableColumn<UserViewModel, String> activeColumn;

    @FXML
    public UserSearchController userSearchController;
    @FXML
    public UserCreateController userCreateController;
    @FXML
    public UserEditController userEditController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info("initialize UserController");
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
        log.info("initialize users table columns");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        activeColumn.setCellValueFactory(new PropertyValueFactory<>("enabled"));
    }

    private void initializeTable() {
        log.info("initialize users table");
        usersTableView.setItems(findAllUsers());
    }

    private void addEventListenerToTable() {
//        usersTableView.getSelectionModel().selectedItemProperty()
//                .addListener((observable, oldValue, newValue) -> {
//                    UserViewModel selectedUser = usersTableView.getSelectionModel().getSelectedItem();
//                    if (selectedUser == null) {
//                        userEditController.emptyEditForm();
//                    } else {
//                        userEditController.populateEditForm(selectedUser);
//                    }
//                });
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

    private void addEventListenerToUpdateButton(){
        // TODO
    }

    private void addEventListenerToDeleteButton(){
        // TODO
    }

    private ObservableList<UserViewModel> findAllUsers() {
        List<UserViewModel> userList = mapper.mapAll(userService.findAllUsers());
        return FXCollections.observableArrayList(userList);
    }

    private void searchUsers() {
        UserRequest request = userSearchController.getFilters();
        List<UserViewModel> users = mapper.mapAll(userService.searchUsers(request));
        usersTableView.setItems(FXCollections.observableArrayList(users));
    }

    private void refreshTable() {
        usersTableView.setItems(findAllUsers());
    }
}
