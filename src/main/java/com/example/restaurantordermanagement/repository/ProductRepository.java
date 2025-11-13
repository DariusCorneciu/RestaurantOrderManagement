package com.example.restaurantordermanagement.repository;

import com.example.restaurantordermanagement.DAO.ProductDAO;
import com.example.restaurantordermanagement.models.Product;
import com.example.restaurantordermanagement.models.Table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductRepository {
    private final ProductDAO productDAO;
    private final List<Product> productList;

    public ProductRepository(ProductDAO productDAO) {
        this.productDAO = productDAO;
        productList = new ArrayList<>(productDAO.loadList());
    }
    public void create(Product product){
        int id = productDAO.save(product);
        if(id != -1){
            product.setId(id);
            productList.add(product);
        }

    }
    public void update(Product product){
        int index = productList.indexOf(product);
        if(index != -1){
            productList.set(index,product);
            productDAO.update(product);
        }


    }
    public void delete(Product product){
        int index = productList.indexOf(product);
        if(index != -1){
            productList.remove(index);
            productDAO.delete(product);
        }
    }

    public Product findElementById(int id){

        for(Product product:productList){
            if(product.getId() == id){
                return product;
            }
        }
        return null;
    }
    public List<Product> allProducts(){
        //trimit o lista
        return Collections.unmodifiableList(productList);
    }
}
