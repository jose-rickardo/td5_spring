package com.restaurant.controller;

import com.restaurant.entity.Ingredient;
import com.restaurant.service.IngredientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IngredientController {
    private final IngredientService ingredientService;
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }
    @GetMapping("/ingredients")
    public List<Ingredient> getAll() {
        return ingredientService.getAllIngredients();
    }
}
