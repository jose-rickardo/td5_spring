package com.restaurant.repository;


import com.restaurant.entity.CategoryEnum;
import com.restaurant.entity.Ingredient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class IngredientRepository {

    private final DataSource dataSource;

    public List<Ingredient> findAll() {
        List<Ingredient> list = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement ps = conn.prepareStatement("""
                select id, name, price, category from ingredient
            """);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Ingredient(
                        rs.getInt("id"),
                        rs.getString("name"),
                        CategoryEnum.valueOf(rs.getString("category")),
                        rs.getDouble("price"),
                        new ArrayList<>()
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }
}