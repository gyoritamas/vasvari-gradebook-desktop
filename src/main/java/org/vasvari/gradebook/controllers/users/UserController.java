package org.vasvari.gradebook.controllers.users;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.vasvari.gradebook.service.UserService;

import java.net.URL;
import java.util.ResourceBundle;

@FxmlView("view/fxml/users/users.fxml")
@Component
@Slf4j
@RequiredArgsConstructor
public class UserController implements Initializable {

    private final UserService userService;

    @FXML
    public TableView usersTableView;
    @FXML
    public TableColumn idColumn;
    @FXML
    public TableColumn nameColumn;
    @FXML
    public TableColumn roleColumn;
    @FXML
    public TableColumn activeColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
