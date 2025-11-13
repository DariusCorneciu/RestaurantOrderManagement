package com.example.restaurantordermanagement.models;


import java.util.Objects;


public class Table {
    private int id;
    private int seats;
    private String name;
    private double xPercent;
    private double yPercent;
    public static int counterId=0;

    public Table(int id, int seats, String name, double xPercent, double yPercent) {
        this.id = id;
        this.seats = seats;
        this.name = name;
        this.xPercent = xPercent;
        this.yPercent = yPercent;
    }


    public String generateFileFormat(){
        return id+";"+seats+";"+name+";"+xPercent+";"+yPercent+"\n";
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return id == table.id;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getSeats() {
        return seats;
    }
    public String getName() {
        return name;
    }
    public double getxPercent() {
        return xPercent;
    }
    public double getyPercent() {
        return yPercent;
    }
}
