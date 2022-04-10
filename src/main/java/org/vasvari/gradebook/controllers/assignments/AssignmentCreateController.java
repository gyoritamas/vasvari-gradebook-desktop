package org.vasvari.gradebook.controllers.assignments;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.vasvari.gradebook.dto.AssignmentInput;
import org.vasvari.gradebook.dto.AssignmentType;
import org.vasvari.gradebook.dto.dataTypes.SimpleData;
import org.vasvari.gradebook.service.AssignmentService;
import org.vasvari.gradebook.service.SubjectService;
import org.vasvari.gradebook.util.EventListenerFactory;
import org.vasvari.gradebook.util.Validator;

import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@FxmlView("view/fxml/assignments/assignmentCreate.fxml")
@Component
@RequiredArgsConstructor
@Slf4j
public class AssignmentCreateController implements Initializable {

    private final LocalDate TOMORROW = LocalDate.now().plusDays(1);

    private final AssignmentService assignmentService;
    private final SubjectService subjectService;
    private final Validator validator;
    private final EventListenerFactory eventListenerFactory;

    @FXML
    public TextField assignmentTitle;
    @FXML
    public ComboBox<SimpleData> assignmentSubject;
    @FXML
    public ComboBox<String> assignmentType;
    @FXML
    public TextArea description;
    @FXML
    public DatePicker deadline;
    @FXML
    public Label titleErrorLabel;
    @FXML
    public Label subjectErrorLabel;
    @FXML
    public Label typeErrorLabel;
    @FXML
    public Label deadlineErrorLabel;
    @FXML
    public Button emptyFormButton;
    @FXML
    public Button saveButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info("initialize AssignmentCreateController");
        initializeSubjectComboBox();
        initializeAssignmentTypeComboBox();
        initializeDeadlineDatePicker();
        addEventListenerToFields();
    }

    private void initializeSubjectComboBox() {
        List<SimpleData> subjects = subjectService.findSubjectsForUser().stream()
                .map(subject -> new SimpleData(subject.getId(), subject.getName()))
                .sorted(Comparator.comparing(SimpleData::getName))
                .collect(Collectors.toList());
        assignmentSubject.getItems().addAll(subjects);
    }

    private void initializeAssignmentTypeComboBox() {
        List<String> assignmentTypes = Arrays.stream(AssignmentType.values())
                .map(AssignmentType::getLocalizedName)
                .collect(Collectors.toList());

        assignmentType.getItems().addAll(assignmentTypes);
    }

    private void initializeDeadlineDatePicker() {
        deadline.setValue(TOMORROW);
    }

    private void addEventListenerToFields() {
        eventListenerFactory.onTextFieldChangeDeleteErrorMessage(assignmentTitle, titleErrorLabel);
        eventListenerFactory.onComboBoxChangeDeleteErrorMessage(assignmentSubject, subjectErrorLabel);
        eventListenerFactory.onComboBoxChangeDeleteErrorMessage(assignmentType, typeErrorLabel);
        eventListenerFactory.onDatePickerChangeDeleteErrorMessage(deadline, deadlineErrorLabel);
    }

    public void saveAssignment() {
        validateFields();
        if (isAnyFieldInvalid()) return;

        AssignmentInput assignment = AssignmentInput.builder()
                .name(assignmentTitle.getText())
                .type(AssignmentType.getAssignmentTypeByLocalizedName(assignmentType.getValue()))
                .subjectId(assignmentSubject.getValue().getId())
                .description(description.getText())
                .deadline(deadline.getValue())
                .build();

        assignmentService.saveAssignment(assignment);
        deleteFormFields();
    }

    private void validateFields() {
        validator.assignmentTitle(assignmentTitle, titleErrorLabel);
        validator.assignmentSubject(assignmentSubject, subjectErrorLabel);
        validator.assignmentType(assignmentType, typeErrorLabel);
        validator.deadline(deadline, deadlineErrorLabel);
    }

    @FXML
    public void emptyForm() {
        deleteFormFields();
        deleteErrorMessages();
    }

    private void deleteFormFields() {
        assignmentTitle.setText(null);
        assignmentSubject.setValue(null);
        assignmentType.setValue(null);
        description.setText(null);
        deadline.setValue(TOMORROW);
    }

    private void deleteErrorMessages() {
        titleErrorLabel.setText(null);
        subjectErrorLabel.setText(null);
        typeErrorLabel.setText(null);
        deadlineErrorLabel.setText(null);
    }

    private boolean isAnyFieldInvalid() {
        return !titleErrorLabel.getText().isEmpty() || !subjectErrorLabel.getText().isEmpty()
                || !typeErrorLabel.getText().isEmpty() || !deadlineErrorLabel.getText().isEmpty();
    }
}
