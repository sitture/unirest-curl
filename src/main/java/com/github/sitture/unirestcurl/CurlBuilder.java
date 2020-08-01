package com.github.sitture.unirestcurl;

import kong.unirest.HttpMethod;
import kong.unirest.HttpRequest;
import kong.unirest.ParamPart;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class CurlBuilder {

    private static final String CURL_PREFIX = "curl --verbose";
    private static final String REQUEST_METHOD = "--request %s";
    private static final String REQUEST_URL = "--url \"%s\"";
    private static final String REQUEST_HEADER = "--header \"%s: %s\"";
    private static final String REQUEST_BODY = "--data '%s'";
    private static final String SPACE_DELIMITER = " ";
    private static final String EMPTY_STRING = "";
    private final transient HttpRequest<?> request;

    /**
     * Builds curl request based on the unirest request.
     * @param request object to transform
     */
    /* default */ CurlBuilder(final HttpRequest<?> request) {
        this.request = request;
    }

    /**
     * Builds curl request based.
     * @return transforms curl request log
     */
    public String build() {
        final List<String> curlItems = new ArrayList<>();
        curlItems.add(CURL_PREFIX);
        curlItems.add(getHttpMethod());
        curlItems.add(getUrl());
        curlItems.add(getHeaders());
        curlItems.add(getBody());
        return getProcessedCurl(curlItems);
    }

    private String getProcessedCurl(final List<String> curlItems) {
        return curlItems.stream()
                .filter(curlItem -> !curlItem.isEmpty())
                .collect(Collectors.joining(SPACE_DELIMITER));
    }

    private String getHttpMethod() {
        return request.getHttpMethod() == HttpMethod.GET ? EMPTY_STRING : String.format(REQUEST_METHOD, request.getHttpMethod());
    }

    private String getUrl() {
        return String.format(REQUEST_URL, request.getUrl());
    }

    private String getHeaders() {
        return request.getHeaders().all().stream()
                .map(header -> String.format(REQUEST_HEADER, header.getName(), header.getValue()))
                .collect(Collectors.joining(SPACE_DELIMITER));
    }

    private String getBody() {
        String processedBody = "";
        if (request.getBody().isPresent()) {
            if (request.getBody().get().multiParts().size() == 0) {
                processedBody = String.format(REQUEST_BODY, request.getBody().get().uniPart().getValue());
            } else {
                processedBody = request.getBody().get().multiParts().stream()
                        .filter(bodyPart -> bodyPart instanceof ParamPart)
                        .map(bodyPart -> String.format("--data '%s=%s'", bodyPart.getName(), bodyPart.getValue()))
                        .collect(Collectors.joining(SPACE_DELIMITER));
            }
        }
        return processedBody;
    }

}
