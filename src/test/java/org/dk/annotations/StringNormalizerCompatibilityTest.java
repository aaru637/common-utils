package org.dk.annotations;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringNormalizerCompatibilityTest {

    @Test
    void normalizesStringsWithJackson2() throws Exception {
        com.fasterxml.jackson.databind.ObjectMapper mapper = com.fasterxml.jackson.databind.json.JsonMapper.builder().build();

        SampleRequest request = mapper.readValue("{\"name\":\"   \",\"identity\":\"  kg  \"}", SampleRequest.class);

        assertEquals("", request.name);
        assertEquals("kg", request.identity);
    }

    @Test
    void normalizesStringsWithJackson3() throws Exception {
        tools.jackson.databind.json.JsonMapper mapper = tools.jackson.databind.json.JsonMapper.builder().build();

        SampleRequest request = mapper.readValue("{\"name\":\"   \",\"identity\":\"  kg  \"}", SampleRequest.class);

        assertEquals("", request.name);
        assertEquals("kg", request.identity);
    }

    static class SampleRequest {
        @StringNormalizer
        public String name;

        @StringNormalizer
        public String identity;
    }
}
