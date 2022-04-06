package org.vasvari.gradebook.controllers.subjects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.vasvari.gradebook.model.request.SubjectRequest;

import java.net.URL;
import java.util.ResourceBundle;

@FxmlView("view/fxml/subjects/subjectSearch.fxml")
@Component
@Slf4j
@RequiredArgsConstructor
public class SubjectSearchController implements Initializable {

    @FXML
    public TextField subjectName;
    @FXML
    public Button resetFiltersButton;
    @FXML
    public Button searchButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public SubjectRequest getFilters() {
        String name = subjectName.getText();
        return new SubjectRequest(name);
    }

    public void resetFilters(ActionEvent actionEvent) {
        subjectName.setText(null);
    }
}
