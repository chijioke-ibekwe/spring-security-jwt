package dev.chijiokeibekwe.jwtsecurity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ResponseStatus {

    SUCCESSFUL("Successful"),

    FAILED("Failed");

    private final String value;

    ResponseStatus(String value)
    {
        this.value = value;
    }

    @JsonValue
    public String getValue()
    {
        return value;
    }
}
