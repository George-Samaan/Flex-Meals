package com.iti.flex_meals.db.retrofit.pojo.categoriesList;

import java.util.List;

public class CategoriesListResponse {
    private List<CategoryListDetailed> meals;

    public List<CategoryListDetailed> getCategoriesList() {
        return meals;
    }
}