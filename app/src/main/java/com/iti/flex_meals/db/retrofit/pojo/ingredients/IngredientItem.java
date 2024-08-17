package com.iti.flex_meals.db.retrofit.pojo.ingredients;

public class IngredientItem {
    private String strDescription;
    private String strIngredient;
    private Object strType;
    private String idIngredient;

    public String getStrDescription() {
        return strDescription;
    }

    public String getStrIngredient() {
        return strIngredient;
    }

    public Object getStrType() {
        return strType;
    }

    public String getImageUrl() {
        return "https://www.themealdb.com/images/ingredients/" + strIngredient + ".png";
    }


    public String getIdIngredient() {
        return idIngredient;
    }
}
