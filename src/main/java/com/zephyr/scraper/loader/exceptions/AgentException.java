package com.zephyr.scraper.loader.exceptions;

public class AgentException extends RuntimeException {
    private static final long serialVersionUID = -1105022205791605357L;

    public AgentException(String message) {
        super(message);
    }

    public AgentException(String message, Throwable cause) {
        super(message, cause);
    }

    public AgentException(Throwable cause) {
        super(cause);
    }

    protected AgentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
