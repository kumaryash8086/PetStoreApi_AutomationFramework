package com.petstore.models.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PetRequest {

    private Long id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private List<Tag> tags;
    private String status;

    public PetRequest() {}

    public Long getId()                { return id; }
    public Category getCategory()      { return category; }
    public String getName()            { return name; }
    public List<String> getPhotoUrls() { return photoUrls; }
    public List<Tag> getTags()         { return tags; }
    public String getStatus()          { return status; }

    public void setId(Long id)                       { this.id = id; }
    public void setCategory(Category category)       { this.category = category; }
    public void setName(String name)                 { this.name = name; }
    public void setPhotoUrls(List<String> photoUrls) { this.photoUrls = photoUrls; }
    public void setTags(List<Tag> tags)              { this.tags = tags; }
    public void setStatus(String status)             { this.status = status; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final PetRequest p = new PetRequest();
        public Builder id(Long id)               { p.id = id;       return this; }
        public Builder category(Category c)      { p.category = c;  return this; }
        public Builder name(String n)            { p.name = n;      return this; }
        public Builder photoUrls(List<String> u) { p.photoUrls = u; return this; }
        public Builder tags(List<Tag> t)         { p.tags = t;      return this; }
        public Builder status(String s)          { p.status = s;    return this; }
        public PetRequest build()                { return p; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Category {
        private Long id;
        private String name;
        public Category() {}
        public Long getId()              { return id; }
        public String getName()          { return name; }
        public void setId(Long id)       { this.id = id; }
        public void setName(String name) { this.name = name; }
        public static Builder builder()  { return new Builder(); }
        public static class Builder {
            private final Category c = new Category();
            public Builder id(Long id)       { c.id = id;     return this; }
            public Builder name(String name) { c.name = name; return this; }
            public Category build()          { return c; }
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Tag {
        private Long id;
        private String name;
        public Tag() {}
        public Long getId()              { return id; }
        public String getName()          { return name; }
        public void setId(Long id)       { this.id = id; }
        public void setName(String name) { this.name = name; }
        public static Builder builder()  { return new Builder(); }
        public static class Builder {
            private final Tag t = new Tag();
            public Builder id(Long id)       { t.id = id;     return this; }
            public Builder name(String name) { t.name = name; return this; }
            public Tag build()               { return t; }
        }
    }
}
