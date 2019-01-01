package com.aquaheyseller.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.aquaheyseller.R;
import com.aquaheyseller.ui.presenters.BasePresenter;


public class RegisterActivity extends BaseActivity {


    private LinearLayout lytTop;
    private String userId;
    private String password;
    private Context mContext;


    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = this;
        /*if (Utils.isLoggedIn(mContext)){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }else {*/
        initUI();
        // }
    }

    private void initUI() {

    }

    private void validationField() {

    }

}
