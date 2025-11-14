package com.example.restaurantordermanagement.models;

import com.example.restaurantordermanagement.utils.AppContext;

import java.util.Objects;

public class ProductOrder implements Comparable<ProductOrder>{

    private int id;
    private int productId;
    private Product product;
    private int orderId;
    private int quantity;
    private Order order;
    public static int counterId = 0;


    public ProductOrder(int id,int productId, int orderId,int quantity) {
        this.productId = productId;
        this.orderId = orderId;
        this.id = id;
        this.quantity = quantity;
    }
    public ProductOrder(ProductOrder other){
        this.productId = other.productId;
        this.orderId = other.orderId;
        this.id = other.id;
        this.quantity = other.quantity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public Product getProduct() {

        if(product == null){
            product = AppContext.getProductService().getProductById(productId);
        }
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public Order getOrder() {
        return order;
    }
    public void substractQuantity(){
        if(quantity>0)
        quantity--;
    }
    public void addQuantity(){
       quantity++;
    }

    public String generateFileFormat(){
        return id+";"+productId+";"+orderId+";"+quantity+"\n";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProductOrder that = (ProductOrder) o;
        return id == that.id;
    }

    @Override
    public int compareTo(ProductOrder o) {
        return Integer.compare(this.quantity, o.quantity);
    }
}
