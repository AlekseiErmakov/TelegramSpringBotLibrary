package com.my.telegram.library.exception;

public class CommandUnsupportedException extends RuntimeException {
    public CommandUnsupportedException() {
    }

    public CommandUnsupportedException(String message) {
        super(message);
    }

    public CommandUnsupportedException(String message, Throwable cause) {
        super(message, cause);
    }
}
