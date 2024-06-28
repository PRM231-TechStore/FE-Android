package com.prm391.techstore.constants;

import androidx.fragment.app.Fragment;

import com.prm391.techstore.R;
import com.prm391.techstore.features.orders.UserOrdersFragment;
import com.prm391.techstore.features.user.on_click_listeners.GoToUserOrdersPageOnClickListener;
import com.prm391.techstore.models.UserSingleOption;

import lombok.Getter;

@Getter
public class UserFragmentConstants {
    private Fragment userFragment;
    private UserSingleOption[] userSingleOptionList;
    private String WARNING_TITLE;
    private String LOGOUT_PROMPT;
    public UserFragmentConstants(Fragment userFragment){
        this.userFragment = userFragment;
        InitializeClassVariables();

    }
    private void InitializeClassVariables(){
        WARNING_TITLE = "Warning";
        LOGOUT_PROMPT = "Do you really want to log out from the application?";
        InitializeUserSingleOptionList();
    }
    private void InitializeUserSingleOptionList(){
        this.userSingleOptionList = new UserSingleOption[]{
                new UserSingleOption("My Orders","View your previous and ongoing orders.",
                        new GoToUserOrdersPageOnClickListener(R.id.mainFrameLayout
                                ,new UserOrdersFragment()
                                ,userFragment.getParentFragmentManager())),
//            new UserSingleOption("Change my Password","Updates your old password with a new one."),
//            new UserSingleOption("Change my Shipping Address","Having changed your" +
//                    " place of residence recently? Then this is your go-to setting."),
        };
    }
}
