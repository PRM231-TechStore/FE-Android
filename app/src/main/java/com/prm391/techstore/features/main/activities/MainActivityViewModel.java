package com.prm391.techstore.features.main.activities;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainActivityViewModel extends ViewModel {
    private final MutableLiveData<String> searchTerm = new MutableLiveData<>();
    private final MutableLiveData<String> sortBy = new MutableLiveData<>();
    private final MutableLiveData<String> sortOrder = new MutableLiveData<>();
    private final MutableLiveData<String> minPrice = new MutableLiveData<>();
    private final MutableLiveData<String> maxPrice = new MutableLiveData<>();
    private final MutableLiveData<String> pageNumber = new MutableLiveData<>();
    private final MutableLiveData<String> pageSize = new MutableLiveData<>();
    private final MutableLiveData<String> label = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> isLastPage = new MutableLiveData<>(false);
    public void ClearAllSearchCategories(){
        searchTerm.setValue(null);
        sortBy.setValue(null);
        sortOrder.setValue(null);
        minPrice.setValue(null);
        maxPrice.setValue(null);
        pageNumber.setValue(null);
        pageSize.setValue(null);
        label.setValue(null);
        isLoading.setValue(false);
        isLastPage.setValue(false);
    }
}
