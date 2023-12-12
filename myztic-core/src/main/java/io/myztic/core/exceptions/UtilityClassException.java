package io.myztic.core.exceptions;

public class UtilityClassException extends IllegalStateException {
    public UtilityClassException() {
        super("Cannot be instantiated as this is a utility class");
    }
}
