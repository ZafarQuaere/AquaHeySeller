package com.aquaheyseller.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.aquaheyseller.R;
import com.aquaheyseller.ui.presenters.OtpPresenter;
import com.aquaheyseller.ui.presenters.operations.IOtp;
import com.aquaheyseller.utils.LogUtils;


public class EnterOTPActivity extends BaseActivity<OtpPresenter> implements IOtp {

    private Context mContext;

    @Override
    protected OtpPresenter initPresenter() {
        return new OtpPresenter(this, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        mContext = this;

        findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validationField();
            }
        });

    }


    private void validationField() {
        EditText editOTP = (EditText) findViewById(R.id.editOTP);
        getPresenter().validateOtp(editOTP.getText().toString().trim());
    }

    @Override
    public void submitOtp() {
        startActivity(new Intent(EnterOTPActivity.this, LoginActivity.class));
        finishAffinity();
    }

    @Override
    public void onValidationError(String msg) {
        LogUtils.showErrorDialog(mContext, getString(R.string.ok), msg);
    }

    public void resendOTP(View view) {

    }

    public void editNumber(View view) {

    }
}
