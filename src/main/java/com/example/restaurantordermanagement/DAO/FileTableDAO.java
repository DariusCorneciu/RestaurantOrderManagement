package com.example.restaurantordermanagement.DAO;

import com.example.restaurantordermanagement.models.Job;
import com.example.restaurantordermanagement.models.Table;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileTableDAO implements TableDAO {

    private final String filePath = "data/table.txt";
    @Override
    public List<Table> loadList() {
        List<Table> tables = new ArrayList<>();
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
                int seats = Integer.parseInt(columns[1]);
                String name = columns[2];
                double x = Double.parseDouble(columns[3]);
                double y = Double.parseDouble(columns[4]);
                tables.add(new Table(id,seats,name,x,y));

            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        Table.counterId = tables.stream()
                .map(e->e.getId())
                .max(Integer::compareTo)
                .orElse(0);

        return tables;
    }

    @Override
    public int save(Table table) {
        table.setId(++Table.counterId);
        String data = table.generateFileFormat();
        try(BufferedWriter writer = new BufferedWriter(
                new FileWriter(filePath,true))){
            writer.write(data);
            return table.getId();

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void delete(Table table) {
        List<Table> tableList = new ArrayList<>(loadList());
        int index = tableList.indexOf(table);
        if(index != -1){
            tableList.remove(index);
            saveAll(tableList);
        }
    }

    @Override
    public void update(Table table) {
        List<Table> tableList = new ArrayList<>(loadList());
        int index = tableList.indexOf(table);
        if(index !=-1){
            tableList.set(index,table);
            saveAll(tableList);
        }
    }

    private void saveAll(List<Table> tablesList){
        try(BufferedWriter writer = new BufferedWriter(
                new FileWriter(filePath,false))){
            for(Table t: tablesList){
                writer.write(t.generateFileFormat());
            }


        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
