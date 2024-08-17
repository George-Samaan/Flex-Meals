package com.iti.flex_meals.db.retrofit.pojo.ingredients;

import java.util.List;

public class IngredientsResponse {
    private List<IngredientItem> meals;

    public List<IngredientItem> getIngredients() {
        return meals;
    }
}