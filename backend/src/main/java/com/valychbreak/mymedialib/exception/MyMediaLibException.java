package com.valychbreak.mymedialib.exception;

public class MyMediaLibException extends Exception {
    public MyMediaLibException() {
    }

    public MyMediaLibException(String message) {
        super(message);
    }

    public MyMediaLibException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyMediaLibException(Throwable cause) {
        super(cause);
    }

    public MyMediaLibException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
