package com.prm391.techstore.constants;

import com.prm391.techstore.models.Category;


public class SearchByConstants {
    public static final Category[] SORT_BY_CATEGORIES = {
            new Category("Sort by...",null),
            new Category("Price (ascending)",new String[]{"price","ASC"}),
            new Category("Price (descending)",new String[]{"price","DESC"}),
            new Category("Name (ascending)",new String[]{"name","ASC"}),
            new Category("Name (descending)",new String[]{"name","DESC"}),
    };
    public static final Category[] FILTER_BY_CATEGORIES = {
            new Category("Filter by...",null),
            new Category("Price","price"),
            new Category("Name","name")
    };
}
