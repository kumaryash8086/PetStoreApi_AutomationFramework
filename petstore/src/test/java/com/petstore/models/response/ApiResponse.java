package com.petstore.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse {
    private Integer code;
    private String type;
    private String message;
    public ApiResponse() {}
    public Integer getCode()    { return code; }
    public String getType()     { return type; }
    public String getMessage()  { return message; }
    public void setCode(Integer code)      { this.code = code; }
    public void setType(String type)       { this.type = type; }
    public void setMessage(String message) { this.message = message; }
}
