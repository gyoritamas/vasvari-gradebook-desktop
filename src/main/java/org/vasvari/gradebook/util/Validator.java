package org.vasvari.gradebook.util;

import javafx.scene.control.*;
import org.springframework.stereotype.Component;
import org.vasvari.gradebook.dto.dataTypes.SimpleTeacher;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class Validator {
    public void lastname(TextField lastname, Label lastnameErrorLabel) {
        lastnameErrorLabel.setText("");
        if (lastname.getText() == null || lastname.getText().isBlank() || lastname.getText().isEmpty())
            lastnameErrorLabel.setText("a vezetéknév nem lehet üres");
        else if (!lastname.getText().matches("[\\p{L}\\s.-]+"))
            lastnameErrorLabel.setText("érvénytelen karakter");
        else if (!lastname.getText().matches("(\\p{L}){2}([\\p{L}\\s.-])*"))
            lastnameErrorLabel.setText("a vezetéknév formátuma hibás");
        else if (lastname.getText().length() < 2 || lastname.getText().length() > 255)
            lastnameErrorLabel.setText("adjon meg 2-255 karaktert");
    }

    public void firstname(TextField firstname, Label firstnameErrorLabel) {
        firstnameErrorLabel.setText("");
        if (firstname.getText() == null || firstname.getText().isBlank() || firstname.getText().isEmpty())
            firstnameErrorLabel.setText("a keresztnév nem lehet üres");
        else if (!firstname.getText().matches("[\\p{L}\\s.-]+"))
            firstnameErrorLabel.setText("érvénytelen karakter");
        else if (!firstname.getText().matches("(\\p{L}){2}([\\p{L}\\s.-])*"))
            firstnameErrorLabel.setText("a keresztnév formátuma hibás");
        else if (firstname.getText().length() < 2 || firstname.getText().length() > 255)
            firstnameErrorLabel.setText("adjon meg 2-255 karaktert");
    }

    public void gradeLevel(ComboBox<String> gradeLevel, Label gradeLevelErrorLabel) {
        if (gradeLevel.selectionModelProperty().getValue().getSelectedItem() == null)
            gradeLevelErrorLabel.setText("az évfolyam nem lehet üres");
    }

    public void email(TextField email, Label emailErrorLabel) {
        emailErrorLabel.setText("");
        if (email.getText() == null || email.getText().isEmpty() || email.getText().isBlank())
            emailErrorLabel.setText("az email cím nem lehet üres");
        else if (!email.getText().matches("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}"))
            emailErrorLabel.setText("az email cím formátuma hibás");
    }

    public void address(TextArea address, Label addressErrorLabel) {
        addressErrorLabel.setText("");
        if (address.getText() == null || address.getText().isEmpty() || address.getText().isBlank())
            addressErrorLabel.setText("az lakcím mező nem lehet üres");
    }

    public void phone(TextField phone, Label phoneErrorLabel) {
        phoneErrorLabel.setText("");
        if (phone.getText() == null || phone.getText().isEmpty() || phone.getText().isBlank())
            phoneErrorLabel.setText("a telefonszám nem lehet üres");
        else if (!phone.getText().matches("^\\+?[\\d \\-()]{7,}"))
            phoneErrorLabel.setText("a telefonszám formátuma hibás");
    }

    public void birthdate(DatePicker birthdate, Label birthdateErrorLabel) {
        birthdateErrorLabel.setText("");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy. MM. dd.");
        String birthdateText = birthdate.getEditor().textProperty().getValue();

        if (birthdate.getEditor().textProperty() == null || birthdateText.isEmpty() || birthdateText.isBlank())
            birthdateErrorLabel.setText("a szül. dátum nem lehet üres");
        else if (!birthdateText.matches("([12]\\d{3}\\. (0[1-9]|1[0-2])\\. (0[1-9]|[12]\\d|3[01]))\\."))
            birthdateErrorLabel.setText("a szül. dátum formátuma hibás");
        else if (LocalDate.parse(birthdateText, dateTimeFormatter).isAfter(LocalDate.now()))
            birthdateErrorLabel.setText("nem adható meg jövőbeli dátum");
    }

    public void subjectName(TextField subjectName, Label subjectNameErrorLabel) {
        subjectNameErrorLabel.setText("");
        if (subjectName.getText() == null || subjectName.getText().isEmpty() || subjectName.getText().isBlank())
            subjectNameErrorLabel.setText("a tantárgy neve nem lehet üres");
        else if (subjectName.getText().length() < 2 || subjectName.getText().length() > 255)
            subjectNameErrorLabel.setText("adjon meg 2-255 karaktert");
    }

    public void subjectTeacher(ComboBox<SimpleTeacher> subjectTeacher, Label subjectTeacherErrorLabel) {
        subjectTeacherErrorLabel.setText("");
        if (subjectTeacher.selectionModelProperty().getValue() == null)
            subjectTeacherErrorLabel.setText("nincs tanár kiválasztva");
    }
}
