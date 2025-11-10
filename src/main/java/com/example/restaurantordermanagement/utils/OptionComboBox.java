package com.example.restaurantordermanagement.utils;

public class OptionComboBox {
    private final int value;
    private final String label;

    public OptionComboBox(int id, String name) {
        this.value = id;
        this.label = name;
    }

    public int getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString(){
        return label;
    }
}
