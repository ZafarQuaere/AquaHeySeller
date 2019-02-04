package com.aquaheyseller.ui.presenters;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.aquaheyseller.R;
import com.aquaheyseller.network_call.MyJsonObjectRequest;
import com.aquaheyseller.ui.presenters.operations.IFrgtPswd;
import com.aquaheyseller.utils.AppConstant;
import com.aquaheyseller.utils.AppController;
import com.aquaheyseller.utils.LogUtils;
import com.aquaheyseller.utils.NetworkUtils;
import com.aquaheyseller.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPswdPresenter extends BasePresenter {
    private Context mContext;
    private IFrgtPswd iFrgtPswd;

    public ForgotPswdPresenter(Context context, IFrgtPswd iFrgtPswd) {
        super(context);
        this.iFrgtPswd = iFrgtPswd;
        mContext = context;
    }

    public void validMobileNo(String mobileNo) {
        if (mobileNo.equals("") || mobileNo.isEmpty()) {
            iFrgtPswd.onValidationError(mContext.getString(R.string.please_enter_valid_mobile_number));
        } else if (mobileNo.length() < 10) {
            iFrgtPswd.onValidationError(mContext.getString(R.string.please_enter_valid_mobile_number));
        } else {
            if (NetworkUtils.isNetworkEnabled(mContext)) {
               // iFrgtPswd.callSubmitMobileApi(mobileNo);
                iFrgtPswd.submitMobile();
            }else {
                iFrgtPswd.onValidationError(mContext.getString(R.string.please_check_your_network_connection));
            }
        }
    }

    public void callSubmitMobileApi(String mobile) {
        showDialog(" Please wait....", "Forgot Password");
        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("mobile", mobile);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = AppConstant.baseUrl + AppConstant.loginUrl;
        LogUtils.DEBUG("URL : " + url + "\nRequest Body ::" + requestObject.toString());
        MyJsonObjectRequest objectRequest = new MyJsonObjectRequest(mContext, Request.Method.POST, url, requestObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
               /* Gson gson = new GsonBuilder().create();
                String string = gson.toJson(response);*/
                LogUtils.DEBUG("ForgotPassword Response ::" + response.toString());
                dismissDialog();
                iFrgtPswd.submitMobile();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissDialog();
                LogUtils.DEBUG("ForgotPassword Error ::" + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(objectRequest, "ForgotPassword");
    }
}
