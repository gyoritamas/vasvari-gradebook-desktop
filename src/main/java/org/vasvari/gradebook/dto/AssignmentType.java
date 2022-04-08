package org.vasvari.gradebook.dto;

public enum AssignmentType {
    TEST("dolgozat"),
    HOMEWORK("házi feladat"),
    PROJECT("projektfeladat"),
    QUIZ("röpdolgozat");

    private final String localizedName;

    AssignmentType(String localizedName) {
        this.localizedName = localizedName;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    @Override
    public String toString() {
        return localizedName;
    }
}
