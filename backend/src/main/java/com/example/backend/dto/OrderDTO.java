package com.example.backend.dto;

public class OrderDTO {

    private String userId;

    private String clientTime;

    private int clientQuantity;

    public OrderDTO(String userId, String clientTime, int clientQuantity) {
        this.userId = userId;
        this.clientTime = clientTime;
        this.clientQuantity = clientQuantity;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClientTime() {
        return clientTime;
    }

    public void setClientTime(String clientTime) {
        this.clientTime = clientTime;
    }

    public int getClientQuantity() {
        return clientQuantity;
    }

    public void setClientQuantity(int clientQuantity) {
        this.clientQuantity = clientQuantity;
    }

}
