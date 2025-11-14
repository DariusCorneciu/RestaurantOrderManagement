package com.example.restaurantordermanagement.DAO;

import com.example.restaurantordermanagement.models.Product;
import com.example.restaurantordermanagement.models.Table;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileProductDAO implements ProductDAO{

    private final String filePath = "data/products.txt";
    @Override
    public List<Product> loadList() {
        List<Product> products = new ArrayList<>();
        try (Scanner cin = new Scanner(new File(filePath))) {

            while(cin.hasNextLine()){
                String data = cin.nextLine();
                if(data.isEmpty()) continue;
                String[] columns = data.split(";");
                if(columns.length < 2){
                    throw new IOException("Linia "+data+" are format incorect!");
                }
                /// 1;Pizza;30.99
                int id = Integer.parseInt(columns[0]);
                String name = columns[1];
                double price = Double.parseDouble(columns[2]);
                products.add(new Product(id,name,price));

            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        Product.counterId = products.stream()
                .map(e->e.getId())
                .max(Integer::compareTo)
                .orElse(0);

        return products;
    }

    @Override
    public int save(Product product) {
        product.setId(++Product.counterId);
        String data = product.generateFileFormat();
        try(BufferedWriter writer = new BufferedWriter(
                new FileWriter(filePath,true))){
            writer.write(data);
            return product.getId();

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void delete(Product product) {
        List<Product> productList = new ArrayList<>(loadList());
        int index = productList.indexOf(product);
        if(index != -1){
            productList.remove(index);
            saveAll(productList);
        }
    }

    @Override
    public void update(Product table) {
        List<Product> productList = new ArrayList<>(loadList());
        int index = productList.indexOf(table);
        if(index !=-1){
            productList.set(index,table);
            saveAll(productList);
        }
    }

    private void saveAll(List<Product> tablesList){
        try(BufferedWriter writer = new BufferedWriter(
                new FileWriter(filePath,false))){
            for(Product t: tablesList){
                writer.write(t.generateFileFormat());
            }


        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
