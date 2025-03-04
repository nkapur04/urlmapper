package com.sample.url.utility.mapper.config;

import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomRetryListener implements RetryListener {

    private static final Logger log = LoggerFactory.getLogger(CustomRetryListener.class);

    @Override
    public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
        // Called before the first attempt
        log.debug("open retry");
        return true;
    }

    @Override
    public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        // Called after the last attempt
        log.debug("close retry");
    }

    @Override
    public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        // Called after every failed attempt
        log.debug("Retry attempt #{}", context.getRetryCount());
    }
}

