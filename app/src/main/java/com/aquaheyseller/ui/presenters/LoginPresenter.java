package com.aquaheyseller.ui.presenters;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.aquaheyseller.R;
import com.aquaheyseller.network_call.MyJsonObjectRequest;
import com.aquaheyseller.ui.presenters.operations.ILogin;
import com.aquaheyseller.utils.AppConstant;
import com.aquaheyseller.utils.AppController;
import com.aquaheyseller.utils.LogUtils;
import com.aquaheyseller.utils.NetworkUtils;
import com.aquaheyseller.utils.Utils;

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

    public void callApi(String userId, String password) {
        showDialog("Login Please wait....", "Login");
        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("email", userId);
            requestObject.put("password", password);

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
                LogUtils.DEBUG("Login Response ::" + response.toString());
                dismissDialog();
                Utils.setLoggedIn(mContext, true);
                mLogin.doLogin();
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
}
