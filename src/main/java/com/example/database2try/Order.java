package com.example.database2try;

import java.util.Date;

public class Order {
    private int orderId;
    private String OrderType;
    private double totalPrice;
    private String paymentMethod;
    private String address;
    private int phoneNumber;

    private  Date order_date;
    private int userId;

    public Order(Date order_date) {
        this.order_date = order_date;
    }

    public Order(int orderId, String orderType, double totalPrice, String paymentMethod, String address, int phoneNumber, Date order_date, int userId) {
        this.orderId = orderId;
        OrderType = orderType;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.order_date=order_date;
        this.userId = userId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderType() {
        return OrderType;
    }

    public void setOrderType(String orderType) {
        OrderType = orderType;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public  Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", OrderType='" + OrderType + '\'' +
                ", totalPrice=" + totalPrice +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", userId=" + userId +
                order_date+
                '}';
    }
}