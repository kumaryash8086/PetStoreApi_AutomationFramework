package com.petstore.models.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreOrderRequest {

    private Long id;
    private Long petId;
    private Integer quantity;
    private String shipDate;
    private String status;
    private Boolean complete;

    public StoreOrderRequest() {}

    public Long getId()          { return id; }
    public Long getPetId()       { return petId; }
    public Integer getQuantity() { return quantity; }
    public String getShipDate()  { return shipDate; }
    public String getStatus()    { return status; }
    public Boolean getComplete() { return complete; }

    public void setId(Long id)            { this.id = id; }
    public void setPetId(Long petId)      { this.petId = petId; }
    public void setQuantity(Integer qty)  { this.quantity = qty; }
    public void setShipDate(String date)  { this.shipDate = date; }
    public void setStatus(String status)  { this.status = status; }
    public void setComplete(Boolean done) { this.complete = done; }

    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private final StoreOrderRequest o = new StoreOrderRequest();
        public Builder id(Long id)            { o.id = id;         return this; }
        public Builder petId(Long petId)      { o.petId = petId;   return this; }
        public Builder quantity(Integer qty)  { o.quantity = qty;  return this; }
        public Builder shipDate(String date)  { o.shipDate = date; return this; }
        public Builder status(String status)  { o.status = status; return this; }
        public Builder complete(Boolean done) { o.complete = done; return this; }
        public StoreOrderRequest build()      { return o; }
    }
}
