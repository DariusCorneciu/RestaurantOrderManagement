package com.example.restaurantordermanagement.DAO;

import com.example.restaurantordermanagement.models.Product;
import com.example.restaurantordermanagement.models.ProductOrder;
import com.example.restaurantordermanagement.models.Table;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileProductOrderDTO implements ProductOrderDAO {
    private final String filePath = "data/productorder.txt";
    @Override
    public List<ProductOrder> loadList() {
        List<ProductOrder> productOrders = new ArrayList<>();
        try (Scanner cin = new Scanner(new File(filePath))) {

            while(cin.hasNextLine()){
                String data = cin.nextLine();
                if(data.isEmpty()) continue;
                String[] columns = data.split(";");
                if(columns.length < 2){
                    throw new IOException("Linia "+data+" are format incorect!");
                }
                /// 1;8;Bar;0.215;0.285
                int id = Integer.parseInt(columns[0]);
                int productId = Integer.parseInt(columns[1]);
                int orderId = Integer.parseInt(columns[2]);
                int quantity = Integer.parseInt(columns[3]);
                productOrders.add(new ProductOrder(id,productId,orderId,quantity));

            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        ProductOrder.counterId = productOrders.stream()
                .map(e->e.getId())
                .max(Integer::compareTo)
                .orElse(0);

        return productOrders;
    }

    @Override
    public int save(ProductOrder productOrder) {
        productOrder.setId(++ProductOrder.counterId);
        String data = productOrder.generateFileFormat();
        try(BufferedWriter writer = new BufferedWriter(
                new FileWriter(filePath,true))){
            writer.write(data);
            return productOrder.getId();

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void delete(ProductOrder table) {
        List<ProductOrder> productOrders = new ArrayList<>(loadList());
        int index = productOrders.indexOf(table);
        if(index != -1){
            productOrders.remove(index);
            saveAll(productOrders);
        }
    }

    @Override
    public void update(ProductOrder productOrder) {
        List<ProductOrder> productOrders = new ArrayList<>(loadList());
        int index = productOrders.indexOf(productOrder);
        if(index !=-1){
            productOrders.set(index,productOrder);
            saveAll(productOrders);
        }
    }

    private void saveAll(List<ProductOrder> productOrders){
        try(BufferedWriter writer = new BufferedWriter(
                new FileWriter(filePath,false))){
            for(ProductOrder t: productOrders){
                System.out.println(t.generateFileFormat());
                writer.write(t.generateFileFormat());
            }


        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
