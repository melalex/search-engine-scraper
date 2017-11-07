package com.zephyr.scraper.domain.exceptions;

import com.zephyr.scraper.domain.RequestContext;
import lombok.Getter;

public class RequestException extends RuntimeException {
    private static final long serialVersionUID = 4366283960416778665L;

    @Getter
    private RequestContext failedContext;

    public RequestException(String message, RequestContext failedContext) {
        super(message);
        this.failedContext = failedContext;
    }

    public RequestException(String message, Throwable cause, RequestContext failedContext) {
        super(message, cause);
        this.failedContext = failedContext;
    }

    public RequestException(Throwable cause, RequestContext failedContext) {
        super(cause);
        this.failedContext = failedContext;
    }
}
