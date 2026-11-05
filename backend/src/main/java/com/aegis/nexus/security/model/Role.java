package com.aegis.nexus.security.model;

public enum Role {
    SUPER_ADMIN("ROLE_SUPER_ADMIN"),
    ADMIN("ROLE_ADMIN"),
    ANALYST("ROLE_ANALYST"),
    VIEWER("ROLE_VIEWER");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
