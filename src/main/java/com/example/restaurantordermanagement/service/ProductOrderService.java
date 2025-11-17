package com.example.restaurantordermanagement.service;

import com.example.restaurantordermanagement.models.Employee;
import com.example.restaurantordermanagement.models.Order;
import com.example.restaurantordermanagement.models.Product;
import com.example.restaurantordermanagement.models.ProductOrder;
import com.example.restaurantordermanagement.repository.ProductOrderRepository;
import com.example.restaurantordermanagement.utils.AppContext;
import com.example.restaurantordermanagement.utils.ElementNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ProductOrderService {

    private final ProductOrderRepository productOrderRepository;

    public ProductOrderService(ProductOrderRepository productOrderRepository) {
        this.productOrderRepository = productOrderRepository;
    }


    public List<ProductOrder> productsOfAnOrder(int orderId){
        List<ProductOrder> fillteredList = productOrderRepository.productsOfAnOrder(orderId);
        return fillteredList;
    }

    public void deleteProductOrder(int id){
        ProductOrder po = productOrderRepository.findElementById(id);
        productOrderRepository.delete(po);
    }

    public List<ProductOrder> getAllProductOrders(){
        return productOrderRepository.allProducts();
    }

    public void adjustProductQuantities(Order order,
                                        Map<Product, Integer> quantityMap){
        List<ProductOrder> productOrders =productsOfAnOrder(order.getId());
        for (var entry : quantityMap.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
           ProductOrder productOrder = productOrders.stream()
                   .filter(e->e.getProductId() == product.getId())
                   .findFirst().orElse(null);
           if(productOrder == null){
               if(quantity != 0){
                   productOrder = new ProductOrder(-1,product.getId(),order.getId(),quantity);
                   productOrderRepository.create(productOrder);
               }

           }else{
               if(quantity == 0){
                   ProductOrder deleteproductOrder = new ProductOrder(productOrder);
                   productOrderRepository.delete(deleteproductOrder);
               }else{
                   ProductOrder updatedProduct = new ProductOrder(productOrder);
                   updatedProduct.setQuantity(quantity);
                   productOrderRepository.update(updatedProduct);
               }

           }


        }

    }







}
