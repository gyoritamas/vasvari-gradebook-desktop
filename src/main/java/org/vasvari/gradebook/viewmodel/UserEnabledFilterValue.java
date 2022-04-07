package org.vasvari.gradebook.viewmodel;

public enum UserEnabledFilterValue {
    BOTH("aktív és inaktív"), ENABLED("aktív"), DISABLED("inaktív");

    private final String localisedName;

    UserEnabledFilterValue(String localisedName) {
        this.localisedName = localisedName;
    }

    @Override
    public String toString() {
        return localisedName;
    }
}
