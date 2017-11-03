package com.zephyr.scraper.domain.exceptions;

public class BrowserException extends RuntimeException {
    private static final long serialVersionUID = -1105022205791605357L;

    public BrowserException(String message) {
        super(message);
    }

    public BrowserException(String message, Throwable cause) {
        super(message, cause);
    }

    public BrowserException(Throwable cause) {
        super(cause);
    }

    protected BrowserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
