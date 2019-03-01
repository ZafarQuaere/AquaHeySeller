package com.aquaheyseller.ui.presenters;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.aquaheyseller.R;
import com.aquaheyseller.network_call.MyJsonObjectRequest;
import com.aquaheyseller.network_call.request_model.Address;
import com.aquaheyseller.ui.presenters.operations.ISellerAddress;
import com.aquaheyseller.utils.AppConstant;
import com.aquaheyseller.utils.AppController;
import com.aquaheyseller.utils.LogUtils;
import com.aquaheyseller.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class AddressPresenter extends BasePresenter {
    private Context mContext;
    private ISellerAddress mSellerAddress;

    public AddressPresenter(Context context, ISellerAddress iSellerAddress) {
        super(context);
        mSellerAddress = iSellerAddress;
        mContext = context;
    }

    public void validateFields(String address, String city, String state, String pincode) {
        if (address.equals("") || address.isEmpty()) {
            mSellerAddress.onValidationError(mContext.getString(R.string.please_enter_address));
        } else if (city.equals("") || city.isEmpty()) {
            mSellerAddress.onValidationError(mContext.getString(R.string.please_enter_city_name));
        } else if (state.equals("") || state.isEmpty()) {
            mSellerAddress.onValidationError(mContext.getString(R.string.please_enter_state));
        } else if (pincode.equals("") || pincode.isEmpty() || pincode.length() < 6 || pincode.length() > 6) {
            mSellerAddress.onValidationError(mContext.getString(R.string.please_enter_valid_pincode));
        } else {
            if (NetworkUtils.isNetworkEnabled(mContext)) {
                Address adress = new Address(address, state, city, pincode, "latitude", "longitude");
                mSellerAddress.callApi(adress);
            } else {
                mSellerAddress.onValidationError(mContext.getString(R.string.please_check_your_network_connection));
            }
        }
    }

    public void callAddressApi(final Address address) {
        showDialog("Address....", "Saving Address");
        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("userId", "20");
            requestObject.put("addressOne", address.getAddress());
            requestObject.put("city", address.getCity());
            requestObject.put("state", address.getState());
            requestObject.put("pincode", address.getPincode());
             requestObject.put("latitude", "256535");
             requestObject.put("longitude", "256535");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = AppConstant.URL_BASE + AppConstant.URL_DEALER_ADDRESS;
        LogUtils.DEBUG("URL : " + url + "\nRequest Body ::" + requestObject.toString());
        MyJsonObjectRequest objectRequest = new MyJsonObjectRequest(mContext, Request.Method.POST, url, requestObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtils.DEBUG("Address Response ::" + response.toString());
                dismissDialog();
                 mSellerAddress.saveAddress();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissDialog();
                LogUtils.DEBUG("Address Error ::" + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(objectRequest, "Address");
    }
}
