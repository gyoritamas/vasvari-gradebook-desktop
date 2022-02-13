package org.vasvari.gradebook.registration;

import org.junit.jupiter.api.Test;
import org.vasvari.gradebook.helpers.PasswordValidator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPasswordValidator {
    @Test
    public void TestValidPassword(){
        PasswordValidator validator = new PasswordValidator();

        String password = "validPassword1234";

        assertTrue(validator.test(password));
    }

    @Test
    public void TestPasswordWithNoNumbers(){
        PasswordValidator validator = new PasswordValidator();

        String password = "passwordWithNoNumbersInIt";

        assertFalse(validator.test(password));
    }

    @Test
    public void TestPasswordTooShort(){
        PasswordValidator validator = new PasswordValidator();

        String password = "pwShort";

        assertFalse(validator.test(password));
    }

    @Test
    public void TestPasswordNoUppercaseLetters(){
        PasswordValidator validator = new PasswordValidator();

        String password = "passwordwithonlylowercaseletters";

        assertFalse(validator.test(password));
    }

    @Test
    public void TestPasswordWithSpecialCharacters(){
        PasswordValidator validator = new PasswordValidator();

        String password = "p4ssw0rdW1thSp3c14lCh4r4ct3rs";

        assertTrue(validator.test(password));
    }
}
