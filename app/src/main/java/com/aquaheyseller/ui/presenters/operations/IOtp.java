package com.aquaheyseller.ui.presenters.operations;

public interface IOtp {

    void submitOtp();
    void onValidationError(String msg);
    void callSubmitOtpApi(String otp);
}
