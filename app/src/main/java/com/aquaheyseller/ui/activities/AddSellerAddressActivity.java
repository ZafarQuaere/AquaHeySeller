package com.aquaheyseller.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.aquaheyseller.R;
import com.aquaheyseller.network_call.request_model.AddressData;
import com.aquaheyseller.ui.presenters.AddressPresenter;
import com.aquaheyseller.ui.presenters.operations.ISellerAddress;
import com.aquaheyseller.utils.LogUtils;
import com.aquaheyseller.utils.Utils;


public class AddSellerAddressActivity extends BaseActivity<AddressPresenter> implements ISellerAddress {

    private Context mContext;

    @Override
    protected AddressPresenter initPresenter() {
        return new AddressPresenter(this, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_seller_address);
        mContext = this;
        Utils.updateActionBar(this,new AddSellerAddressActivity().getClass().getSimpleName(),getString(R.string.save_address),
                null,null);
        findViewById(R.id.btnSaveAddress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startActivity(new Intent(RegisterActivity.this, EnterOTPActivity.class));
                validateFields();
            }
        });

    }

    private void validateFields() {
        EditText editAddress = (EditText) findViewById(R.id.editAddress);
        EditText editCity = (EditText) findViewById(R.id.editCity);
        EditText editState = (EditText) findViewById(R.id.editState);
        EditText editPincode = (EditText) findViewById(R.id.editPincode);

        getPresenter().validateFields(editAddress.getText().toString().trim(),
                editCity.getText().toString().trim(),
                editState.getText().toString().trim(),
                editPincode.getText().toString().trim());
    }

    @Override
    public void callApi(AddressData addressData) {
        //openProgressDialog();
        getPresenter().callAddressApi(addressData);

    }

    @Override
    public void saveAddress() {
        // hideProgressDialog();
        startActivity(new Intent(AddSellerAddressActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onValidationError(String msg) {
        LogUtils.showErrorDialog(mContext, getString(R.string.ok), msg);
    }
}
