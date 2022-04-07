package org.vasvari.gradebook.controllers.assignments;

import javafx.fxml.Initializable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@FxmlView("view/fxml/assignments/assignmentSearch.fxml")
@Component
@RequiredArgsConstructor
@Slf4j
public class AssignmentSearchController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
