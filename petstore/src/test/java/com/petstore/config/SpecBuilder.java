package com.petstore.config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpecBuilder {

    private static final Logger log = LoggerFactory.getLogger(SpecBuilder.class);
    private static final ConfigManager config = ConfigManager.getInstance();

    private SpecBuilder() {}

    public static RequestSpecification getDefaultSpec() {
        log.debug("Building default request spec with API key auth");
        return new RequestSpecBuilder()
                .setBaseUri(config.getBaseUrl())
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addHeader(config.getApiKeyHeader(), config.getApiKey())
                .log(LogDetail.ALL)
                .build();
    }

    public static RequestSpecification getNoAuthSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(config.getBaseUrl())
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    public static RequestSpecification getMultipartSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(config.getBaseUrl())
                .setContentType("multipart/form-data")
                .addHeader(config.getApiKeyHeader(), config.getApiKey())
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification getResponseSpec() {
        return new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();
    }
}
