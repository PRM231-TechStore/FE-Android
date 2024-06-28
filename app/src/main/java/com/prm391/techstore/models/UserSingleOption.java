package com.prm391.techstore.models;


import android.content.DialogInterface;
import android.view.View;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserSingleOption {
    private String name;
    private String description;
    private View.OnClickListener onClickListener;
}
