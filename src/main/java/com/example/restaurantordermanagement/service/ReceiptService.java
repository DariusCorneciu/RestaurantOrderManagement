package com.example.restaurantordermanagement.service;

import com.example.restaurantordermanagement.models.Job;
import com.example.restaurantordermanagement.models.ProductOrder;
import com.example.restaurantordermanagement.models.Receipt;
import com.example.restaurantordermanagement.repository.ReceiptRepository;
import com.example.restaurantordermanagement.repository.TableRepository;
import com.example.restaurantordermanagement.utils.AppContext;
import com.example.restaurantordermanagement.utils.EmptyStringException;
import com.example.restaurantordermanagement.utils.SameElementException;

import java.util.Collections;
import java.util.List;

public class ReceiptService {
    private final ReceiptRepository receiptRepository;

    public ReceiptService(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    public void create(Receipt receipt){
        if(receipt.getReceiptCode() == null ||receipt.getReceiptCode().isBlank()){
            throw new EmptyStringException("Empty Receipt code");
        }

        List<ProductOrder> list = AppContext.getProductOrderService().productsOfAnOrder(receipt.getOrderId());
           double total =0;
            for(ProductOrder p:list){
                total = p.getProduct().getPrice() *p.getQuantity();
            }
        System.out.println("Total: "+total);
            receipt.setTotalPrice(total);

       receiptRepository.create(receipt);
    }
    public List<Receipt> allReceipts(){
        //trimit o lista
        return receiptRepository.allReceipts();
    }




}
