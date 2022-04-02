package org.vasvari.gradebook.controllers.contentarea;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vasvari.gradebook.dto.SubjectInput;
import org.vasvari.gradebook.dto.SubjectOutput;
import org.vasvari.gradebook.service.SubjectService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@FxmlView("view/fxml/contentarea/paneB.fxml")
@Component
public class PaneB implements Initializable {
    private final SubjectService subjectService;

    @Autowired
    public PaneB(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @FXML
    private TableView<SubjectOutput> subjectsTableView;
    @FXML
    public TableColumn<SubjectOutput, String> nameColumn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<SubjectOutput> data = getClasses();
        setTableColumns();
        subjectsTableView.setItems(data);
    }

    private void setTableColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("course"));
    }

    private ObservableList<SubjectOutput> getClasses() {
        List<SubjectOutput> classes = subjectService.findAllSubjects().stream()
                .map(subject -> new SubjectOutput(
                        subject.getId(),
                        subject.getName(),
                        subject.getTeacher(),
                        subject.getStudents()))
                .collect(Collectors.toList());
        return FXCollections.observableArrayList(classes);
    }

    public void refreshTableView() {
        subjectsTableView.getItems().clear();
        subjectsTableView.setItems(getClasses());
    }

    public void addClass(ActionEvent actionEvent) {
        SubjectInput subject = SubjectInput.builder()
                .name("test subject")
                .teacherId(1L)
                .build();

        subjectService.saveSubject(subject);
        refreshTableView();
    }
}
