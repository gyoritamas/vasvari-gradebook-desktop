package org.vasvari.gradebook.controllers.users;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.vasvari.gradebook.dto.UserDto;
import org.vasvari.gradebook.service.UserService;
import org.vasvari.gradebook.util.UserUtil;

import java.net.URL;
import java.util.ResourceBundle;

@FxmlView("view/fxml/users/userEdit.fxml")
@Component
@Slf4j
@RequiredArgsConstructor
public class UserEditController implements Initializable {

    private final UserService userService;
    private final UserUtil userUtil;

    private Long selectedId;

    @FXML
    public GridPane userEditPane;
    @FXML
    public Label username;
    @FXML
    public Label userRole;
    @FXML
    public Label enabled;
    @FXML
    public Button changeEnableButton;
    @FXML
    public Button deleteButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(!userUtil.hasAnyRole("ADMIN")) return;
        log.info("initialize UserEditController");
    }

    public void changeEnabled() {
        if (selectedId == null) return;
        boolean enabled = userService.findUserById(selectedId).isEnabled();
        if (enabled)
            userService.disableUser(selectedId);
        else
            userService.enableUser(selectedId);
    }

    public void emptyEditForm() {
        changeEnableButton.setText("Módosítás");
        selectedId = null;
        username.setText(null);
        userRole.setText(null);
        enabled.setText(null);
        userEditPane.setDisable(true);
    }

    public void populateEditForm(UserDto selectedUser) {
        userEditPane.setDisable(false);
        changeEnableButton.setText(selectedUser.isEnabled() ? "Fiók kikapcsolása" : "Fiók bekapcsolása");
        selectedId = selectedUser.getId();
        username.setText(selectedUser.getUsername());
        userRole.setText(selectedUser.getRole().getLocalizedName());
        enabled.setText(selectedUser.isEnabled() ? "igen" : "nem");
    }

    public void deleteUser() {
        if (selectedId == null) return;
        userService.deleteUser(selectedId);
    }

}
