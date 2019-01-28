package com.aquaheyseller.ui.presenters;

import android.content.Context;

import com.aquaheyseller.R;
import com.aquaheyseller.ui.presenters.operations.IFrgtPswd;
import com.aquaheyseller.utils.Utils;

public class ForgotPswdPresenter extends BasePresenter {
    private Context mContext;
    private IFrgtPswd iFrgtPswd;

    public ForgotPswdPresenter(Context context, IFrgtPswd iFrgtPswd) {
        super(context);
        this.iFrgtPswd = iFrgtPswd;
        mContext = context;
    }

    public void validMobileNo(String mobileNo) {
        if (mobileNo.equals("") || mobileNo.isEmpty()) {
            iFrgtPswd.onValidationError(mContext.getString(R.string.please_enter_valid_mobile_number));
        } else if (mobileNo.length() < 10) {
            iFrgtPswd.onValidationError(mContext.getString(R.string.please_enter_valid_mobile_number));
        } else {
            iFrgtPswd.submitMobile();
        }
    }
}
