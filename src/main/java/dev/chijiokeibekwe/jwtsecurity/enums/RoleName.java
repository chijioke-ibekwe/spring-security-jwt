package dev.chijiokeibekwe.jwtsecurity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RoleName {
    ROLE_CUSTOMER ("role_customer"), ROLE_ADMIN ("role_admin");

    private final String value;

    RoleName(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
