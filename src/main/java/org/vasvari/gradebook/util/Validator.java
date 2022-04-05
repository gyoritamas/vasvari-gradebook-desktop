package org.vasvari.gradebook.util;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@Component
public class Validator {
    public void lastName(TextField lastname, Label lastnameErrorLabel) {
        lastnameErrorLabel.setText("");
        if (lastname.getText().length() < 2)
            lastnameErrorLabel.setText("a vezetéknév legalább 2 karakter hosszú kell legyen");
        else if (!lastname.getText().matches("[a-zA-Z .-]{2,}"))
            lastnameErrorLabel.setText("a vezetéknév érvénytelen karaktert tartalmaz");
    }

    public void firstName(TextField firstname, Label firstnameErrorLabel) {
        firstnameErrorLabel.setText("");
        if (firstname.getText().length() < 2)
            firstnameErrorLabel.setText("a keresztnév legalább 2 karakter hosszú kell legyen");
        else if (!firstname.getText().matches("[a-zA-Z .-]{2,}"))
            firstnameErrorLabel.setText("a keresztnév érvénytelen karaktert tartalmaz");
    }

    public void email(TextField email, Label emailErrorLabel) {
        emailErrorLabel.setText("");
        if (email.getText().isEmpty() || email.getText().isBlank())
            emailErrorLabel.setText("az email cím nem lehet üres");
        else if (!email.getText().matches("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}"))
            emailErrorLabel.setText("az email cím formátuma hibás");
    }

    public void address(TextArea address, Label addressErrorLabel) {
        addressErrorLabel.setText("");
        if (address.getText().isEmpty() || address.getText().isBlank())
            addressErrorLabel.setText("az lakcím mező nem lehet üres");
    }

    public void phone(TextField phone, Label phoneErrorLabel) {
        phoneErrorLabel.setText("");
        if (phone.getText().isEmpty() || phone.getText().isBlank())
            phoneErrorLabel.setText("a telefonszám nem lehet üres");
        else if (!phone.getText().matches("^\\+?[\\d \\-()]{7,}"))
            phoneErrorLabel.setText("a telefonszám formátuma hibás");
    }

    public void birthdate(DatePicker birthdate, Label birthdateErrorLabel) {
        birthdateErrorLabel.setText("");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy. MM. dd.");
        String birthdateText = birthdate.getEditor().textProperty().getValue();

        if (birthdateText.isEmpty() || birthdateText.isBlank())
            birthdateErrorLabel.setText("a szül. dátum nem lehet üres");
        else if (!birthdateText.matches("([12]\\d{3}\\. (0[1-9]|1[0-2])\\. (0[1-9]|[12]\\d|3[01]))\\."))
            birthdateErrorLabel.setText("a szül. dátum formátuma hibás");
        else if (LocalDate.parse(birthdateText, dateTimeFormatter).isAfter(LocalDate.now()))
            birthdateErrorLabel.setText("nem adható meg jövőbeli dátum");
    }

}
