package org.vasvari.gradebook.helpers;

import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.function.Predicate;
import java.util.regex.Pattern;

@Service
public class EmailValidator implements Predicate<String> {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$");

    @Override
    public boolean test(String s) {
        return EMAIL_PATTERN.matcher(s.toUpperCase(Locale.ROOT)).matches();
    }
}
