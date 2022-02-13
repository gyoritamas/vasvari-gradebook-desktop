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
import org.vasvari.gradebook.dto.ClassInput;
import org.vasvari.gradebook.dto.ClassOutput;
import org.vasvari.gradebook.service.ClassService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@FxmlView("view/fxml/contentarea/paneB.fxml")
@Component
public class PaneB implements Initializable {
    private final ClassService classService;

    @Autowired
    public PaneB(ClassService classService) {
        this.classService = classService;
    }

    @FXML
    private TableView<ClassOutput> classesTableView;
    @FXML
    public TableColumn<ClassOutput, String> nameColumn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<ClassOutput> data = getClasses();
        setTableColumns();
        classesTableView.setItems(data);
    }

    private void setTableColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("course"));
    }

    private ObservableList<ClassOutput> getClasses() {
        List<ClassOutput> classes = classService.findAllClasses().stream()
                .map(clazz -> new ClassOutput(
                        clazz.getId(),
                        clazz.getCourse(),
                        clazz.getStudents()))
                .collect(Collectors.toList());
        return FXCollections.observableArrayList(classes);
    }

    public void refreshTableView() {
        classesTableView.getItems().clear();
        classesTableView.setItems(getClasses());
    }

    public void addClass(ActionEvent actionEvent) {
        ClassInput classInput = new ClassInput("TEST CLASS");

        classService.save(classInput);
        refreshTableView();
    }
}
