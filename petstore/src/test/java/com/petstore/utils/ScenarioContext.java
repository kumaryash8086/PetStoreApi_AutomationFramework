package com.petstore.utils;

import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {

    private final Map<String, Object> context = new HashMap<>();
    private Response lastResponse;

    // Context Keys
    public static final String PET_ID     = "PET_ID";
    public static final String PET_NAME   = "PET_NAME";
    public static final String PET_STATUS = "PET_STATUS";
    public static final String ORDER_ID   = "ORDER_ID";
    public static final String USERNAME   = "USERNAME";
    public static final String PASSWORD   = "PASSWORD";

    public void set(String key, Object value) {	
        context.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) context.get(key);
    }

    public String getString(String key) {
        Object val = context.get(key);
        return val != null ? val.toString() : null;
    }

    public Long getLong(String key) {
        Object val = context.get(key);
        if (val == null)             return null;
        if (val instanceof Long)     return (Long) val;
        if (val instanceof Integer)  return ((Integer) val).longValue();
        return Long.parseLong(val.toString());
    }

    public void setResponse(Response response) { this.lastResponse = response; }
    public Response getResponse()              { return lastResponse; }

    public void clear() {
        context.clear();
        lastResponse = null;
    }
}
