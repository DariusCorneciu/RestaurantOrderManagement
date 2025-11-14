package com.example.restaurantordermanagement.service;

import com.example.restaurantordermanagement.models.Clocking;
import com.example.restaurantordermanagement.models.Order;
import com.example.restaurantordermanagement.models.ProductOrder;
import com.example.restaurantordermanagement.repository.OrderRepository;
import com.example.restaurantordermanagement.utils.AlreadyExistsException;
import com.example.restaurantordermanagement.utils.AppContext;
import com.example.restaurantordermanagement.utils.ElementNotFoundException;

import java.util.List;

public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void create(Order order){

        order.getEmployee(); //throw  ElementNotFoundException
        order.getTable();  //throw  ElementNotFoundException
        orderRepository.create(order);
    }

    public void closeOrder(int orderId){
       Order o = orderRepository.findElementById(orderId);
       if(o == null){
          throw new  ElementNotFoundException("No order with that id!");
       }
       Order updatedOrder = new Order(o);

       updatedOrder.setActive(false);
       orderRepository.update(updatedOrder);
    }

    public int tableHasActiveOrder(int tableId){

        Order o = orderRepository.allProducts().stream().filter(e->e.getTableId() == tableId && e.isActive()).findFirst().orElse(null);

        if(o == null){
            return -1;
        }

        return o.getId();

    }

    public List<Order> getAllActiveOrders(){
       return  orderRepository.allProducts().stream().filter(e->e.isActive()).toList();


    }
}
