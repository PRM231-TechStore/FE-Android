package com.prm391.techstore.utils;

import com.prm391.techstore.constants.SearchByConstants;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyUtils {
    public static String DecimalToVND(double amount){
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        df.applyPattern(SearchByConstants.PRICE_FORMAT);

        // Format the amount
        return df.format(amount);
    }
}
