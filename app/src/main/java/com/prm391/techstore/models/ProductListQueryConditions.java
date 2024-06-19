package com.prm391.techstore.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import retrofit2.http.Query;

@Getter
@AllArgsConstructor
public class ProductListQueryConditions {
    private String searchTerm;
    private String sortBy;
    private String sortOrder;
    private String minPrice;
    private String maxPrice;
    private String pageNumber;
    private String pageSize;
}
