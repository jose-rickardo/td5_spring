package com.restaurant.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StockMovementRepository {

    private final JdbcTemplate jdbc;

    public StockMovementRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * Calcule le stock d'un ingrédient à une date donnée.
     * On suppose que la table stock_movement a : ingredient_id, quantity, unit, movement_date
     */
    public double getStockAtDate(int ingredientId, String at, String unit) {
        String sql = """
            SELECT COALESCE(SUM(quantity), 0)
            FROM stock_movement
            WHERE ingredient_id = ?
              AND unit = ?
              AND movement_date <= ?::timestamp
            """;
        Double result = jdbc.queryForObject(sql, Double.class, ingredientId, unit, at);
        return result != null ? result : 0.0;
    }
}