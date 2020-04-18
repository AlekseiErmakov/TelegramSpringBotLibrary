package com.my.telegram.library.exception;

public class TelegramBotMethodException extends RuntimeException{
    public TelegramBotMethodException() {
    }

    public TelegramBotMethodException(String message) {
        super(message);
    }

    public TelegramBotMethodException(String message, Throwable cause) {
        super(message, cause);
    }
}
