package com.iti.flex_meals.db.retrofit.pojo.mealDetails;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class MealsItem {
    private Object strImageSource;
    private String strIngredient10;
    private String strIngredient12;
    private String strIngredient11;
    private String strIngredient14;
    private String strCategory;
    private String strIngredient13;
    private String strIngredient16;
    private String strIngredient15;
    private String strIngredient18;
    private String strIngredient17;
    private String strArea;
    private Object strCreativeCommonsConfirmed;
    private String strIngredient19;
    private String strTags;
    private String idMeal;
    private String strInstructions;
    private String strIngredient1;
    private String strIngredient3;
    private String strIngredient2;
    private String strIngredient20;
    private String strIngredient5;
    private String strIngredient4;
    private String strIngredient7;
    private String strIngredient6;
    private String strIngredient9;
    private String strIngredient8;
    private String strMealThumb;
    private String strMeasure20;
    private String strYoutube;
    private String strMeal;
    private String strMeasure12;
    private String strMeasure13;
    private String strMeasure10;
    private String strMeasure11;
    private Object dateModified;
    private Object strDrinkAlternate;
    private String strSource;
    private String strMeasure9;
    private String strMeasure7;
    private String strMeasure8;
    private String strMeasure5;
    private String strMeasure6;
    private String strMeasure3;
    private String strMeasure4;
    private String strMeasure1;
    private String strMeasure18;
    private String strMeasure2;
    private String strMeasure19;
    private String strMeasure16;
    private String strMeasure17;
    private String strMeasure14;
    private String strMeasure15;

    public Object getStrImageSource() {
        return strImageSource;
    }

    public String getStrIngredient10() {
        return strIngredient10;
    }

    public String getStrIngredient12() {
        return strIngredient12;
    }

    public String getStrIngredient11() {
        return strIngredient11;
    }

    public String getStrIngredient14() {
        return strIngredient14;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public String getStrIngredient13() {
        return strIngredient13;
    }

    public String getStrIngredient16() {
        return strIngredient16;
    }

    public String getStrIngredient15() {
        return strIngredient15;
    }

    public String getStrIngredient18() {
        return strIngredient18;
    }

    public String getStrIngredient17() {
        return strIngredient17;
    }

    public String getStrArea() {
        return strArea;
    }

    public Object getStrCreativeCommonsConfirmed() {
        return strCreativeCommonsConfirmed;
    }

    public String getStrIngredient19() {
        return strIngredient19;
    }

    public String getStrTags() {
        return strTags;
    }

    public String getIdMeal() {
        return idMeal;
    }

    public String getStrInstructions() {
        return strInstructions;
    }

    public String getStrIngredient1() {
        return strIngredient1;
    }

    public String getStrIngredient3() {
        return strIngredient3;
    }

    public String getStrIngredient2() {
        return strIngredient2;
    }

    public String getStrIngredient20() {
        return strIngredient20;
    }

    public String getStrIngredient5() {
        return strIngredient5;
    }

    public String getStrIngredient4() {
        return strIngredient4;
    }

    public String getStrIngredient7() {
        return strIngredient7;
    }

    public String getStrIngredient6() {
        return strIngredient6;
    }

    public String getStrIngredient9() {
        return strIngredient9;
    }

    public String getStrIngredient8() {
        return strIngredient8;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public String getStrMeasure20() {
        return strMeasure20;
    }

    public String getStrYoutube() {
        return strYoutube;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public String getStrMeasure12() {
        return strMeasure12;
    }

    public String getStrMeasure13() {
        return strMeasure13;
    }

    public String getStrMeasure10() {
        return strMeasure10;
    }

    public String getStrMeasure11() {
        return strMeasure11;
    }

    public Object getDateModified() {
        return dateModified;
    }

    public Object getStrDrinkAlternate() {
        return strDrinkAlternate;
    }

    public String getStrSource() {
        return strSource;
    }

    public String getStrMeasure9() {
        return strMeasure9;
    }

    public String getStrMeasure7() {
        return strMeasure7;
    }

    public String getStrMeasure8() {
        return strMeasure8;
    }

    public String getStrMeasure5() {
        return strMeasure5;
    }

    public String getStrMeasure6() {
        return strMeasure6;
    }

    public String getStrMeasure3() {
        return strMeasure3;
    }

    public String getStrMeasure4() {
        return strMeasure4;
    }

    public String getStrMeasure1() {
        return strMeasure1;
    }

    public String getStrMeasure18() {
        return strMeasure18;
    }

    public String getStrMeasure2() {
        return strMeasure2;
    }

    public String getStrMeasure19() {
        return strMeasure19;
    }

    public String getStrMeasure16() {
        return strMeasure16;
    }

    public String getStrMeasure17() {
        return strMeasure17;
    }

    public String getStrMeasure14() {
        return strMeasure14;
    }

    public String getStrMeasure15() {
        return strMeasure15;
    }


    public List<Pair<String, String>> filterIngredientsAndMeasurements() {
        List<Pair<String, String>> pairs = new ArrayList<>();
        addPairToList(pairs, strIngredient1, strMeasure1);
        addPairToList(pairs, strIngredient2, strMeasure2);
        addPairToList(pairs, strIngredient3, strMeasure3);
        addPairToList(pairs, strIngredient4, strMeasure4);
        addPairToList(pairs, strIngredient5, strMeasure5);
        addPairToList(pairs, strIngredient6, strMeasure6);
        addPairToList(pairs, strIngredient7, strMeasure7);
        addPairToList(pairs, strIngredient8, strMeasure8);
        addPairToList(pairs, strIngredient9, strMeasure9);
        addPairToList(pairs, strIngredient10, strMeasure10);
        addPairToList(pairs, strIngredient11, strMeasure11);
        addPairToList(pairs, strIngredient12, strMeasure12);
        addPairToList(pairs, strIngredient13, strMeasure13);
        addPairToList(pairs, strIngredient14, strMeasure14);
        addPairToList(pairs, strIngredient15, strMeasure15);
        addPairToList(pairs, strIngredient16, strMeasure16);
        addPairToList(pairs, strIngredient17, strMeasure17);
        addPairToList(pairs, strIngredient18, strMeasure18);
        addPairToList(pairs, strIngredient19, strMeasure19);
        addPairToList(pairs, strIngredient20, strMeasure20);
        return pairs;
    }

    private void addPairToList(List<Pair<String, String>> list, String ingredient, String measure) {
        if (ingredient != null && !ingredient.trim().isEmpty() && measure != null && !measure.trim().isEmpty()) {
            list.add(new Pair<>(ingredient, measure));
        }
    }
}
