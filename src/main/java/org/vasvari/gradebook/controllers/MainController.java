package org.vasvari.gradebook.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.vasvari.gradebook.JavaFxApplication;
import org.vasvari.gradebook.service.gateway.LoginGateway;
import org.vasvari.gradebook.util.UserUtil;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static org.vasvari.gradebook.JavaFxApplication.getMainScene;

@Component
@FxmlView("../view/fxml/main.fxml")
@Slf4j
@RequiredArgsConstructor
public class MainController implements Initializable {
    private final LoginGateway loginService;
    private final UserUtil userUtil;

    private ToggleButton selected;

    private ToggleGroup toggleGroup;

    private Map<ToggleButton, String> buttonMap;

    @FXML
    public ToggleButton studentsButton;
    @FXML
    public ToggleButton teachersButton;
    @FXML
    public ToggleButton subjectsButton;
    @FXML
    public ToggleButton assignmentsButton;
    @FXML
    public ToggleButton entriesButton;
    @FXML
    public ToggleButton usersButton;
    @FXML
    public ToggleButton profileButton;

    @FXML
    public Label userLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info("initialize MainController");
        JavaFxApplication.getTheStage().setTitle("E-napló");
        JavaFxApplication.getTheStage().setHeight(800);
        JavaFxApplication.getTheStage().setWidth(1300);
        mapButtonsToContentId();
        initializeToggleButtons();
        setUserLabel();
    }

    private void mapButtonsToContentId() {
        buttonMap = new HashMap<>();
        buttonMap.put(studentsButton, "#students");
        buttonMap.put(subjectsButton, "#subjects");
        buttonMap.put(assignmentsButton, "#assignments");
        buttonMap.put(entriesButton, "#entries");
        buttonMap.put(profileButton, "#profile");
        if (userUtil.hasAnyRole("ADMIN")) {
            buttonMap.put(teachersButton, "#teachers");
            buttonMap.put(usersButton, "#users");
        }
    }

    private void initializeToggleButtons() {
        if (userUtil.hasAnyRole("ADMIN")) {
            teachersButton.setVisible(true);
            usersButton.setVisible(true);
        }
        toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().addAll(buttonMap.keySet());
        toggleGroup.selectToggle(studentsButton);
        selected = studentsButton;
    }

    @FXML
    public void studentsButtonClicked() {
        hidePreviousActivePaneAndShowSelectedPane(studentsButton);
    }

    @FXML
    public void subjectsButtonClicked() {
        hidePreviousActivePaneAndShowSelectedPane(subjectsButton);
    }

    @FXML
    public void teachersButtonClicked() {
        hidePreviousActivePaneAndShowSelectedPane(teachersButton);
    }

    @FXML
    public void assignmentsButtonClicked() {
        hidePreviousActivePaneAndShowSelectedPane(assignmentsButton);
    }

    @FXML
    public void entriesButtonClicked() {
        hidePreviousActivePaneAndShowSelectedPane(entriesButton);
    }

    @FXML
    public void usersButtonClicked() {
        hidePreviousActivePaneAndShowSelectedPane(usersButton);
    }

    @FXML
    public void profileButtonClicked() {
        hidePreviousActivePaneAndShowSelectedPane(profileButton);
    }

    private void hidePreviousActivePaneAndShowSelectedPane(ToggleButton button) {
        Pane previousActiveBorderPane = (Pane) getMainScene().lookup(buttonMap.get(selected));
        previousActiveBorderPane.setVisible(false);
        selected = button;
        log.info("selected menu: {}", buttonMap.get(selected));
        Pane nextActiveBorderPane = (Pane) getMainScene().lookup(buttonMap.get(selected));
        nextActiveBorderPane.setVisible(true);
        toggleGroup.selectToggle(button);
    }

    private void setUserLabel() {
        String username = userUtil.username();
        String userRole = userUtil.userRole().getLocalizedName();
        userLabel.setText(String.format("%s (%s)", username, userRole));
    }

    @FXML
    public void logout() {
        loginService.logout();
        JavaFxApplication.setRoot(LoginController.class);
    }
}
