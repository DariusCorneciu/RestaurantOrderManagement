package com.example.restaurantordermanagement.repository;

import com.example.restaurantordermanagement.DAO.OrderDAO;
import com.example.restaurantordermanagement.models.Order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderRepository {

    private final OrderDAO orderDAO;
    private final List<Order> orderList;

    public OrderRepository(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
        orderList = new ArrayList<>(orderDAO.loadList());
    }
    public void create(Order order){
        int id = orderDAO.save(order);
        if(id != -1){
            order.setId(id);
            orderList.add(order);
        }

    }
    public void update(Order order){
        int index = orderList.indexOf(order);
        if(index != -1){
            orderList.set(index,order);
            orderDAO.update(order);
        }


    }
    public void delete(Order order){
        int index = orderList.indexOf(order);
        if(index != -1){
            orderList.remove(index);
            orderDAO.delete(order);
        }
    }

    public Order findElementById(int id){

        for(Order product:orderList){
            if(product.getId() == id){
                return product;
            }
        }
        return null;
    }
    public List<Order> allProducts(){
        //trimit o lista
        return Collections.unmodifiableList(orderList);
    }
}
