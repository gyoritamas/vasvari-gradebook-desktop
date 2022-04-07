package org.vasvari.gradebook.dto;

public enum UserRole {
    ADMIN("rendszergazda"), TEACHER("tanár"), STUDENT("tanuló");

    private final String localizedName;

    UserRole(String localizedName) {
        this.localizedName = localizedName;
    }

    public String getLocalizedName() {
        return localizedName;
    }
}
