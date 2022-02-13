package org.vasvari.gradebook.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.vasvari.gradebook.JavaFxApplication;
import org.vasvari.gradebook.service.LoginService;
import org.vasvari.gradebook.service.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static org.vasvari.gradebook.JavaFxApplication.getMainScene;

@Component
@FxmlView("../view/fxml/main.fxml")
public class MainController implements Initializable {
    private final LoginService loginService;

    @FXML
    public Label userLabel;

    @Autowired
    public MainController(LoginService loginService) {
        this.loginService = loginService;
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        JavaFxApplication.setRoot(LoginController.class);
    }

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
        System.out.println("sourceString : " + sourceString);
        String attributesString = sourceString.substring(sourceString.indexOf("[") + 1, sourceString.indexOf("]"));
        System.out.println("attributesString : " + attributesString);
        String[] attributes = attributesString.split(",");
        String id = "";
        for (String attribute : attributes) {
            if (attribute.contains("id=")) {
                System.out.println("attribute containing \"id=\" : " + attribute);
                id = attribute.split("=")[1];
            }
        }
        System.out.println("id : " + id);
        return id;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        showSelectedPaneAndHideOthers("menuAContentArea");
        setUserLabel();
    }

    private void setUserLabel() {
        userLabel.setText(getUserName());
    }

    private String getUserName() {
        if (loginService.getActiveUser().isPresent())
            return loginService.getActiveUser().get().getUsername();

        return "Nincs bejelentkezve";
    }
}
