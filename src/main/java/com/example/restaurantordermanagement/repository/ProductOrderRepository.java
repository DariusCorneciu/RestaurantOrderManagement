package com.example.restaurantordermanagement.repository;

import com.example.restaurantordermanagement.DAO.ProductOrderDAO;
import com.example.restaurantordermanagement.DAO.TableDAO;
import com.example.restaurantordermanagement.models.ProductOrder;
import com.example.restaurantordermanagement.models.Table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductOrderRepository {
    private final ProductOrderDAO productOrderDAO;
    private final List<ProductOrder> productOrders;

    public ProductOrderRepository(ProductOrderDAO productOrderDAO) {
        this.productOrderDAO = productOrderDAO;
        productOrders = new ArrayList<ProductOrder>(productOrderDAO.loadList());
    }
    public void create(ProductOrder productOrder){
        int id = productOrderDAO.save(productOrder);
        System.out.println("CreatePO:"+id);
        if(id != -1){
            System.out.println(productOrder.generateFileFormat());
            productOrder.setId(id);
            productOrders.add(productOrder);
        }

    }
    public void update(ProductOrder productOrder){
        int index = productOrders.indexOf(productOrder);
        System.out.println("UpdatePO:"+index);
        if(index != -1){
            productOrders.set(index,productOrder);
            System.out.println(productOrder.generateFileFormat());
            productOrderDAO.update(productOrder);
        }


    }
    public void delete(ProductOrder productOrder){
        int index = productOrders.indexOf(productOrder);
        System.out.println("DeletePO:"+index);
        if(index != -1){
            productOrders.remove(index);
            System.out.println(productOrder.generateFileFormat());
            productOrderDAO.delete(productOrder);
        }
    }
    public ProductOrder findElementById(int id){
        //Job jobToReturn = null;
        for(ProductOrder t:productOrders){
            if(t.getId() == id){
                return t;
            }
        }
        return null;
    }
    public List<ProductOrder> allProducts(){
        //trimit o lista
        return Collections.unmodifiableList(productOrders);
    }

    public List<ProductOrder> productsOfAnOrder(int orderId){
        return productOrders.stream()
                .filter(po->po.getOrderId() == orderId)
                .toList();
    }

    public void substractQuantity(ProductOrder productOrder){
        ProductOrder updatedProduct = new ProductOrder(productOrder);
        updatedProduct.substractQuantity();
    }

    public void addQUantity(ProductOrder productOrder){
        ProductOrder updatedProduct = new ProductOrder(productOrder);
        updatedProduct.addQuantity();
    }
}
