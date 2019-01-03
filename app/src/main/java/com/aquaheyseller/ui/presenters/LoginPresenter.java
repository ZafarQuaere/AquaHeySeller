package com.aquaheyseller.ui.presenters;

import android.content.Context;

import com.aquaheyseller.ui.presenters.operations.ILogin;

public class LoginPresenter extends BasePresenter {

    ILogin mLogin;
    public LoginPresenter(Context context, ILogin iLogin) {
        super(context);
        mLogin = iLogin;
    }

    public void validateUsername(String s) {

    }

    public void validatePassword(String password) {

    }
}
