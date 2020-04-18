package com.my.telegram.library.exception;

public class TelegramScriptException extends RuntimeException {
    public TelegramScriptException() {
    }

    public TelegramScriptException(String message) {
        super(message);
    }

    public TelegramScriptException(String message, Throwable cause) {
        super(message, cause);
    }
}
