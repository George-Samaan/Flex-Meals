package com.iti.flex_meals.db.retrofit.pojo.mealDetails;


import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity(tableName = "MealDetailsTable", primaryKeys = {"idMeal", "UID"})
public class MealsItem {
    @NonNull
    private String idMeal;
    @NonNull
    private String UID;

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public void setStrIngredient10(String strIngredient10) {
        this.strIngredient10 = strIngredient10;
    }

    private String strIngredient10;

    public void setStrIngredient12(String strIngredient12) {
        this.strIngredient12 = strIngredient12;
    }

    private String strIngredient12;

    public void setStrIngredient11(String strIngredient11) {
        this.strIngredient11 = strIngredient11;
    }

    public void setStrIngredient14(String strIngredient14) {
        this.strIngredient14 = strIngredient14;
    }

    public void setStrIngredient13(String strIngredient13) {
        this.strIngredient13 = strIngredient13;
    }

    public void setStrIngredient16(String strIngredient16) {
        this.strIngredient16 = strIngredient16;
    }

    private String strIngredient11;
    private String strIngredient14;

    public void setStrCategory(String strCategory) {
        this.strCategory = strCategory;
    }

    public void setStrIngredient15(String strIngredient15) {
        this.strIngredient15 = strIngredient15;
    }

    public void setStrIngredient18(String strIngredient18) {
        this.strIngredient18 = strIngredient18;
    }

    public void setStrIngredient17(String strIngredient17) {
        this.strIngredient17 = strIngredient17;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }

    private String strCategory;
    private String strIngredient13;
    private String strIngredient16;
    private String strIngredient15;
    private String strIngredient18;
    private String strIngredient17;
    private String strArea;

    public void setStrIngredient19(String strIngredient19) {
        this.strIngredient19 = strIngredient19;
    }

    public void setStrInstructions(String strInstructions) {
        this.strInstructions = strInstructions;
    }

    public void setStrIngredient1(String strIngredient1) {
        this.strIngredient1 = strIngredient1;
    }

    public void setStrIngredient3(String strIngredient3) {
        this.strIngredient3 = strIngredient3;
    }

    public void setStrIngredient2(String strIngredient2) {
        this.strIngredient2 = strIngredient2;
    }

    public void setStrIngredient20(String strIngredient20) {
        this.strIngredient20 = strIngredient20;
    }

    public void setStrIngredient5(String strIngredient5) {
        this.strIngredient5 = strIngredient5;
    }

    public void setStrIngredient4(String strIngredient4) {
        this.strIngredient4 = strIngredient4;
    }

    public void setStrIngredient7(String strIngredient7) {
        this.strIngredient7 = strIngredient7;
    }

    private String strIngredient19;
    private String strInstructions;
    private String strIngredient1;
    private String strIngredient3;
    private String strIngredient2;
    private String strIngredient20;
    private String strIngredient5;
    private String strIngredient4;
    private String strIngredient7;

    public void setStrIngredient6(String strIngredient6) {
        this.strIngredient6 = strIngredient6;
    }

    public void setStrIngredient9(String strIngredient9) {
        this.strIngredient9 = strIngredient9;
    }

    public void setStrIngredient8(String strIngredient8) {
        this.strIngredient8 = strIngredient8;
    }

    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }

    public void setStrMeasure20(String strMeasure20) {
        this.strMeasure20 = strMeasure20;
    }

    public void setStrYoutube(String strYoutube) {
        this.strYoutube = strYoutube;
    }

    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public void setStrMeasure12(String strMeasure12) {
        this.strMeasure12 = strMeasure12;
    }

    public void setStrMeasure13(String strMeasure13) {
        this.strMeasure13 = strMeasure13;
    }

    public void setStrMeasure10(String strMeasure10) {
        this.strMeasure10 = strMeasure10;
    }

    public void setStrMeasure11(String strMeasure11) {
        this.strMeasure11 = strMeasure11;
    }

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
    private String strSource;
    private String strMeasure9;

    public void setStrSource(String strSource) {
        this.strSource = strSource;
    }

    public void setStrMeasure9(String strMeasure9) {
        this.strMeasure9 = strMeasure9;
    }

    public void setStrMeasure7(String strMeasure7) {
        this.strMeasure7 = strMeasure7;
    }

    public void setStrMeasure8(String strMeasure8) {
        this.strMeasure8 = strMeasure8;
    }

    public void setStrMeasure5(String strMeasure5) {
        this.strMeasure5 = strMeasure5;
    }

    public void setStrMeasure6(String strMeasure6) {
        this.strMeasure6 = strMeasure6;
    }

    public void setStrMeasure3(String strMeasure3) {
        this.strMeasure3 = strMeasure3;
    }

    public void setStrMeasure4(String strMeasure4) {
        this.strMeasure4 = strMeasure4;
    }

    public void setStrMeasure1(String strMeasure1) {
        this.strMeasure1 = strMeasure1;
    }

    public void setStrMeasure18(String strMeasure18) {
        this.strMeasure18 = strMeasure18;
    }

    public void setStrMeasure2(String strMeasure2) {
        this.strMeasure2 = strMeasure2;
    }

    public void setStrMeasure19(String strMeasure19) {
        this.strMeasure19 = strMeasure19;
    }

    public void setStrMeasure16(String strMeasure16) {
        this.strMeasure16 = strMeasure16;
    }

    public void setStrMeasure17(String strMeasure17) {
        this.strMeasure17 = strMeasure17;
    }

    public void setStrMeasure14(String strMeasure14) {
        this.strMeasure14 = strMeasure14;
    }

    public void setStrMeasure15(String strMeasure15) {
        this.strMeasure15 = strMeasure15;
    }

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

    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(String idMeal) {
        this.idMeal = idMeal;
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

    public String getStrIngredient19() {
        return strIngredient19;
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

    public static MealsItem fromMap(Map<String, Object> map) {
        MealsItem mealsItem = new MealsItem();

        // Set the primary keys
        mealsItem.setIdMeal((String) map.get("idMeal"));
        mealsItem.setUID((String) map.get("UID"));

        // Set ingredients
        mealsItem.setStrIngredient1((String) map.get("strIngredient1"));
        mealsItem.setStrIngredient2((String) map.get("strIngredient2"));
        mealsItem.setStrIngredient3((String) map.get("strIngredient3"));
        mealsItem.setStrIngredient4((String) map.get("strIngredient4"));
        mealsItem.setStrIngredient5((String) map.get("strIngredient5"));
        mealsItem.setStrIngredient6((String) map.get("strIngredient6"));
        mealsItem.setStrIngredient7((String) map.get("strIngredient7"));
        mealsItem.setStrIngredient8((String) map.get("strIngredient8"));
        mealsItem.setStrIngredient9((String) map.get("strIngredient9"));
        mealsItem.setStrIngredient10((String) map.get("strIngredient10"));
        mealsItem.setStrIngredient11((String) map.get("strIngredient11"));
        mealsItem.setStrIngredient12((String) map.get("strIngredient12"));
        mealsItem.setStrIngredient13((String) map.get("strIngredient13"));
        mealsItem.setStrIngredient14((String) map.get("strIngredient14"));
        mealsItem.setStrIngredient15((String) map.get("strIngredient15"));
        mealsItem.setStrIngredient16((String) map.get("strIngredient16"));
        mealsItem.setStrIngredient17((String) map.get("strIngredient17"));
        mealsItem.setStrIngredient18((String) map.get("strIngredient18"));
        mealsItem.setStrIngredient19((String) map.get("strIngredient19"));
        mealsItem.setStrIngredient20((String) map.get("strIngredient20"));

        // Set measurements
        mealsItem.setStrMeasure1((String) map.get("strMeasure1"));
        mealsItem.setStrMeasure2((String) map.get("strMeasure2"));
        mealsItem.setStrMeasure3((String) map.get("strMeasure3"));
        mealsItem.setStrMeasure4((String) map.get("strMeasure4"));
        mealsItem.setStrMeasure5((String) map.get("strMeasure5"));
        mealsItem.setStrMeasure6((String) map.get("strMeasure6"));
        mealsItem.setStrMeasure7((String) map.get("strMeasure7"));
        mealsItem.setStrMeasure8((String) map.get("strMeasure8"));
        mealsItem.setStrMeasure9((String) map.get("strMeasure9"));
        mealsItem.setStrMeasure10((String) map.get("strMeasure10"));
        mealsItem.setStrMeasure11((String) map.get("strMeasure11"));
        mealsItem.setStrMeasure12((String) map.get("strMeasure12"));
        mealsItem.setStrMeasure13((String) map.get("strMeasure13"));
        mealsItem.setStrMeasure14((String) map.get("strMeasure14"));
        mealsItem.setStrMeasure15((String) map.get("strMeasure15"));
        mealsItem.setStrMeasure16((String) map.get("strMeasure16"));
        mealsItem.setStrMeasure17((String) map.get("strMeasure17"));
        mealsItem.setStrMeasure18((String) map.get("strMeasure18"));
        mealsItem.setStrMeasure19((String) map.get("strMeasure19"));
        mealsItem.setStrMeasure20((String) map.get("strMeasure20"));

        // Set other details
        mealsItem.setStrMeal((String) map.get("strMeal"));
        mealsItem.setStrMealThumb((String) map.get("strMealThumb"));
        mealsItem.setStrYoutube((String) map.get("strYoutube"));
        mealsItem.setStrInstructions((String) map.get("strInstructions"));
        mealsItem.setStrCategory((String) map.get("strCategory"));
        mealsItem.setStrArea((String) map.get("strArea"));
        mealsItem.setStrSource((String) map.get("strSource"));

        return mealsItem;
    }


    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();

        // Add primary keys
        map.put("idMeal", getIdMeal());
        map.put("UID", getUID());

        // Add ingredients
        map.put("strIngredient1", getStrIngredient1());
        map.put("strIngredient2", getStrIngredient2());
        map.put("strIngredient3", getStrIngredient3());
        map.put("strIngredient4", getStrIngredient4());
        map.put("strIngredient5", getStrIngredient5());
        map.put("strIngredient6", getStrIngredient6());
        map.put("strIngredient7", getStrIngredient7());
        map.put("strIngredient8", getStrIngredient8());
        map.put("strIngredient9", getStrIngredient9());
        map.put("strIngredient10", getStrIngredient10());
        map.put("strIngredient11", getStrIngredient11());
        map.put("strIngredient12", getStrIngredient12());
        map.put("strIngredient13", getStrIngredient13());
        map.put("strIngredient14", getStrIngredient14());
        map.put("strIngredient15", getStrIngredient15());
        map.put("strIngredient16", getStrIngredient16());
        map.put("strIngredient17", getStrIngredient17());
        map.put("strIngredient18", getStrIngredient18());
        map.put("strIngredient19", getStrIngredient19());
        map.put("strIngredient20", getStrIngredient20());

        // Add measurements
        map.put("strMeasure1", getStrMeasure1());
        map.put("strMeasure2", getStrMeasure2());
        map.put("strMeasure3", getStrMeasure3());
        map.put("strMeasure4", getStrMeasure4());
        map.put("strMeasure5", getStrMeasure5());
        map.put("strMeasure6", getStrMeasure6());
        map.put("strMeasure7", getStrMeasure7());
        map.put("strMeasure8", getStrMeasure8());
        map.put("strMeasure9", getStrMeasure9());
        map.put("strMeasure10", getStrMeasure10());
        map.put("strMeasure11", getStrMeasure11());
        map.put("strMeasure12", getStrMeasure12());
        map.put("strMeasure13", getStrMeasure13());
        map.put("strMeasure14", getStrMeasure14());
        map.put("strMeasure15", getStrMeasure15());
        map.put("strMeasure16", getStrMeasure16());
        map.put("strMeasure17", getStrMeasure17());
        map.put("strMeasure18", getStrMeasure18());
        map.put("strMeasure19", getStrMeasure19());
        map.put("strMeasure20", getStrMeasure20());

        // Add other details
        map.put("strMeal", getStrMeal());
        map.put("strMealThumb", getStrMealThumb());
        map.put("strYoutube", getStrYoutube());
        map.put("strInstructions", getStrInstructions());
        map.put("strCategory", getStrCategory());
        map.put("strArea", getStrArea());
        map.put("strSource", getStrSource());

        return map;
    }

}
