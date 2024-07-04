package com.prm391.techstore.constants;

import com.prm391.techstore.models.UserOrder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class UserOrdersFragmentConstants {
    public static final String USER_ORDERS_TITLE = "My Orders";
    public static final List<UserOrder> TEMP_USER_ORDERS_LIST = Arrays.asList(
            new UserOrder("abc", 1, 1, "def", LocalDate.now()),
            new UserOrder("abc", 1, 1, "def", LocalDate.now()),
            new UserOrder("abc", 1, 1, "def", LocalDate.now()),
            new UserOrder("abc", 1, 1, "def", LocalDate.now()),
            new UserOrder("abc", 1, 1, "def", LocalDate.now())
    );
}
