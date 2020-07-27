package com.github.sitture.unirestcurl;

import kong.unirest.HttpRequest;
import kong.unirest.Unirest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CurlBuilderTest {

    @Test
    public void canTransformASimpleGetRequest() {
        final HttpRequest<?> request = Unirest.get("https://localhost/get");
        final String generatedCurl = new CurlBuilder(request).build();
        assertEquals("curl", generatedCurl, "Invalid curl request.");
    }

}
