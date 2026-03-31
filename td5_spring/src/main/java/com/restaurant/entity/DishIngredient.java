package com.restaurant.entity;

public class DishIngredient {
    private Dish dish;
    private Ingredient ingredient;
    private Double quantity;
    private Unit unit;

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Double getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "DishIngredient{" +
                "dish=" + dish +
                ", ingredient=" + ingredient +
                ", quantity=" + quantity +
                ", unit=" + unit +
                '}';
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
}


