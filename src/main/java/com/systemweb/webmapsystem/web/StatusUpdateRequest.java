package com.systemweb.webmapsystem.web;

public class StatusUpdateRequest {
    private String status;

    // Default constructor for JSON deserialization
    public StatusUpdateRequest() {}

    public StatusUpdateRequest(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;  
    }
}
