package dev.chijiokeibekwe.jwtsecurity.common;

import dev.chijiokeibekwe.jwtsecurity.enums.ResponseStatus;

public record ResponseObject<T> (ResponseStatus status, String message, T data)
{
    //
}
