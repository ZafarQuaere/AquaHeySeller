package com.aquaheyseller.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aquaheyseller.R;
import com.aquaheyseller.ui.presenters.BasePresenter;
import com.aquaheyseller.ui.presenters.LoginPresenter;
import com.aquaheyseller.ui.presenters.operations.ILogin;
import com.aquaheyseller.utils.Utils;


public class LoginActivity extends BaseActivity<LoginPresenter> implements ILogin {

    private EditText editUserName;
    private EditText editPassword;
    private RelativeLayout lytParent;
    private LinearLayout lytTop;
    private Button btnLogin;
    private Button btnSignUp;
    private String userId;
    private String password;
    private Context mContext;


    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter(this,this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        /*if (Utils.isLoggedIn(mContext)){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }else {*/
            initUI();
       // }
    }

    private void initUI() {
        editUserName = (EditText) findViewById(R.id.editUserName);
        editPassword = (EditText) findViewById(R.id.editPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        lytParent = (RelativeLayout) findViewById(R.id.lytParent);
        lytTop = (LinearLayout) findViewById(R.id.lytTop);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validationField();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
        lytParent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Utils.hideKeyboard(LoginActivity.this,lytTop);
            }
        });
    }

    private void validationField() {

        userId = editUserName.getText().toString();
        password = editPassword.getText().toString();
        getPresenter().validateUsername(userId);
        getPresenter().validatePassword(password);

        if (userId.equals("") || userId.equals(null)) {
            Toast.makeText(LoginActivity.this, "please enter username", Toast.LENGTH_SHORT).show();
        } else if (password.equals("") || password.equals(null)) {
            Toast.makeText(LoginActivity.this, "please enter password", Toast.LENGTH_SHORT).show();
        } else {
            Utils.setLoggedIn(mContext,true);
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

    }

    @Override
    public void doLogin() {

    }

    @Override
    public boolean validateUserName() {
        return false;
    }

    @Override
    public boolean validatePassword() {
        return false;
    }
}
