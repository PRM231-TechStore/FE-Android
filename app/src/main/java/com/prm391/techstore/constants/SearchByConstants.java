package com.prm391.techstore.constants;

import com.prm391.techstore.models.Category;


public class SearchByConstants {
    public static final Category[] SORT_BY_CATEGORIES = {
            new Category("Price (ascending)",new String[]{"price","ASC"}),
            new Category("Price (descending)",new String[]{"price","DESC"}),
            new Category("Name (ascending)",new String[]{"name","ASC"}),
            new Category("Name (descending)",new String[]{"name","DESC"}),
    };
    public static final Category[] PRICE_CATEGORIES = {
            new Category("From 10.000.000 VND to 20.000.000 VND",new float[]{10000000,20000000}),
            new Category("From 20.000.000 VND to 40.000.000 VND",new float[]{20000000,40000000}),
            new Category("From 40.000.000 VND to 80.000.000 VND",new float[]{40000000,80000000}),
            new Category("Over 80.000.000 VND",new float[]{80000000,999999999}),
    };
    public static final float MIN_PRICE_ON_SLIDER = 10000000f;
    public static final float MAX_PRICE_ON_SLIDER = 100000000f;
    public static final String PRICE_FORMAT = "#,##0.00";
    public static final String INVALID_PRICE_RANGE = "You haven't selected a price range! Please try again.";

}
