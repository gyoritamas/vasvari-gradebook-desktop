package org.vasvari.gradebook.controllers.students;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.vasvari.gradebook.dto.StudentDto;
import org.vasvari.gradebook.dto.dataTypes.InitialCredentials;
import org.vasvari.gradebook.service.UserService;
import org.vasvari.gradebook.util.UserUtil;

import java.net.URL;
import java.util.ResourceBundle;

@FxmlView("view/fxml/students/studentUser.fxml")
@Component
@RequiredArgsConstructor
@Slf4j
public class StudentUserController implements Initializable {

    private final UserService userService;
    private final UserUtil userUtil;

    private Long selectedStudentId;

    @FXML
    public GridPane studentUserPane;
    @FXML
    public Label nameLabel;
    @FXML
    public Label nameValueLabel;

    @FXML
    public Button createUserButton;

    @FXML
    public Label credentialsLabel;
    @FXML
    public Label usernameLabel;
    @FXML
    public TextField usernameField;
    @FXML
    public Label passwordLabel;
    @FXML
    public TextField passwordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(!userUtil.hasAnyRole("ADMIN")) return;
        log.info("initialize StudentUserController");
        hideCredentials();
        createUserButton.setVisible(false);
    }

    @FXML
    public void createUser() {
        createUserButton.setDisable(true);
        showCredentials();
        InitialCredentials credentials = userService.createStudentUser(selectedStudentId);
        usernameField.setText(credentials.getUsername());
        passwordField.setText(credentials.getPassword());
    }

    public void emptyForm() {
        selectedStudentId = null;
        nameLabel.setText("név");
        nameValueLabel.setText(null);
        hideCredentials();
        studentUserPane.setDisable(true);
    }

    public void populateForm(StudentDto selectedStudent) {
        studentUserPane.setDisable(false);
        hideCredentials();
        selectedStudentId = selectedStudent.getId();

        userService.findStudentUser(selectedStudentId).ifPresentOrElse((user) -> {
                    nameLabel.setText("felh. név");
                    nameValueLabel.setText(user.getUsername());
                    createUserButton.setVisible(false);
                },
                () -> {
                    nameLabel.setText("név");
                    nameValueLabel.setText(selectedStudent.getName());
                    createUserButton.setDisable(false);
                    createUserButton.setVisible(true);
                });
    }

    private void hideCredentials() {
        credentialsLabel.setVisible(false);
        usernameLabel.setVisible(false);
        usernameField.setVisible(false);
        passwordLabel.setVisible(false);
        passwordField.setVisible(false);
    }

    private void showCredentials() {
        credentialsLabel.setVisible(true);
        usernameLabel.setVisible(true);
        usernameField.setVisible(true);
        passwordLabel.setVisible(true);
        passwordField.setVisible(true);
    }
}
