package com.example.restaurantordermanagement.DAO;

import com.example.restaurantordermanagement.models.Order;
import com.example.restaurantordermanagement.models.Product;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileOrderDAO  implements OrderDAO{
    private final String filePath = "data/orders.txt";
    @Override
    public List<Order> loadList() {
        List<Order> orders = new ArrayList<>();
        try (Scanner cin = new Scanner(new File(filePath))) {

            while(cin.hasNextLine()){
                String data = cin.nextLine();
                if(data.isEmpty()) continue;
                String[] columns = data.split(";");
                if(columns.length < 2){
                    throw new IOException("Linia "+data+" are format incorect!");
                }
                ///1;1;1
                int id = Integer.parseInt(columns[0]);
                int tableId = Integer.parseInt(columns[1]);
                int employeeId = Integer.parseInt(columns[2]);
                boolean isActive = Boolean.parseBoolean(columns[3]);
               // System.out.println(id+" "+tableId+" "+employeeId+" "+isActive);

                orders.add(new Order(id,tableId,employeeId,isActive));

            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        Order.counterId = orders.stream()
                .map(e->e.getId())
                .max(Integer::compareTo)
                .orElse(0);

        return orders;
    }

    @Override
    public int save(Order order) {
        order.setId(++Order.counterId);
        String data = order.generateFileFormat();
        try(BufferedWriter writer = new BufferedWriter(
                new FileWriter(filePath,true))){
            writer.write(data);
            return order.getId();

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void delete(Order order) {
        List<Order> orderList = new ArrayList<Order>(loadList());
        int index = orderList.indexOf(order);
        if(index != -1){
            orderList.remove(index);
            saveAll(orderList);
        }
    }

    @Override
    public void update(Order order) {
        List<Order> orderList = new ArrayList<>(loadList());
        int index = orderList.indexOf(order);
        if(index !=-1){
            orderList.set(index,order);
            saveAll(orderList);
        }
    }

    private void saveAll(List<Order> orderList){
        try(BufferedWriter writer = new BufferedWriter(
                new FileWriter(filePath,false))){
            for(Order t: orderList){
                writer.write(t.generateFileFormat());
            }


        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
