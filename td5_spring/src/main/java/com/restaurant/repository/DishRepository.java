package com.restaurant.repository;

import com.restaurant.entity.Dish;
import com.restaurant.entity.Ingredient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class DishRepository {

    private final JdbcTemplate jdbc;

    public DishRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Dish> findAll() {
        // Récupérer tous les plats
        List<Map<String, Object>> dishRows = jdbc.queryForList(
                "SELECT id, name, selling_price FROM dish"
        );

        List<Dish> dishes = new ArrayList<>();
        for (Map<String, Object> row : dishRows) {
            int dishId = (int) row.get("id");
            List<Ingredient> ingredients = getIngredientsByDishId(dishId);
            dishes.add(new Dish(
                    dishId,
                    (String) row.get("name"),
                    ((Number) row.get("selling_price")).doubleValue(),
                    ingredients
            ));
        }
        return dishes;
    }

    public Optional<Dish> findById(int id) {
        List<Map<String, Object>> rows = jdbc.queryForList(
                "SELECT id, name, selling_price FROM dish WHERE id = ?", id
        );
        if (rows.isEmpty()) return Optional.empty();

        Map<String, Object> row = rows.get(0);
        List<Ingredient> ingredients = getIngredientsByDishId(id);
        return Optional.of(new Dish(
                id,
                (String) row.get("name"),
                ((Number) row.get("selling_price")).doubleValue(),
                ingredients
        ));
    }

    private List<Ingredient> getIngredientsByDishId(int dishId) {
        return jdbc.query(
                """
                SELECT i.id, i.name, i.category, i.price
                FROM ingredient i
                JOIN dish_ingredient di ON di.ingredient_id = i.id
                WHERE di.dish_id = ?
                """,
                (rs, rn) -> new Ingredient(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getDouble("price")
                ),
                dishId
        );
    }

    /**
     * Remplace les ingrédients associés au plat par la liste fournie
     * (seuls les IDs existants en base sont pris en compte).
     */
    public void updateIngredients(int dishId, List<Ingredient> requestedIngredients,
                                  List<Ingredient> allExistingIngredients) {
        // Filtrer : garder uniquement les ingrédients qui existent réellement en base
        Set<Integer> existingIds = new HashSet<>();
        for (Ingredient i : allExistingIngredients) existingIds.add(i.getId());

        // Supprimer toutes les associations actuelles
        jdbc.update("DELETE FROM dish_ingredient WHERE dish_id = ?", dishId);

        // Réinsérer uniquement les ingrédients valides
        for (Ingredient ing : requestedIngredients) {
            if (existingIds.contains(ing.getId())) {
                jdbc.update(
                        "INSERT INTO dish_ingredient (dish_id, ingredient_id) VALUES (?, ?)",
                        dishId, ing.getId()
                );
            }
        }
    }
}