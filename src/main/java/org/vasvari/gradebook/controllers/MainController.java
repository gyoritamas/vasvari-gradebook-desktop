package org.vasvari.gradebook.controllers;

import javafx.event.ActionEvent;
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

import java.io.IOException;
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
    public Label userLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info("initialize MainController");
        JavaFxApplication.getTheStage().setTitle("E-napló");
        JavaFxApplication.getTheStage().setHeight(800);
        JavaFxApplication.getTheStage().setWidth(1200);
        initializeToggleButtons();
        mapButtonsToContentId();
        setUserLabel();
    }

    private void mapButtonsToContentId() {
        buttonMap = new HashMap<>();
        buttonMap.put(studentsButton, "#students");
        buttonMap.put(subjectsButton, "#menuBContentArea");
        buttonMap.put(teachersButton, "#teachers");
        buttonMap.put(assignmentsButton, "#menuDContentArea");
    }

    private void initializeToggleButtons() {
        toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().addAll(studentsButton, teachersButton, subjectsButton, assignmentsButton);
        toggleGroup.selectToggle(studentsButton);
        selected = studentsButton;
    }

    public void studentsButtonClicked(ActionEvent actionEvent) {
        hidePreviousActivePaneAndShowSelectedPane(studentsButton);
    }

    public void subjectsButtonClicked(ActionEvent actionEvent) {
        hidePreviousActivePaneAndShowSelectedPane(subjectsButton);
    }

    public void teachersButtonClicked(ActionEvent actionEvent) {
        hidePreviousActivePaneAndShowSelectedPane(teachersButton);
    }

    public void assignmentsButtonClicked(ActionEvent actionEvent) {
        hidePreviousActivePaneAndShowSelectedPane(assignmentsButton);
    }

    public void profileButtonClicked(ActionEvent actionEvent) {
        //hidePreviousActiveBorderPaneAndShowSelectedBorderPane(profileButton);
    }

    private void hidePreviousActivePaneAndShowSelectedPane(ToggleButton button) {
        Pane previousActiveBorderPane = (Pane) getMainScene().lookup(buttonMap.get(selected));
        previousActiveBorderPane.setVisible(false);
        selected = button;
        Pane nextActiveBorderPane = (Pane) getMainScene().lookup(buttonMap.get(selected));
        nextActiveBorderPane.setVisible(true);
        toggleGroup.selectToggle(button);
    }

    private void setUserLabel() {
        userLabel.setText(getUserName());
    }

    private String getUserName() {
        return userUtil.username();
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        loginService.logout();
        JavaFxApplication.setRoot(LoginController.class);
    }
}
