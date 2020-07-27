package com.github.sitture.unirestcurl;

import kong.unirest.HttpRequest;
import kong.unirest.Unirest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CurlTransformerTest {

    private static final String TEST_URL = "https://localhost/test";

    @Test
    public void canTransformGetRequestWithNoHeaders() {
        final HttpRequest<?> request = Unirest.get(TEST_URL);
        final String generatedCurl = new CurlTransformer(request).transform();
        final String expectedCurl = String.format("curl --verbose --url \"%s\"", TEST_URL);
        assertEquals(expectedCurl, generatedCurl, "Unexpected curl request generated.");
    }

    @Test
    public void canTransformGetRequestWithHeaders() {
        final HttpRequest<?> request = Unirest.get(TEST_URL)
                .header("Accept", "application/json")
                .basicAuth("username", "password");
        final String generatedCurl = new CurlTransformer(request).transform();
        final String expectedCurl = String.format("curl --verbose --url \"%s\" --header \"Accept:application/json\" --header \"Authorization:Basic dXNlcm5hbWU6cGFzc3dvcmQ=\"", TEST_URL);
        assertEquals(expectedCurl, generatedCurl, "Unexpected curl request generated.");
    }

    @Test
    public void canTransformPostRequestWithHeaders() {
        final HttpRequest<?> request = Unirest.post(TEST_URL)
                .header("Accept", "application/json");
        final String generatedCurl = new CurlTransformer(request).transform();
        final String expectedCurl = String.format("curl --verbose --request POST --url \"%s\" --header \"Accept:application/json\"", TEST_URL);
        assertEquals(expectedCurl, generatedCurl, "Unexpected curl request generated.");
    }

    @Test
    public void canTransformPostRequestWithHeadersAndStringBody() {
        final HttpRequest<?> request = Unirest.post(TEST_URL)
                .header("Accept", "application/xml")
                .body("{\"test\": \"body\"}");
        final String generatedCurl = new CurlTransformer(request).transform();
        final String expectedCurl = String.format("curl --verbose --request POST --url \"%s\" --header \"Accept:application/xml\" --data '{\"test\": \"body\"}'", TEST_URL);
        assertEquals(expectedCurl, generatedCurl, "Unexpected curl request generated.");
    }

    @Test
    public void canTransformPostRequestWithHeadersAndFieldsBodyMap() {
        final Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("key1", "value");
        bodyMap.put("key2", 2);
        final HttpRequest<?> request = Unirest.post(TEST_URL)
                .header("Accept", "application/xml")
                .fields(bodyMap);
        final String generatedCurl = new CurlTransformer(request).transform();
        final String expectedCurl = String.format("curl --verbose --request POST --url \"%s\" --header \"Accept:application/xml\" --data 'key1=value&key2=2'", TEST_URL);
        assertEquals(expectedCurl, generatedCurl, "Unexpected curl request generated.");
    }

    @Test
    public void canTransformPostRequestWithHeadersAndSingleFields() {
        final HttpRequest<?> request = Unirest.post(TEST_URL)
                .header("Accept", "application/xml")
                .field("key1", "value")
                .field("key2", "value2");
        final String generatedCurl = new CurlTransformer(request).transform();
        final String expectedCurl = String.format("curl --verbose --request POST --url \"%s\" --header \"Accept:application/xml\" --data 'key1=value&key2=value2'", TEST_URL);
        assertEquals(expectedCurl, generatedCurl, "Unexpected curl request generated.");
    }

}
