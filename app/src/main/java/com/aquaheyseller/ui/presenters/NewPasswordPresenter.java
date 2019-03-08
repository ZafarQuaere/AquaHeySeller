package com.aquaheyseller.ui.presenters;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.aquaheyseller.R;
import com.aquaheyseller.network_call.MyJsonObjectRequest;
import com.aquaheyseller.ui.presenters.operations.INewPswd;
import com.aquaheyseller.utils.AppConstant;
import com.aquaheyseller.utils.AppController;
import com.aquaheyseller.utils.LogUtils;
import com.aquaheyseller.utils.NetworkUtils;
import com.aquaheyseller.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class NewPasswordPresenter extends BasePresenter {
    private Context mContext;
    private INewPswd mNewPswd;

    public NewPasswordPresenter(Context context, INewPswd iNewPswd) {
        super(context);
        mNewPswd = iNewPswd;
        mContext = context;
    }

    public void validateUsernamePassword(String password, String confirmPswd) {
        if (password.equals("") || password.isEmpty()) {
            mNewPswd.onValidationError(mContext.getString(R.string.please_enter_password));
        }  else if (password.length() < 6) {
            mNewPswd.onValidationError(mContext.getString(R.string.password_must_have_atleast_6_character));
        } else if (!password.equalsIgnoreCase(confirmPswd)) {
            mNewPswd.onValidationError(mContext.getString(R.string.please_enter_same_pswd));
        } else {
            if (NetworkUtils.isNetworkEnabled(mContext)) {
                mNewPswd.callApi(password);
            } else {
                mNewPswd.onValidationError(mContext.getString(R.string.please_check_your_network_connection));
            }
        }
    }

    public void changePswdApi(String password) {
        String otpData = Utils.getOTPData(mContext);

        LogUtils.DEBUG("OTP DATA : " + otpData);
        showDialog(" Please wait....", "SubmitOtp");
        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("mobile", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = AppConstant.URL_BASE + AppConstant.URL_CHANGE_PASSWORD + Utils.getMobileNo(mContext)+ AppConstant.URL_CP_PASSWORD+password;
        LogUtils.DEBUG("URL : " + url + "\nRequest Body ::" + requestObject.toString());
        MyJsonObjectRequest objectRequest = new MyJsonObjectRequest(mContext, Request.Method.POST, url, requestObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                /**/
                LogUtils.DEBUG("SubmitOtp Response ::" + response.toString());
                dismissDialog();

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
