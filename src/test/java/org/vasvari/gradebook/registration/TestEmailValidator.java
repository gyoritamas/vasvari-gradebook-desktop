package org.vasvari.gradebook.registration;

import org.junit.jupiter.api.Test;
import org.vasvari.gradebook.helpers.EmailValidator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestEmailValidator {
    @Test
    public void TestValidEmail() {
        EmailValidator validator = new EmailValidator();

        boolean isValid = validator.test("tomoichi@asifboot.com");

        assertTrue(isValid);
    }

    @Test
    public void TestEmailWithNoUser() {
        EmailValidator validator = new EmailValidator();

        boolean isValid = validator.test("@asifboot.com");

        assertFalse(isValid);
    }

    @Test
    public void TestEmailWithNoDomain() {
        EmailValidator validator = new EmailValidator();

        boolean isValid = validator.test("tomoichi@mailboxvip.");

        assertFalse(isValid);
    }

    @Test
    public void TestEmailWithShortDomain() {
        EmailValidator validator = new EmailValidator();

        boolean isValid = validator.test("tomoichi@mailboxvip.i");

        assertFalse(isValid);
    }

    @Test
    public void TestEmailWithInvalidCharacters() {
        EmailValidator validator = new EmailValidator();

        boolean isValid = validator.test("géza@citromail.hu");

        assertFalse(isValid);
    }
}
