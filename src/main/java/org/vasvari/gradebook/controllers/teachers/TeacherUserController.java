package org.vasvari.gradebook.controllers.teachers;

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
import org.vasvari.gradebook.dto.TeacherDto;
import org.vasvari.gradebook.dto.dataTypes.InitialCredentials;
import org.vasvari.gradebook.service.UserService;

import java.net.URL;
import java.util.ResourceBundle;

@FxmlView("view/fxml/teacher/teacherUser.fxml")
@Component
@RequiredArgsConstructor
@Slf4j
public class TeacherUserController implements Initializable {

    private final UserService userService;

    private Long selectedTeacherId;

    @FXML
    public GridPane teacherUserPane;
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
        log.info("initialize TeacherUserController");
        hideCredentials();
        createUserButton.setVisible(false);
    }

    @FXML
    public void createUser() {
        createUserButton.setDisable(true);
        showCredentials();
        InitialCredentials credentials = userService.createTeacherUser(selectedTeacherId);
        usernameField.setText(credentials.getUsername());
        passwordField.setText(credentials.getPassword());
    }

    public void emptyForm() {
        selectedTeacherId = null;
        nameLabel.setText("név");
        nameValueLabel.setText(null);
        hideCredentials();
        teacherUserPane.setDisable(true);
    }

    public void populateForm(TeacherDto selectedTeacher) {
        teacherUserPane.setDisable(false);
        hideCredentials();
        selectedTeacherId = selectedTeacher.getId();

        userService.findTeacherUser(selectedTeacherId).ifPresentOrElse((user) -> {
                    nameLabel.setText("felh. név");
                    nameValueLabel.setText(user.getUsername());
                    createUserButton.setVisible(false);
                },
                () -> {
                    nameLabel.setText("név");
                    nameValueLabel.setText(selectedTeacher.getName());
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
