package org.vasvari.gradebook.util;

import javafx.scene.control.Label;
import org.springframework.stereotype.Component;

@Component
public class InternalServerErrorHandler {
    public void printErrorToLabel(Exception exception, Label errorLabel) {
        String errorMessage = exception.getMessage();
        try {
            String details = errorMessage.substring(errorMessage.indexOf("[") + 1, errorMessage.indexOf("]"));
            Problem problem = Problem.fromJson(details);
            String localizedMessage = getLocalizedMessage(problem);
            errorLabel.setText(localizedMessage);
        } catch (Exception e) {
            errorLabel.setText(errorMessage);
        }
    }

    private static String getLocalizedMessage(Problem problem) {
        switch (problem.getTitle()) {
            case "Assignment in use":
                return "A feladat használatban van";
            case "Duplicate entry":
                return "A feladat már osztályozva van";
            case "Invalid credentials":
                return "Hibás felhasználónév vagy jelszó";
            case "Student in use":
                return "A tanuló használatban van";
            case "Subject in use":
                return "A tantárgy használatban van";
            case "Given password is incorrect":
                return "A megadott jelszó hibás";
            case "Username taken":
                return "A felhasználónév foglalt";
            case "Already has an account":
                return "A felhasználói fiók már létezik";
            default:
                return problem.getTitle();
        }
    }
}
