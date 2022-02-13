package org.vasvari.gradebook.helpers;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Pattern;

@Service
public class UsernameValidator implements Predicate<String> {

    // at least 5 characters long with lowercase, uppercase letters and numbers
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^(?=.*?[a-zA-Z])+[a-zA-Z0-9]{5,}$");

    @Override
    public boolean test(String s) {
        return USERNAME_PATTERN.matcher(s).matches();
    }
}
