package org.vasvari.gradebook.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
import java.util.List;
import java.util.ResourceBundle;

import static org.vasvari.gradebook.JavaFxApplication.getMainScene;

@Component
@FxmlView("../view/fxml/main.fxml")
@Slf4j
@RequiredArgsConstructor
public class MainController implements Initializable {
    private final LoginGateway loginService;
    private final UserUtil userUtil;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JavaFxApplication.getTheStage().setTitle("E-napló");
        JavaFxApplication.getTheStage().setHeight(800);
        JavaFxApplication.getTheStage().setWidth(1200);
//        showSelectedPaneAndHideOthers("menuAContentArea");
        setUserLabel();
    }

    @FXML
    public Label userLabel;

    public void menuButtonClicked(ActionEvent actionEvent) {
        String buttonID = getSourceId(actionEvent.getSource());
        String paneName = buttonID.replaceAll("Button", "ContentArea");
        showSelectedPaneAndHideOthers(paneName);
    }

    private void showSelectedPaneAndHideOthers(String paneName) {
        // TODO: put this list in a field or something
        for (String id : List.of("A", "B", "C", "D")) {
            hidePaneWithName("menu" + id + "ContentArea");
        }
        showPaneWithName(paneName);
    }

    private void showPaneWithName(String paneName) {
        System.out.println("showPane: " + paneName);
        Scene mainScene = getMainScene();
        Pane selectedPane = (Pane) mainScene.lookup("#" + paneName);
        selectedPane.setVisible(true);
    }

    private void hidePaneWithName(String paneName) {
        System.out.println("hidePane: " + paneName);
        Scene mainScene = getMainScene();
        Pane selectedPane = (Pane) mainScene.lookup("#" + paneName);
        selectedPane.setVisible(false);
    }

    private String getSourceId(Object source) {
        //Output of source is Button[id=logsButton, styleClass=button leftPaneButton]'Logs' so splitting by single quote gets the name of the object.
        //return source.toString().split("'")[1];
        String sourceString = source.toString();
        String attributesString = sourceString.substring(sourceString.indexOf("[") + 1, sourceString.indexOf("]"));
        String[] attributes = attributesString.split(",");
        String id = "";
        for (String attribute : attributes) {
            if (attribute.contains("id=")) {
                id = attribute.split("=")[1];
            }
        }

        return id;
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
