package com.petstore.models.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequest {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private Integer userStatus;

    public UserRequest() {}

    public Long getId()            { return id; }
    public String getUsername()    { return username; }
    public String getFirstName()   { return firstName; }
    public String getLastName()    { return lastName; }
    public String getEmail()       { return email; }
    public String getPassword()    { return password; }
    public String getPhone()       { return phone; }
    public Integer getUserStatus() { return userStatus; }

    public void setId(Long id)                 { this.id = id; }
    public void setUsername(String username)   { this.username = username; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName)   { this.lastName = lastName; }
    public void setEmail(String email)         { this.email = email; }
    public void setPassword(String password)   { this.password = password; }
    public void setPhone(String phone)         { this.phone = phone; }
    public void setUserStatus(Integer status)  { this.userStatus = status; }

    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private final UserRequest u = new UserRequest();
        public Builder id(Long id)                 { u.id = id;               return this; }
        public Builder username(String v)          { u.username = v;          return this; }
        public Builder firstName(String v)         { u.firstName = v;         return this; }
        public Builder lastName(String v)          { u.lastName = v;          return this; }
        public Builder email(String v)             { u.email = v;             return this; }
        public Builder password(String v)          { u.password = v;          return this; }
        public Builder phone(String v)             { u.phone = v;             return this; }
        public Builder userStatus(Integer v)       { u.userStatus = v;        return this; }
        public UserRequest build()                 { return u; }
    }
}
