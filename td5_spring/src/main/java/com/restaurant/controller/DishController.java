package com.restaurant.controller;

import com.restaurant.entity.Dish;
import com.restaurant.entity.Ingredient;
import com.restaurant.repository.DishRepository;
import com.restaurant.repository.IngredientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dishes")
public class DishController {

    private final DishRepository dishRepository;
    private final IngredientRepository ingredientRepository;

    public DishController(DishRepository dishRepository,
                          IngredientRepository ingredientRepository) {
        this.dishRepository = dishRepository;
        this.ingredientRepository = ingredientRepository;
    }

    // d) GET /dishes
    @GetMapping
    public ResponseEntity<List<Dish>> getAll() {
        return ResponseEntity.ok(dishRepository.findAll());
    }

    // e) PUT /dishes/{id}/ingredients
    @PutMapping("/{id}/ingredients")
    public ResponseEntity<?> updateIngredients(
            @PathVariable int id,
            @RequestBody(required = false) List<Ingredient> ingredients) {

        // Corps obligatoire
        if (ingredients == null) {
            return ResponseEntity.status(400)
                    .body("Request body is required and must contain a list of ingredients.");
        }

        // Vérification existence du plat
        Optional<Dish> dish = dishRepository.findById(id);
        if (dish.isEmpty()) {
            return ResponseEntity.status(404)
                    .body("Dish.id=" + id + " is not found");
        }

        // Mise à jour (ignorer les ingrédients inconnus)
        List<Ingredient> allExisting = ingredientRepository.findAll();
        dishRepository.updateIngredients(id, ingredients, allExisting);

        // Retourner le plat mis à jour
        return ResponseEntity.ok(dishRepository.findById(id).get());
    }
}