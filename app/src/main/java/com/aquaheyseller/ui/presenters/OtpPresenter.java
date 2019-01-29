package com.aquaheyseller.ui.presenters;

import android.content.Context;

import com.aquaheyseller.R;
import com.aquaheyseller.ui.presenters.operations.ILogin;
import com.aquaheyseller.ui.presenters.operations.IOtp;
import com.aquaheyseller.utils.Utils;

public class OtpPresenter extends BasePresenter {
    private Context mContext;
    private IOtp iOtp;

    public OtpPresenter(Context context, IOtp iOtp) {
        super(context);
        this.iOtp = iOtp;
        mContext = context;
    }

    public void validateOtp(String otp) {
        if (otp.equals("") || otp.isEmpty()) {
            iOtp.onValidationError(mContext.getString(R.string.please_enter_otp));
        } else if (otp.length() < 4) {
            iOtp.onValidationError(mContext.getString(R.string.enter_valid_otp));
        } else {
            iOtp.submitOtp();
        }
    }
}
