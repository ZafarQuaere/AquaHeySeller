package com.aquaheyseller.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.aquaheyseller.R;
import com.aquaheyseller.network_call.request.Register;
import com.aquaheyseller.ui.presenters.RegisterPresenter;
import com.aquaheyseller.ui.presenters.operations.IRegister;
import com.aquaheyseller.utils.LogUtils;


public class RegisterActivity extends BaseActivity<RegisterPresenter> implements IRegister {

    private Context mContext;

    @Override
    protected RegisterPresenter initPresenter() {
        return new RegisterPresenter(this,this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = this;

        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(RegisterActivity.this, EnterOTPActivity.class));
                validateFields();
            }
        });

    }

    private void validateFields() {
        EditText editName = (EditText) findViewById(R.id.editName);
        EditText editMobile = (EditText) findViewById(R.id.editMobile);
        EditText editEmailId = (EditText) findViewById(R.id.editEmailId);
        EditText editPassword = (EditText) findViewById(R.id.editPassword);
        EditText editConfirmPassword = (EditText) findViewById(R.id.editConfirmPassword);

        getPresenter().validateFields(editName.getText().toString().trim(),
                editMobile.getText().toString().trim(),
                editEmailId.getText().toString().trim(),
                editPassword.getText().toString().trim(),
                editConfirmPassword.getText().toString().trim());
    }

    @Override
    public void callApi(Register register) {
        //openProgressDialog();
        getPresenter().callRegisterApi(register);

    }

    @Override
    public void doRegister() {
       // hideProgressDialog();
        startActivity(new Intent(RegisterActivity.this, EnterOTPActivity.class));
        finish();
    }

    @Override
    public void onValidationError(String msg) {
        LogUtils.showErrorDialog(mContext,getString(R.string.ok),msg);
    }
}
