package com.iti.flex_meals.db.retrofit.pojo.countries;

import java.util.List;

public class AllCountryResponse {
    private List<CountryItem> meals;

    public List<CountryItem> getAllCountries() {
        return meals;
    }
}