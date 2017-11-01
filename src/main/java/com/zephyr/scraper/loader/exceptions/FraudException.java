package com.zephyr.scraper.loader.exceptions;

public class FraudException extends RuntimeException {
    private static final long serialVersionUID = -7579427673806004192L;

    public FraudException(String message) {
        super(message);
    }

    public FraudException(String message, Throwable cause) {
        super(message, cause);
    }

    public FraudException(Throwable cause) {
        super(cause);
    }

    protected FraudException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
