package com.aquaheyseller.ui.presenters;

import android.content.Context;

import com.aquaheyseller.R;
import com.aquaheyseller.ui.presenters.operations.ILogin;

public class LoginPresenter extends BasePresenter {
    Context mContext;
    ILogin mLogin;

    public LoginPresenter(Context context, ILogin iLogin) {
        super(context);
        mLogin = iLogin;
        mContext = context;
    }

    public void validateUsernamePassword(String userId, String password) {
        if (userId.equals("") || userId.isEmpty()) {
            mLogin.onValidationError(mContext.getString(R.string.please_enter_username));
        } else if (password.equals("") || password.isEmpty()) {
            mLogin.onValidationError(mContext.getString(R.string.please_enter_password));
        } else if (password.length() < 6) {
            mLogin.onValidationError(mContext.getString(R.string.password_must_have_atleast_6_character));
        } else {
            mLogin.doLogin();
        }
    }
}
