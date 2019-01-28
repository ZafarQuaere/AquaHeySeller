package com.aquaheyseller.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aquaheyseller.R;
import com.aquaheyseller.ui.presenters.LoginPresenter;
import com.aquaheyseller.ui.presenters.operations.ILogin;
import com.aquaheyseller.utils.LogUtils;
import com.aquaheyseller.utils.Utils;


public class LoginActivity extends BaseActivity<LoginPresenter> implements ILogin, View.OnClickListener {

    private EditText editUserName;
    private EditText editPassword;
    private LinearLayout lytTop;
    private Context mContext;
    private TextView textForgetPswd;


    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter(this, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        if (Utils.isLoggedIn(mContext)) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else {
            initUI();
        }
    }

    private void initUI() {
        editUserName = (EditText) findViewById(R.id.editUserName);
        editPassword = (EditText) findViewById(R.id.editPassword);
        textForgetPswd = (TextView) findViewById(R.id.textForgetPswd);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        Button btnSignUp = (Button) findViewById(R.id.btnSignUp);
        RelativeLayout lytParent = (RelativeLayout) findViewById(R.id.lytParent);
        lytTop = (LinearLayout) findViewById(R.id.lytTop);
        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        lytParent.setOnClickListener(this);
        textForgetPswd.setOnClickListener(this);

    }

    private void validationField() {
        String userId = editUserName.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        getPresenter().validateUsernamePassword(userId, password);
    }

    @Override
    public void doLogin() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onValidationError(String msg) {
        LogUtils.showErrorDialog(mContext, getString(R.string.ok), msg);
    }

    @Override
    public void callLoginApi(String userId, String password) {
        getPresenter().callApi(userId, password);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textForgetPswd:
                startActivity(new Intent(LoginActivity.this, ForgetPswdActivity.class));
                break;

            case R.id.btnLogin:
                validationField();
                break;

            case R.id.btnSignUp:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;

            case R.id.lytParent:
                Utils.hideKeyboard(LoginActivity.this, lytTop);
                break;
        }
    }
}
