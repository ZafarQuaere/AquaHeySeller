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
                iFrgtPswd.callSubmitMobileApi(mobileNo);
            } else {
                iFrgtPswd.onValidationError(mContext.getString(R.string.please_check_your_network_connection));
            }
        }
    }

    public void callSubmitMobileApi(final String mobile) {
        showDialog(" Please wait....", "Forgot Password");
        String url = AppConstant.URL_BASE + AppConstant.URL_VERIFY_MOBILE + mobile;

        //  LogUtils.DEBUG("URL : " + url + "\nRequest Body ::" + requestObject.toString());
        MyJsonObjectRequest objectRequest = new MyJsonObjectRequest(mContext, Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtils.DEBUG("ForgotPassword Response ::" + response.toString());
                dismissDialog();
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String resMob = jsonObject.getString("mobile");
                    if (resMob.equalsIgnoreCase(mobile)) {
                        Utils.setMobileNo(mContext,mobile);
                        callOTPServiceApi(mobile);
                    } else {
                        LogUtils.showErrorDialog(mContext, mContext.getString(R.string.ok),
                                mContext.getString(R.string.please_enter_valid_mobile_number));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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

    private void callOTPServiceApi(String mobile) {
         String url = AppConstant.URL_BASE + AppConstant.URL_OTP_SERVICE+mobile;
         //LogUtils.DEBUG("URL : " + url + "\nRequest Body ::" + requestObject.toString());
        MyJsonObjectRequest otpServiceRequest = new MyJsonObjectRequest(mContext, Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtils.DEBUG("OtpService Response ::" + response.toString());
                Utils.saveOTPData(mContext,response.toString());
                iFrgtPswd.submitMobile();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtils.DEBUG("OtpService Error ::" + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(otpServiceRequest, "OtpService");
    }
}
