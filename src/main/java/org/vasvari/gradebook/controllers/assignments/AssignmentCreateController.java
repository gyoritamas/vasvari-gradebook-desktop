package org.vasvari.gradebook.controllers.assignments;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@FxmlView("view/fxml/assignments/assignmentCreate.fxml")
@Component
@RequiredArgsConstructor
@Slf4j
public class AssignmentCreateController implements Initializable {

    @FXML
    public Button saveButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void saveAssignment() {
    }
}