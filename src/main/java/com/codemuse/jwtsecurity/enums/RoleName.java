package com.codemuse.jwtsecurity.enums;

public enum RoleName {
    ROLE_CUSTOMER ("role_customer"), ROLE_ADMIN ("role_admin");

    private final String value;

    RoleName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
