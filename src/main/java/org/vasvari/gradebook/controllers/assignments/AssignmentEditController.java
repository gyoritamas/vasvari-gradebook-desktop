package org.vasvari.gradebook.controllers.assignments;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.vasvari.gradebook.dto.AssignmentInput;
import org.vasvari.gradebook.dto.AssignmentOutput;
import org.vasvari.gradebook.dto.AssignmentType;
import org.vasvari.gradebook.dto.dataTypes.SimpleData;
import org.vasvari.gradebook.service.AssignmentService;
import org.vasvari.gradebook.service.SubjectService;
import org.vasvari.gradebook.util.Validator;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@FxmlView("view/fxml/assignments/assignmentEdit.fxml")
@Component
@RequiredArgsConstructor
@Slf4j
public class AssignmentEditController implements Initializable {

    private final AssignmentService assignmentService;
    private final SubjectService subjectService;
    private final Validator validator;

    private Long selectedId;

    @FXML
    public GridPane assignmentEditTab;
    @FXML
    public TextField assignmentTitle;
    @FXML
    public ComboBox<SimpleData> assignmentSubject;
    @FXML
    public ComboBox<AssignmentType> assignmentType;
    @FXML
    public TextArea description;
    @FXML
    public DatePicker deadline;
    @FXML
    public Label titleErrorLabel;
    @FXML
    public Label deadlineErrorLabel;
    @FXML
    public Button updateButton;
    @FXML
    public Button deleteButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info("initialize AssignmentEditController");
        initializeSubjectComboBox();
        initializeAssignmentTypeComboBox();
        addEventFilterToFields();
    }

    private void initializeSubjectComboBox() {
        List<SimpleData> subjects = subjectService.findSubjectsForUser().stream()
                .map(subject -> new SimpleData(subject.getId(), subject.getName()))
                .collect(Collectors.toList());
        assignmentSubject.getItems().addAll(subjects);
    }

    private void initializeAssignmentTypeComboBox() {
        assignmentType.getItems().addAll(AssignmentType.values());
    }

    private void addEventFilterToFields() {
        assignmentTitle.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (selectedId != null && (oldValue != null && !oldValue.equals(newValue)) || (oldValue == null && newValue != null))
                validator.assignmentTitle(assignmentTitle, titleErrorLabel);
            updateButton.setDisable(isAnyFieldInvalid());
        });
        deadline.getEditor().textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (selectedId != null && (oldValue != null && !oldValue.equals(newValue)) || (oldValue == null && newValue != null))
                validator.deadline(deadline, deadlineErrorLabel);
            updateButton.setDisable(isAnyFieldInvalid());
        });
    }

    private boolean isAnyFieldInvalid() {
        return !titleErrorLabel.getText().isEmpty() || !deadlineErrorLabel.getText().isEmpty();
    }

    public void updateAssignment() {
        if (selectedId == null) return;

        AssignmentInput assignment = AssignmentInput.builder()
                .name(assignmentTitle.getText())
                .type(assignmentType.getValue())
                .subjectId(assignmentSubject.getValue().getId())
                .description(description.getText())
                .deadline(deadline.getValue())
                .build();

        assignmentService.updateAssignment(selectedId, assignment);
    }

    public void emptyEditForm() {
        setSelectedId(null);
        assignmentTitle.setText(null);
        assignmentType.setValue(null);
        assignmentSubject.setValue(null);
        description.setText(null);
        deadline.setValue(null);
        assignmentEditTab.setDisable(true);
    }

    public void populateEditForm(AssignmentOutput assignment) {
        assignmentEditTab.setDisable(false);
        setSelectedId(assignment.getId());
        assignmentTitle.setText(assignment.getName());
        assignmentType.setValue(assignment.getType());
        assignmentSubject.setValue(assignment.getSubject());
        description.setText(assignment.getDescription());
        deadline.setValue(assignment.getDeadline());
    }

    public void deleteAssignment() {
        if (selectedId == null) return;
        assignmentService.deleteAssignment(selectedId);
    }

    public void setSelectedId(Long selectedId) {
        this.selectedId = selectedId;
    }
}
