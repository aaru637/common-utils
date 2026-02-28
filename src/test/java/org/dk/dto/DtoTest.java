package org.dk.dto;

import org.dk.JsonUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the DTO classes: ApiResponse, MetaInfo, and MetaResponse.
 *
 * @author Dhineshkumar Dhandapani
 */
class DtoTest {

    @Test
    void testMetaInfo() {
        MetaInfo metaInfo = new MetaInfo("200", "Success");
        assertEquals("200", metaInfo.getCode());
        assertEquals("Success", metaInfo.getMessage());

        MetaInfo empty = new MetaInfo();
        assertNull(empty.getCode());
        assertNull(empty.getMessage());
    }

    @Test
    void testMetaResponseAdd() {
        MetaResponse metaResponse = new MetaResponse();
        assertTrue(metaResponse.isSuccess());
        assertNull(metaResponse.getMetaInfos());

        MetaInfo info = new MetaInfo("INF001", "Info Message");
        metaResponse.add(info, false);

        assertTrue(metaResponse.isSuccess());
        assertEquals(1, metaResponse.getMetaInfos().size());
        assertEquals(info, metaResponse.getMetaInfos().get(0));

        MetaInfo error = new MetaInfo("ERR001", "Error Message");
        metaResponse.add(error, true);

        assertFalse(metaResponse.getMetaInfos().isEmpty());
        assertFalse(metaResponse.isSuccess());
        assertEquals(2, metaResponse.getMetaInfos().size());
        assertEquals(error, metaResponse.getMetaInfos().get(1));

        // Adding an info message should NOT reset isSuccess to true
        metaResponse.add(new MetaInfo("INF002", "Another Info"), false);
        assertFalse(metaResponse.isSuccess(), "Success status should remain false after an error was added");
        assertEquals(3, metaResponse.getMetaInfos().size());
    }

    @Test
    void testMetaResponseMerge() {
        MetaResponse m1 = new MetaResponse();
        m1.add(new MetaInfo("M1", "Msg1"), false);

        MetaResponse m2 = new MetaResponse();
        m2.add(new MetaInfo("E1", "Err1"), true);

        m1.add(m2);

        assertFalse(m1.isSuccess());
        assertEquals(2, m1.getMetaInfos().size());
    }

    @Test
    void testApiResponse() {
        ApiResponse response = new ApiResponse();
        assertEquals(400, response.getStatus()); // Default value
        assertNull(response.getData());
        assertNull(response.getMetaResponse());

        response.setStatus(200);
        response.setData("Test Data");
        MetaResponse meta = new MetaResponse();
        meta.add(new MetaInfo("200", "OK"), false);
        response.setMetaResponse(meta);

        assertEquals(200, response.getStatus());
        assertEquals("Test Data", response.getData());
        assertEquals(meta, response.getMetaResponse());
    }

    @Test
    void testSerialization() {
        ApiResponse response = new ApiResponse();
        response.setStatus(200);
        response.setData("Serialized Data");
        MetaResponse meta = new MetaResponse();
        meta.add(new MetaInfo("S1", "Success"), false);
        response.setMetaResponse(meta);

        String json = JsonUtils.toJson(response);
        assertNotNull(json);

        ApiResponse deserialized = JsonUtils.fromJson(json, ApiResponse.class);
        assertNotNull(deserialized);
        assertEquals(200.0, ((Number) deserialized.getStatus()).doubleValue()); // Gson might deserialize as Double
        assertEquals("Serialized Data", deserialized.getData());
    }
}
