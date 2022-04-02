package org.vasvari.gradebook.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.vasvari.gradebook.JavaFxApplication;
import org.vasvari.gradebook.controllers.LoginController;

import java.io.IOException;

@Controller
@FxmlView("../view/fxml/registration.fxml")
public class RegistrationController {

//    private final RegistrationService registrationService;
//
//    @FXML
//    private TextField username;
//
//    @FXML
//    private TextField password;
//
//    @FXML
//    private TextField email;
//
//    @FXML
//    private Label resultLabel;
//
//    @Autowired
//    public RegistrationController(RegistrationService registrationService) {
//        this.registrationService = registrationService;
//    }
//
//    public void register(ActionEvent actionEvent) {
//        String usernameInput = username.getText();
//        String passwordInput = password.getText();
//        String emailInput = email.getText();
//
//        RegistrationRequest request = new RegistrationRequest(usernameInput, passwordInput, emailInput);
//
//        resultLabel.setText(registerResult(request));
//    }
//
//    private String registerResult(RegistrationRequest request) {
//        return registrationService.register(request);
//    }
//
//    public void loadLogin(ActionEvent actionEvent) throws IOException {
//        JavaFxApplication.setRoot(LoginController.class);
//    }
}
