package com.prm391.techstore.constants;

import com.prm391.techstore.models.UserSingleOption;

public class UserFragmentConstants {
    public static final UserSingleOption[] userSingleOptionList = {
            new UserSingleOption("My Orders","View your previous and ongoing orders."),
            new UserSingleOption("Change my Password","Updates your old password with a new one."),
            new UserSingleOption("Change my Shipping Address","Having changed your" +
                    " place of residence recently? Then this is your go-to setting."),
    };
    public static final String WARNING_TITLE = "Warning";
    public static final String LOGOUT_PROMPT = "Do you really want to log out from the application?";
}
