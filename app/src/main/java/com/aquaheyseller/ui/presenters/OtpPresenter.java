package com.aquaheyseller.ui.presenters;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.aquaheyseller.R;
import com.aquaheyseller.network_call.MyJsonObjectRequest;
import com.aquaheyseller.ui.presenters.operations.ILogin;
import com.aquaheyseller.ui.presenters.operations.IOtp;
import com.aquaheyseller.utils.AppConstant;
import com.aquaheyseller.utils.AppController;
import com.aquaheyseller.utils.LogUtils;
import com.aquaheyseller.utils.NetworkUtils;
import com.aquaheyseller.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class OtpPresenter extends BasePresenter {
    private Context mContext;
    private IOtp iOtp;

    public OtpPresenter(Context context, IOtp iOtp) {
        super(context);
        this.iOtp = iOtp;
        mContext = context;
    }

    public void validateOtp(String otp) {
        if (otp.equals("") || otp.isEmpty()) {
            iOtp.onValidationError(mContext.getString(R.string.please_enter_otp));
        } else if (otp.length() < 4) {
            iOtp.onValidationError(mContext.getString(R.string.enter_valid_otp));
        } else {
            if (NetworkUtils.isNetworkEnabled(mContext)) {
              //  iOtp.callSubmitOtpApi(otp);
                iOtp.submitOtp();
            }else {
                iOtp.onValidationError(mContext.getString(R.string.please_check_your_network_connection));
            }
        }
    }

    public void callSubmitOtpApi(String mobile) {
        showDialog(" Please wait....", "SubmitOtp");
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
                LogUtils.DEBUG("SubmitOtp Response ::" + response.toString());
                dismissDialog();
                iOtp.submitOtp();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissDialog();
                LogUtils.DEBUG("SubmitOtp Error ::" + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(objectRequest, "SubmitOtp");
    }
}
