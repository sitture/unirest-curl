package com.github.sitture.unirestcurl;

import kong.unirest.HttpRequest;

public class CurlBuilder {

    private static final String CURL_PREFIX = "curl";

    public CurlBuilder(final HttpRequest<?> request) {
        request.getHeaders();
    }

    public String build() {
        return CURL_PREFIX;
    }

}
