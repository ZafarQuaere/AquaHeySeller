package com.aquaheyseller.ui.presenters;

import android.content.Context;
import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.aquaheyseller.R;
import com.aquaheyseller.network_call.MyJsonObjectRequest;
import com.aquaheyseller.network_call.response_model.LoginPojo;
import com.aquaheyseller.ui.activities.ForgetPswdActivity;
import com.aquaheyseller.ui.presenters.operations.ILogin;
import com.aquaheyseller.utils.AppConstant;
import com.aquaheyseller.utils.AppController;
import com.aquaheyseller.utils.LogUtils;
import com.aquaheyseller.utils.NetworkUtils;
import com.aquaheyseller.utils.Utils;
import com.aquaheyseller.utils.parser.ParseManager;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginPresenter extends BasePresenter {
    private Context mContext;
    private ILogin mLogin;

    public LoginPresenter(Context context, ILogin iLogin) {
        super(context);
        mLogin = iLogin;
        mContext = context;
    }

    public void validateUsernamePassword(String userId, String password) {
        if (userId.equals("") || userId.isEmpty()) {
            mLogin.onValidationError(mContext.getString(R.string.please_enter_username));
        } else if (password.equals("") || password.isEmpty()) {
            mLogin.onValidationError(mContext.getString(R.string.please_enter_password));
        } else if (password.length() < 6) {
            mLogin.onValidationError(mContext.getString(R.string.password_must_have_atleast_6_character));
        } else {
            if (NetworkUtils.isNetworkEnabled(mContext)) {
                mLogin.callLoginApi(userId, password);
            }else {
                mLogin.onValidationError(mContext.getString(R.string.please_check_your_network_connection));
            }
        }
    }

    public void callApi(String mobile, String password) {
        showDialog("Login Please wait....", "Login");
        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("mobile", mobile);
            requestObject.put("password", password);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = AppConstant.URL_BASE + AppConstant.URL_LOGIN;
        LogUtils.DEBUG("URL : " + url + "\nRequest Body ::" + requestObject.toString());
        MyJsonObjectRequest objectRequest = new MyJsonObjectRequest(mContext, Request.Method.POST, url, requestObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtils.DEBUG("Login Response ::" + response.toString());
                LoginPojo loginData = ParseManager.getInstance().fromJSON(response.toString(),LoginPojo.class);
                try {
                    if (loginData.getSuccess().equals(AppConstant.SUCCESS)){
                        dismissDialog();
                        Utils.setLoggedIn(mContext, true);
                        mLogin.doLogin();
                    }else {
                        LogUtils.showErrorDialog(mContext, mContext.getString(R.string.ok),
                                mContext.getString(R.string.please_enter_valid_credentials));
                    }
                } catch (Exception e) {
                    dismissDialog();
                    e.printStackTrace();
                    LogUtils.showErrorDialog(mContext, mContext.getString(R.string.ok),
                            mContext.getString(R.string.please_enter_valid_credentials));
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissDialog();
                LogUtils.DEBUG("Login Error ::" + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(objectRequest, "Login");
    }

    public void startActivity(Context mContext) {
        Intent intent = new Intent(mContext, ForgetPswdActivity.class);
        intent.putExtra(AppConstant.COMINGFROM,AppConstant.LOGIN);
        mContext.startActivity(intent);
    }
}
