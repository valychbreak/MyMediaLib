package com.valychbreak.mymedialib.exception;

public class CatalogNotFoundException extends MyMediaLibException {
    public CatalogNotFoundException() {
    }

    public CatalogNotFoundException(String message) {
        super(message);
    }

    public CatalogNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CatalogNotFoundException(Throwable cause) {
        super(cause);
    }

    public CatalogNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
