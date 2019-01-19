package com.aquaheyseller.ui.presenters;

import android.content.Context;

import com.aquaheyseller.R;
import com.aquaheyseller.ui.presenters.operations.IRegister;
import com.aquaheyseller.utils.Utils;

public class RegisterPresenter extends BasePresenter {
    private Context mContext;
    private IRegister mRegister;

    public RegisterPresenter(Context context, IRegister iRegister) {
        super(context);
        mRegister = iRegister;
        mContext = context;
    }

    public void validateFields(String name, String mobileNo,String email,String pswd,String confmPswd) {
        if (name.equals("") || name.isEmpty()) {
            mRegister.onValidationError(mContext.getString(R.string.please_enter_username));
        } else if (mobileNo.equals("") || mobileNo.isEmpty() || mobileNo.length() < 10) {
            mRegister.onValidationError(mContext.getString(R.string.please_enter_valid_mobile_number));
        } else if (!Utils.isValidEmail(email)) {
            mRegister.onValidationError(mContext.getString(R.string.please_enter_valid_email));
        } else if (pswd.equals("") || pswd.isEmpty() ) {
            mRegister.onValidationError(mContext.getString(R.string.please_enter_password));
        } else if (pswd.length() < 6) {
            mRegister.onValidationError(mContext.getString(R.string.password_must_have_atleast_6_character));
        } else if (!confmPswd.equals(pswd) ) {
            mRegister.onValidationError(mContext.getString(R.string.please_enter_same_pswd));
        } else {
            mRegister.doRegister();
        }
    }
}
