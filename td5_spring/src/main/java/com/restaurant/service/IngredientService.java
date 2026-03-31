package com.restaurant.service;

import com.restaurant.entity.Ingredient;
import com.restaurant.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {
    private final IngredientRepository IngredientrRepository;
    public IngredientService(IngredientRepository IngredientrRepository) {
        this.IngredientrRepository = IngredientrRepository;
    }
    public List<Ingredient> getAllIngredients() {
        return IngredientrRepository.findAll();
    }
}
