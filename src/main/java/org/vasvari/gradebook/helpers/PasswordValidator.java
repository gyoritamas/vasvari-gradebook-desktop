package org.vasvari.gradebook.helpers;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Pattern;

@Service
public class PasswordValidator implements Predicate<String> {

    // at least 8 characters long with at least one lowercase, one uppercase and one number
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])[a-zA-Z0-9]{8,}$");

    @Override
    public boolean test(String s) {
        return PASSWORD_PATTERN.matcher(s).matches();
    }
}
