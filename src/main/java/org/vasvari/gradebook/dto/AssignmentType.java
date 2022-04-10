package org.vasvari.gradebook.dto;

import java.util.Arrays;

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

    public static AssignmentType getAssignmentTypeByLocalizedName(String localizedName) {
        return Arrays.stream(AssignmentType.values())
                .filter(t -> t.getLocalizedName().equals(localizedName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not find AssignmentType with localizedName " + localizedName));
    }
}
