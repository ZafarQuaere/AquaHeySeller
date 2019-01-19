package com.aquaheyseller.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.aquaheyseller.R;
import com.aquaheyseller.ui.presenters.BasePresenter;
import com.aquaheyseller.ui.presenters.OtpPresenter;
import com.aquaheyseller.ui.presenters.operations.IOtp;


public class EnterOTPActivity extends BaseActivity<OtpPresenter> implements IOtp {


    private LinearLayout lytTop;
    private String userId;
    private String password;
    private Context mContext;


    @Override
    protected OtpPresenter initPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        mContext = this;

        initUI();

    }

    private void initUI() {

    }

    private void validationField() {

    }

    @Override
    public void submitOtp() {

    }

    @Override
    public void onValidationError(String msg) {

    }
}
