package com.example.restaurantordermanagement.DAO;

import com.example.restaurantordermanagement.models.Clocking;
import com.example.restaurantordermanagement.models.Table;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileClockingDAO implements ClockingDAO {
    private final String filePath = "data/clocking.txt";
    @Override
    public List<Clocking> loadList() {
        List<Clocking> clockings = new ArrayList<>();
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
                int employeeId = Integer.parseInt(columns[1]);
                LocalDateTime clockin = LocalDateTime.parse(columns[2]);
                LocalDateTime clockout = null;
                if(!columns[3].equals("null")){
                  clockout = LocalDateTime.parse(columns[3]);
                }


                clockings.add(new Clocking(id,employeeId,clockin,clockout));

            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        Clocking.counterId = clockings.stream()
                .map(e->e.getId())
                .max(Integer::compareTo)
                .orElse(0);


        return clockings;
    }

    @Override
    public int save(Clocking clocking) {
        clocking.setId(++Clocking.counterId);
        String data = clocking.generateFileFormat();
        try(BufferedWriter writer = new BufferedWriter(
                new FileWriter(filePath,true))){
            writer.write(data);
            return clocking.getId();

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void delete(Clocking clocking) {
        List<Clocking> tableList = new ArrayList<>(loadList());
        int index = tableList.indexOf(clocking);
        if(index != -1){
            tableList.remove(index);
            saveAll(tableList);
        }
    }

    @Override
    public void update(Clocking clocking) {
        List<Clocking> tableList = new ArrayList<>(loadList());
        int index = tableList.indexOf(clocking);
        if(index !=-1){
            tableList.set(index,clocking);
            saveAll(tableList);
        }
    }

    private void saveAll(List<Clocking> tablesList){
        try(BufferedWriter writer = new BufferedWriter(
                new FileWriter(filePath,false))){
            for(Clocking t: tablesList){
                writer.write(t.generateFileFormat());
            }


        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
