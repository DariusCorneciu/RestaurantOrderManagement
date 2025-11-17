package com.example.restaurantordermanagement.models;

import java.time.LocalDate;
import java.util.Objects;

public class Receipt {
    private int id;
    private int orderId;
    private Order order;
    private String receiptCode;
    private LocalDate date;
    private double totalPrice;
    public static int counterId;

    public Receipt(int id, int orderId, String receiptCode, LocalDate date,double totalPrice) {
        this.id = id;
        this.orderId = orderId;
        this.receiptCode = receiptCode;
        this.date = date;
        this.totalPrice = totalPrice;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Order getOrder() {
        if(order == null){

        }
        return order;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getReceiptCode() {
        return receiptCode;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Receipt receipt = (Receipt) o;
        return id == receipt.id;
    }
    public String generateFileFormat(){
        return this.id+";"+this.orderId+";"+receiptCode+";"+date+";"+totalPrice+"\n";
    }


}
