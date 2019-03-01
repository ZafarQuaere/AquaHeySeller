package com.aquaheyseller.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.aquaheyseller.R;
import com.aquaheyseller.ui.presenters.AddSellerPresenter;
import com.aquaheyseller.ui.presenters.operations.IAddSeller;
import com.aquaheyseller.utils.LogUtils;


public class AddSellerActivity extends BaseActivity<AddSellerPresenter> implements IAddSeller {

    private Context mContext;

    @Override
    protected AddSellerPresenter initPresenter() {
        return new AddSellerPresenter(this, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_seller);
        mContext = this;
        findViewById(R.id.btnAddSeller).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields();
            }
        });

    }

    private void validateFields() {
        EditText editSellerName = (EditText) findViewById(R.id.editSellerName);
        EditText editSellerMobile = (EditText) findViewById(R.id.editSellerMobile);


        getPresenter().validateFields(editSellerName.getText().toString().trim(),
                editSellerMobile.getText().toString().trim());
    }

    @Override
    public void callApi(String dName, String mobile) {
        startActivity(new Intent(AddSellerActivity.this, RegisterActivity.class));
        //openProgressDialog();
        getPresenter().callAddSellerApi(dName, mobile);

    }

    @Override
    public void addSeller() {
        // hideProgressDialog();
        startActivity(new Intent(AddSellerActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onValidationError(String msg) {
        LogUtils.showErrorDialog(mContext, getString(R.string.ok), msg);
    }
}
