package com.aquaheyseller.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.aquaheyseller.R;
import com.aquaheyseller.ui.presenters.ForgotPswdPresenter;
import com.aquaheyseller.ui.presenters.OtpPresenter;
import com.aquaheyseller.ui.presenters.operations.IFrgtPswd;
import com.aquaheyseller.ui.presenters.operations.IOtp;
import com.aquaheyseller.utils.LogUtils;


public class ForgetPswdActivity extends BaseActivity<ForgotPswdPresenter> implements IFrgtPswd {

    private Context mContext;


    @Override
    protected ForgotPswdPresenter initPresenter() {
        return  new ForgotPswdPresenter(this,this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pswd);
        mContext = this;

        findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validationField();
            }
        });

    }

    private void validationField() {
        EditText editMobile = (EditText)findViewById(R.id.editMobile);
        getPresenter().validMobileNo(editMobile.getText().toString().trim());
    }


    @Override
    public void submitMobile() {
        startActivity(new Intent(ForgetPswdActivity.this,EnterOTPActivity.class));
        finish();
    }

    @Override
    public void onValidationError(String msg) {
        LogUtils.showErrorDialog(mContext, getString(R.string.ok), msg);
    }

    @Override
    public void callSubmitMobileApi(String mobile) {
        getPresenter().callSubmitMobileApi(mobile);
    }
}
