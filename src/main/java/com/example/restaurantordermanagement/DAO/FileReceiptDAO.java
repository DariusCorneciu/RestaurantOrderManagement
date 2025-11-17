package com.example.restaurantordermanagement.DAO;

import com.example.restaurantordermanagement.models.Receipt;
import com.example.restaurantordermanagement.models.Table;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReceiptDAO implements ReceiptDAO {
    private final String filePath = "data/receipts.txt";
    @Override
    public List<Receipt> loadList() {
        List<Receipt> receipts = new ArrayList<>();
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
                int orderId = Integer.parseInt(columns[1]);
                String receiptCode = columns[2];
                LocalDate date = LocalDate.parse(columns[3]);
                double totalPrice = Double.parseDouble(columns[4]);
                System.out.println(columns[4]+" "+totalPrice);
//                public String generateFileFormat(){
//                    return this.id+";"+this.orderId+";"+receiptCode+";"+date+"\n";
//                }
                receipts.add(new Receipt(id,orderId,receiptCode,date,totalPrice));

            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        Receipt.counterId = receipts.stream()
                .map(e->e.getId())
                .max(Integer::compareTo)
                .orElse(0);

        return receipts;
    }

    @Override
    public int save(Receipt receipt) {
        receipt.setId(++Receipt.counterId);
        String data = receipt.generateFileFormat();
        try(BufferedWriter writer = new BufferedWriter(
                new FileWriter(filePath,true))){
            writer.write(data);
            return receipt.getId();

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void delete(Receipt receipt) {
        List<Receipt> receiptList = new ArrayList<>(loadList());
        int index = receiptList.indexOf(receipt);
        if(index != -1){
            receiptList.remove(index);
            saveAll(receiptList);
        }
    }

    @Override
    public void update(Receipt receipt) {
        List<Receipt> receiptList = new ArrayList<>(loadList());
        int index = receiptList.indexOf(receipt);
        if(index !=-1){
            receiptList.set(index,receipt);
            saveAll(receiptList);
        }
    }

    private void saveAll(List<Receipt> tablesList){
        try(BufferedWriter writer = new BufferedWriter(
                new FileWriter(filePath,false))){
            for(Receipt t: tablesList){
                writer.write(t.generateFileFormat());
            }


        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
