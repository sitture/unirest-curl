package com.github.sitture.unirestcurl;

import kong.unirest.Header;
import kong.unirest.HttpMethod;
import kong.unirest.HttpRequest;
import kong.unirest.ParamPart;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CurlTransformer {

    private static final String CURL_PREFIX = "curl --verbose";
    private static final String REQUEST_METHOD = "--request %s";
    private static final String REQUEST_URL = "--url \"%s\"";
    private static final String REQUEST_HEADER = "--header \"%s:%s\"";
    private static final String REQUEST_BODY = "--data '%s'";
    private final transient HttpRequest<?> request;

    public CurlTransformer(final HttpRequest<?> request) {
        this.request = request;
    }

    public String transform() {
        final List<String> curlItems = new ArrayList<>();
        curlItems.add(CURL_PREFIX);
        curlItems.add(getHttpMethod());
        curlItems.add(getUrl());
        curlItems.add(getHeaders());
        curlItems.add(getBody());
        return getProcessedCurl(curlItems);
    }

    private String getProcessedCurl(final List<String> curlItems) {
        final StringBuilder curlString = new StringBuilder();
        for (final String curlItem : curlItems) {
            if (!curlItem.isEmpty() && curlItem.length() > 0) {
                curlString.append(curlItem);
                curlString.append(' ');
            }
        }
        return curlString.toString().trim();
    }


    private String getHttpMethod() {
        return request.getHttpMethod() == HttpMethod.GET ? "" : String.format(REQUEST_METHOD, request.getHttpMethod());
    }

    private String getUrl() {
        return String.format(REQUEST_URL, request.getUrl());
    }

    private String getHeaders() {
        final StringBuilder headersString = new StringBuilder();
        for (final Header header : request.getHeaders().all()) {
            headersString.append(String.format(REQUEST_HEADER, header.getName(), header.getValue()));
            headersString.append(' ');
        }
        return headersString.toString().trim();
    }

    private String getBody() {
        String processedBody = "";
        if (request.getBody().isPresent()) {
            if (request.getBody().get().multiParts().size() == 0) {
                processedBody = String.format(REQUEST_BODY, request.getBody().get().uniPart().getValue());
            } else {
                final String fields = request.getBody().get().multiParts().stream()
                        .map(bodyPart -> {
                            if (bodyPart instanceof ParamPart) {
                                return String.format("%s=%s", bodyPart.getName(), bodyPart.getValue());
                            }
                            return "";
                        })
                        .collect(Collectors.joining("&"));
                processedBody = String.format(REQUEST_BODY, fields);
            }
        }
        return processedBody;
    }

}
