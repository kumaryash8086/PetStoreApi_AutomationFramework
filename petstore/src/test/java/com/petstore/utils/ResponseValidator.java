package com.petstore.utils;

import com.petstore.config.ConfigManager;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import java.util.List;

public class ResponseValidator {

    private static final Logger log = LoggerFactory.getLogger(ResponseValidator.class);
    private static final ConfigManager config = ConfigManager.getInstance();

    private ResponseValidator() {}

    public static void assertStatusCode(Response response, int expected) {
        log.info("Asserting status code: expected={}, actual={}", expected, response.statusCode());
        Assert.assertEquals(response.statusCode(), expected,
                "Status code mismatch. Body: " + response.asString());
    }

    public static void assertResponseTime(Response response) {
        long threshold = config.getResponseTimeout();
        log.info("Response time: {}ms (threshold: {}ms)", response.time(), threshold);
        Assert.assertTrue(response.time() < threshold,
                "Response time " + response.time() + "ms exceeded " + threshold + "ms");
    }

    public static void assertFieldNotNull(Response response, String jsonPath) {
        Object value = response.jsonPath().get(jsonPath);
        log.info("Asserting field '{}' is not null: {}", jsonPath, value);
        Assert.assertNotNull(value, "Field '" + jsonPath + "' should not be null. Body: " + response.asString());
    }

    public static void assertFieldEquals(Response response, String jsonPath, Object expected) {
        Object actual = response.jsonPath().get(jsonPath);
        log.info("Asserting field '{}': expected='{}', actual='{}'", jsonPath, expected, actual);
        Assert.assertEquals(actual, expected, "Field '" + jsonPath + "' value mismatch");
    }

    public static void assertListNotEmpty(Response response, String jsonPath) {
        List<?> list = response.jsonPath().getList(jsonPath);
        Assert.assertNotNull(list, "List at '" + jsonPath + "' is null");
        Assert.assertFalse(list.isEmpty(), "List at '" + jsonPath + "' should not be empty");
    }

    public static void logResponse(Response response) {
        log.info("Response => Status: {} | Time: {}ms | Size: {}b",
                response.statusCode(), response.time(), response.asByteArray().length);
    }
}
