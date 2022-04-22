package org.vasvari.gradebook.util;

import javafx.scene.control.*;
import org.springframework.stereotype.Component;
import org.vasvari.gradebook.dto.dataTypes.SimpleData;
import org.vasvari.gradebook.dto.dataTypes.SimpleTeacher;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class Validator {
    private static final int FIRSTNAME_MIN_LENGTH = 2;
    private static final int FIRSTNAME_MAX_LENGTH = 255;
    private static final int LASTNAME_MIN_LENGTH = 2;
    private static final int LASTNAME_MAX_LENGTH = 255;
    private static final int SUBJECT_NAME_MIN_LENGTH = 4;
    private static final int SUBJECT_NAME_MAX_LENGTH = 255;
    private static final int ASSIGNMENT_TITLE_MIN_LENGTH = 4;
    private static final int ASSIGNMENT_TITLE_MAX_LENGTH = 255;
    private static final int USERNAME_MIN_LENGTH = 4;
    private static final int USERNAME_MAX_LENGTH = 20;
    private static final int PASSWORD_MIN_LENGTH = 8;
    private static final int PASSWORD_MAX_LENGTH = 20;

    public void lastname(TextField lastname, Label lastnameErrorLabel) {
        lastnameErrorLabel.setText("");
        if (lastname.getText() == null || lastname.getText().isBlank() || lastname.getText().isEmpty())
            lastnameErrorLabel.setText("a vezetéknév nem lehet üres");
        else if (!lastname.getText().matches("[\\p{L}\\s.-]+"))
            lastnameErrorLabel.setText("érvénytelen karakter");
        else if (!lastname.getText().matches("(\\p{L}){2}([\\p{L}\\s.-])*"))
            lastnameErrorLabel.setText("a vezetéknév formátuma hibás");
        else if (lastname.getText().length() < LASTNAME_MIN_LENGTH || lastname.getText().length() > LASTNAME_MAX_LENGTH)
            lastnameErrorLabel.setText(String.format("adjon meg %d-%d karaktert", LASTNAME_MIN_LENGTH, LASTNAME_MAX_LENGTH));
    }

    public void firstname(TextField firstname, Label firstnameErrorLabel) {
        firstnameErrorLabel.setText("");
        if (firstname.getText() == null || firstname.getText().isBlank() || firstname.getText().isEmpty())
            firstnameErrorLabel.setText("a keresztnév nem lehet üres");
        else if (!firstname.getText().matches("[\\p{L}\\s.-]+"))
            firstnameErrorLabel.setText("érvénytelen karakter");
        else if (!firstname.getText().matches("(\\p{L}){2}([\\p{L}\\s.-])*"))
            firstnameErrorLabel.setText("a keresztnév formátuma hibás");
        else if (firstname.getText().length() < FIRSTNAME_MIN_LENGTH || firstname.getText().length() > FIRSTNAME_MAX_LENGTH)
            firstnameErrorLabel.setText(String.format("adjon meg %d-%d karaktert", FIRSTNAME_MIN_LENGTH, FIRSTNAME_MAX_LENGTH));
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
        else if (subjectName.getText().length() < SUBJECT_NAME_MIN_LENGTH || subjectName.getText().length() > SUBJECT_NAME_MAX_LENGTH)
            subjectNameErrorLabel.setText(String.format("adjon meg %d-%d karaktert", SUBJECT_NAME_MIN_LENGTH, SUBJECT_NAME_MAX_LENGTH));
    }

    public void subjectTeacher(ComboBox<SimpleTeacher> subjectTeacher, Label subjectTeacherErrorLabel) {
        subjectTeacherErrorLabel.setText("");
        if (subjectTeacher.selectionModelProperty().getValue().getSelectedItem() == null)
            subjectTeacherErrorLabel.setText("nincs tanár kiválasztva");
    }

    public void assignmentSubject(ComboBox<SimpleData> assignmentSubject, Label assignmentSubjectErrorLabel) {
        assignmentSubjectErrorLabel.setText("");
        if (assignmentSubject.selectionModelProperty().getValue().getSelectedItem() == null)
            assignmentSubjectErrorLabel.setText("nincs tantárgy kiválasztva");
    }

    public void assignmentTitle(TextField assignmentTitle, Label titleErrorLabel) {
        titleErrorLabel.setText("");
        if (assignmentTitle.getText() == null || assignmentTitle.getText().isEmpty() || assignmentTitle.getText().isBlank())
            titleErrorLabel.setText("a feladat címe nem lehet üres");
        else if (assignmentTitle.getText().length() < ASSIGNMENT_TITLE_MIN_LENGTH || assignmentTitle.getText().length() > ASSIGNMENT_TITLE_MAX_LENGTH)
            titleErrorLabel.setText(String.format("adjon meg %d-%d karaktert", ASSIGNMENT_TITLE_MIN_LENGTH, ASSIGNMENT_TITLE_MAX_LENGTH));
    }

    public void assignmentType(ComboBox<String> assignmentType, Label assignmentTypeErrorLabel) {
        assignmentTypeErrorLabel.setText("");
        if (assignmentType.selectionModelProperty().getValue().getSelectedItem() == null)
            assignmentTypeErrorLabel.setText("nincs típus kiválasztva");
    }

    public void deadline(DatePicker deadline, Label deadlineErrorLabel) {
        deadlineErrorLabel.setText("");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy. MM. dd.");
        String deadlineText = deadline.getEditor().textProperty().getValue();

        if (deadline.getEditor().textProperty() == null || deadlineText.isEmpty() || deadlineText.isBlank())
            deadlineErrorLabel.setText("a határidő nem lehet üres");
        else if (!deadlineText.matches("([12]\\d{3}\\. (0[1-9]|1[0-2])\\. (0[1-9]|[12]\\d|3[01]))\\."))
            deadlineErrorLabel.setText("a dátum formátuma hibás");
        else if (!LocalDate.parse(deadlineText, dateTimeFormatter).isAfter(LocalDate.now()))
            deadlineErrorLabel.setText("csak jövőbeli dátum adható meg");
    }

    public void username(TextField username, Label usernameErrorLabel) {
        usernameErrorLabel.setText("");
        if (username.getText() == null || username.getText().isEmpty() || username.getText().isBlank())
            usernameErrorLabel.setText("a felhasználónév nem lehet üres");
        else if (!username.getText().matches("^[a-zA-Z](.)+"))
            usernameErrorLabel.setText("az első karakter csak betű lehet");
        else if (!username.getText().matches("^[a-zA-Z]([0-9a-zA-Z])+"))
            usernameErrorLabel.setText("csak betűket és számokat tartalmazhat");
        else if (username.getText().length() < USERNAME_MIN_LENGTH || username.getText().length() > USERNAME_MAX_LENGTH)
            usernameErrorLabel.setText(String.format("adjon meg %d-%d karaktert", USERNAME_MIN_LENGTH, USERNAME_MAX_LENGTH));
    }

    public void password(TextField password, Label passwordErrorLabel) {
        passwordErrorLabel.setText("");
        if (password.getText() == null || password.getText().isEmpty() || password.getText().isBlank())
            passwordErrorLabel.setText("a jelszó mező nem lehet üres");
        else if (password.getText().length() < PASSWORD_MIN_LENGTH || password.getText().length() > PASSWORD_MAX_LENGTH)
            passwordErrorLabel.setText(String.format("adjon meg %d-%d karaktert", PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH));
        else if (!password.getText().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}$"))
            passwordErrorLabel.setText("a jelszónak kisbetűt, nagybetűt és számot is kell tartalmaznia");
    }

    public void passwordsMatch(TextField password, TextField passwordRepeat, Label passwordErrorLabel) {
        passwordErrorLabel.setText("");
        if (!password.getText().equals(passwordRepeat.getText()))
            passwordErrorLabel.setText("a jelszavak nem egyeznek");
    }
}
