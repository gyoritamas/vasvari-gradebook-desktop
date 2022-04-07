package org.vasvari.gradebook.controllers.users;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.vasvari.gradebook.dto.UserRole;
import org.vasvari.gradebook.model.request.UserRequest;
import org.vasvari.gradebook.viewmodel.UserEnabledFilterValue;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static org.vasvari.gradebook.viewmodel.UserEnabledFilterValue.BOTH;
import static org.vasvari.gradebook.viewmodel.UserEnabledFilterValue.ENABLED;

@FxmlView("view/fxml/users/userSearch.fxml")
@Component
@Slf4j
@RequiredArgsConstructor
public class UserSearchController implements Initializable {

    private static final String USER_ROLE_DEFAULT_VALUE = "minden szerepkör";

    @FXML
    public TextField username;
    @FXML
    public ComboBox<String> roleFilter;
    @FXML
    public ComboBox<UserEnabledFilterValue> enabledFilter;
    @FXML
    public Button resetFiltersButton;
    @FXML
    public Button searchButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info("initialize UserSearchController");
        initializeRoleFilter();
        initializeEnabledFilter();
    }

    private void initializeRoleFilter() {
        List<String> listOfOptions = new ArrayList<>();
        listOfOptions.add(USER_ROLE_DEFAULT_VALUE);
        listOfOptions.addAll(Arrays.stream(UserRole.values())
                .map(UserRole::getLocalizedName)
                .collect(Collectors.toList()));
        ObservableList<String> roleOptions = FXCollections.observableArrayList(listOfOptions);
        roleFilter.getItems().addAll(roleOptions);
        roleFilter.setValue(USER_ROLE_DEFAULT_VALUE);
    }

    private void initializeEnabledFilter() {
        ObservableList<UserEnabledFilterValue> enabledOptions =
                FXCollections.observableArrayList(UserEnabledFilterValue.values());
        enabledFilter.getItems().addAll(enabledOptions);
        enabledFilter.setValue(BOTH);
    }

    public UserRequest getFilters() {
        String username = this.username.getText();
        UserRole role = roleFilter.getValue().equals(USER_ROLE_DEFAULT_VALUE) ? null : getEnumValueOfRoleFilter();
        Boolean enabled = enabledFilter.getValue().equals(BOTH) ? null : enabledFilter.getValue().equals(ENABLED);

        return new UserRequest(username, role, enabled);
    }

    private UserRole getEnumValueOfRoleFilter() {
        return Arrays.stream(UserRole.values())
                .filter(r -> r.getLocalizedName().equals(roleFilter.getValue()))
                .findFirst().orElseThrow(() -> new RuntimeException("Unrecognised user role"));
    }

    public void resetFilters() {
        username.setText(null);
        roleFilter.setValue(USER_ROLE_DEFAULT_VALUE);
        enabledFilter.setValue(BOTH);
    }
}
