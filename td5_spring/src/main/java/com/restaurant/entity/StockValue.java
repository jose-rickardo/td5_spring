package com.restaurant.entity;

public class StockValue {
    private String unit;
    private double value;

    public StockValue(String unit, double value) {
        this.unit = unit;
        this.value = value;
    }

    public String getUnit() { return unit; }
    public double getValue() { return value; }
}