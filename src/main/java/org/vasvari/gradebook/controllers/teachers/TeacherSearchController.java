package org.vasvari.gradebook.controllers.teachers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.vasvari.gradebook.model.request.TeacherRequest;

import java.net.URL;
import java.util.ResourceBundle;

@FxmlView("view/fxml/teachers/teacherSearch.fxml")
@Component
@Slf4j
@RequiredArgsConstructor
public class TeacherSearchController implements Initializable {

    @FXML
    public TextField teacherName;
    @FXML
    public Button searchButton;
    @FXML
    public Button resetFiltersButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public TeacherRequest getFilters() {
        String name = teacherName.getText();
        return new TeacherRequest(name);
    }

    public void resetFilters() {
        teacherName.setText(null);
    }
}
