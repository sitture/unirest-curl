package com.github.sitture.unirestcurl;

import kong.unirest.Config;
import kong.unirest.HttpRequest;
import kong.unirest.Interceptor;

public class CurlInterceptor implements Interceptor {

    private final RequestLogger requestLogger;

    public CurlInterceptor(final RequestLogger requestLogger) {
        this.requestLogger = requestLogger;
    }

    @Override
    public void onRequest(final HttpRequest<?> request, final Config config) {
        final String generatedCurl = new CurlBuilder(request).build();
        requestLogger.log(generatedCurl);
    }
}
