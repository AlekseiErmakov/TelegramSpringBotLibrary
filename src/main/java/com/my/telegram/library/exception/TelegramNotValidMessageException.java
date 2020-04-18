package com.my.telegram.library.exception;

public class TelegramNotValidMessageException extends RuntimeException{
    public TelegramNotValidMessageException() {
    }

    public TelegramNotValidMessageException(String message) {
        super(message);
    }

    public TelegramNotValidMessageException(String message, Throwable cause) {
        super(message, cause);
    }
}
