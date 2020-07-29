package com.github.sitture.unirestcurl;

import kong.unirest.Config;
import kong.unirest.HttpRequest;
import kong.unirest.Interceptor;

public class CurlInterceptor implements Interceptor {

    private final transient RequestLogger requestLogger;

    /**
     * Interceptor for capturing requests as curls.
     * @param requestLogger to use for logging
     */
    public CurlInterceptor(final RequestLogger requestLogger) {
        this.requestLogger = requestLogger;
    }

    @Override
    public void onRequest(final HttpRequest<?> request, final Config config) {
        final String generatedCurl = new CurlBuilder(request).build();
        requestLogger.log(generatedCurl);
    }
}
