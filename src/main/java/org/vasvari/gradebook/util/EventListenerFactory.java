package org.vasvari.gradebook.util;

import javafx.scene.control.*;
import org.springframework.stereotype.Component;

@Component
public class EventListenerFactory {

    public void onComboBoxChangeDeleteErrorMessage(ComboBox<?> comboBox, Label errorLabel) {
        comboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if ((oldValue != null && !oldValue.equals(newValue)) || (oldValue == null && newValue != null))
                errorLabel.setText("");
        });
    }

    public void onTextFieldChangeDeleteErrorMessage(TextField textField, Label errorLabel) {
        textField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if ((oldValue != null && !oldValue.equals(newValue)) || (oldValue == null && newValue != null))
                errorLabel.setText("");
        });
    }

    public void onDatePickerChangeDeleteErrorMessage(DatePicker datePicker, Label errorLabel) {
        datePicker.getEditor().textProperty().addListener((observableValue, oldValue, newValue) -> {
            if ((oldValue != null && !oldValue.equals(newValue)) || (oldValue == null && newValue != null))
                errorLabel.setText("");
        });
    }

    public void onTextAreaChangeDeleteErrorMessage(TextArea textArea, Label errorLabel) {
        textArea.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if ((oldValue != null && !oldValue.equals(newValue)) || (oldValue == null && newValue != null))
                errorLabel.setText("");
        });
    }
}
