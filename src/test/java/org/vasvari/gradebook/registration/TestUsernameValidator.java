package org.vasvari.gradebook.registration;

import org.junit.jupiter.api.Test;
import org.vasvari.gradebook.helpers.UsernameValidator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUsernameValidator {
    @Test
    public void TestValidUsername(){
        UsernameValidator validator = new UsernameValidator();

        String username = "johndoe68";

        assertTrue(validator.test(username));
    }

    @Test
    public void TestShortUsername(){
        UsernameValidator validator = new UsernameValidator();

        String username = "john";

        assertFalse(validator.test(username));
    }

    @Test
    public void TestMinimumLengthUsername(){
        UsernameValidator validator = new UsernameValidator();

        String username = "johndoeX";

        assertTrue(validator.test(username));
    }

    @Test
    public void TestUsernameWithInvalidCharacters(){
        UsernameValidator validator = new UsernameValidator();

        String username = "john_doe";

        assertFalse(validator.test(username));
    }

    @Test
    public void TestUsernameWithNoLetters(){
        UsernameValidator validator = new UsernameValidator();

        String username = "12345678";

        assertFalse(validator.test(username));
    }
}
