package com.example.restaurantordermanagement.repository;

import com.example.restaurantordermanagement.DAO.ReceiptDAO;
import com.example.restaurantordermanagement.DAO.TableDAO;
import com.example.restaurantordermanagement.models.Receipt;
import com.example.restaurantordermanagement.models.Table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReceiptRepository {
    private final ReceiptDAO receiptDAO;
    private final List<Receipt> receiptList;

    public ReceiptRepository(ReceiptDAO receiptDAO) {
        this.receiptDAO = receiptDAO;
        receiptList = new ArrayList<Receipt>(receiptDAO.loadList());
    }
    public void create(Receipt receipt){
        int id = receiptDAO.save(receipt);

        if(id != -1){
            receipt.setId(id);
            receiptList.add(receipt);
        }

    }
    public void update(Receipt receipt){
        int index = receiptList.indexOf(receipt);
        if(index != -1){
            receiptList.set(index,receipt);
            receiptDAO.update(receipt);
        }


    }
    public void delete(Receipt receipt){
        int index = receiptList.indexOf(receipt);
        if(index != -1){
            receiptList.remove(index);
            receiptDAO.delete(receipt);
        }
    }
    public Receipt findElementById(int id){
        //Job jobToReturn = null;
        for(Receipt t:receiptList){
            if(t.getId() == id){
                return t;
            }
        }
        return null;
    }
    public List<Receipt> allReceipts(){
        //trimit o lista
        return Collections.unmodifiableList(receiptList);
    }
}
