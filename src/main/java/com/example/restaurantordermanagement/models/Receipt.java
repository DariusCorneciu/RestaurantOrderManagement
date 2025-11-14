package com.example.restaurantordermanagement.models;

import java.time.LocalDate;
import java.util.Objects;

public class Receipt {
    private int id;
    private int orderId;
    private Order order;
    private String receiptCode;
    private LocalDate date;

    public Receipt(int id, int orderId, String receiptCode, LocalDate date) {
        this.id = id;
        this.orderId = orderId;
        this.receiptCode = receiptCode;
        this.date = date;
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

    public Order getOrder() {
        if(order == null){

        }
        return order;
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


}
