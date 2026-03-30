package com.restaurant.repository;

import com.restaurant.entity.Ingredient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class IngredientRepository {

    private final JdbcTemplate jdbc;

    public IngredientRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<Ingredient> ingredientMapper = (rs, rowNum) ->
            new Ingredient(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getDouble("price")
            );

    public List<Ingredient> findAll() {
        return jdbc.query(
                "SELECT id, name, category, price FROM ingredient",
                ingredientMapper
        );
    }

    public Optional<Ingredient> findById(int id) {
        List<Ingredient> results = jdbc.query(
                "SELECT id, name, category, price FROM ingredient WHERE id = ?",
                ingredientMapper,
                id
        );
        return results.stream().findFirst();
    }
}