package com.my.telegram.library.exception;

public class TelegramControllerException extends RuntimeException {

    public TelegramControllerException() {
    }

    public TelegramControllerException(String message) {
        super(message);
    }

    public TelegramControllerException(String message, Throwable cause) {
        super(message, cause);
    }
}
