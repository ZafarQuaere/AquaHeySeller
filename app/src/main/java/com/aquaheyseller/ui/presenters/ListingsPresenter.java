package com.aquaheyseller.ui.presenters;

import android.content.Context;
import android.os.Handler;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.aquaheyseller.R;
import com.aquaheyseller.network_call.MyJsonObjectRequest;
import com.aquaheyseller.network_call.request_model.AddressData;
import com.aquaheyseller.ui.presenters.operations.IFragListing;
import com.aquaheyseller.utils.AppConstant;
import com.aquaheyseller.utils.AppController;
import com.aquaheyseller.utils.LogUtils;
import com.aquaheyseller.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class ListingsPresenter extends BaseFragmentPresenter {
    private Context mContext;
    private IFragListing mAddProduct;

    public ListingsPresenter(Context context, IFragListing iSellerAddress) {
        super(context);
        mAddProduct = iSellerAddress;
        mContext = context;
    }

    public void validateFields(String address, String city, String state, String pincode) {
        if (address.equals("") || address.isEmpty()) {
            mAddProduct.onValidationError(mContext.getString(R.string.please_enter_address));
        } else if (city.equals("") || city.isEmpty()) {
            mAddProduct.onValidationError(mContext.getString(R.string.please_enter_city_name));
        } else if (state.equals("") || state.isEmpty()) {
            mAddProduct.onValidationError(mContext.getString(R.string.please_enter_state));
        } else if (pincode.equals("") || pincode.isEmpty() || pincode.length() < 6 || pincode.length() > 6) {
            mAddProduct.onValidationError(mContext.getString(R.string.please_enter_valid_pincode));
        } else {
            if (NetworkUtils.isNetworkEnabled(mContext)) {
                AddressData adress = new AddressData(address, state, city, pincode, "latitude", "longitude");
               // mAddProduct.callApi(adress);
            } else {
                mAddProduct.onValidationError(mContext.getString(R.string.please_check_your_network_connection));
            }
        }
    }

    public void callAddressApi(final AddressData addressData) {
        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("userId", "20");
            requestObject.put("addressOne", addressData.getAddress());
            requestObject.put("city", addressData.getCity());
            requestObject.put("state", addressData.getState());
            requestObject.put("pincode", addressData.getPincode());
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
                LogUtils.DEBUG("AddressData Response ::" + response.toString());

                // mAddProduct.saveAddress();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                LogUtils.DEBUG("AddressData Error ::" + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(objectRequest, "AddressData");
    }

    public void starTestDialog() {

        openProgressDialog();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideProgressDialog();
            }
        },5000);

    }
}
