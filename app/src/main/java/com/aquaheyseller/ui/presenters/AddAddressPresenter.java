package com.aquaheyseller.ui.presenters;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.aquaheyseller.R;
import com.aquaheyseller.network_call.MyJsonObjectRequest;
import com.aquaheyseller.network_call.request.Register;
import com.aquaheyseller.ui.presenters.operations.IRegister;
import com.aquaheyseller.utils.AppConstant;
import com.aquaheyseller.utils.AppController;
import com.aquaheyseller.utils.LogUtils;
import com.aquaheyseller.utils.NetworkUtils;
import com.aquaheyseller.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class AddAddressPresenter extends BasePresenter {
    private Context mContext;
    private IRegister mRegister;

    public AddAddressPresenter(Context context, IRegister iRegister) {
        super(context);
        mRegister = iRegister;
        mContext = context;
    }

    public void validateFields(String name, String mobileNo, String email, String pswd, String confmPswd) {
        if (name.equals("") || name.isEmpty()) {
            mRegister.onValidationError(mContext.getString(R.string.please_enter_username));
        } else if (mobileNo.equals("") || mobileNo.isEmpty() || mobileNo.length() < 10) {
            mRegister.onValidationError(mContext.getString(R.string.please_enter_valid_mobile_number));
        } else if (!Utils.isValidEmail(email)) {
            mRegister.onValidationError(mContext.getString(R.string.please_enter_valid_email));
        } else if (pswd.equals("") || pswd.isEmpty()) {
            mRegister.onValidationError(mContext.getString(R.string.please_enter_password));
        } else if (pswd.length() < 6) {
            mRegister.onValidationError(mContext.getString(R.string.password_must_have_atleast_6_character));
        } else if (!confmPswd.equals(pswd)) {
            mRegister.onValidationError(mContext.getString(R.string.please_enter_same_pswd));
        } else {
            if (NetworkUtils.isNetworkEnabled(mContext)) {
                Register register = new Register(name, pswd, mobileNo, email, 0, 0);
                mRegister.callApi(register);
            }else {
                mRegister.onValidationError(mContext.getString(R.string.please_check_your_network_connection));
            }

        }
    }

    public void callRegisterApi(final Register register) {
        showDialog("Registering....","Register");
        JSONObject requestObject = new JSONObject();
        try {
           // requestObject.put("dname", register.getName());
            requestObject.put("email", register.getEmail());
            requestObject.put("password", register.getPassword());
           /* requestObject.put("mobile", register.getMobile());
            requestObject.put("userType", register.getUserType());
            requestObject.put("dealerId", register.getDealerId());*/

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = AppConstant.baseUrl+AppConstant.registerUrl;
        LogUtils.DEBUG("URL : "+url+"\nRequest Body ::"+requestObject.toString());
        MyJsonObjectRequest objectRequest = new MyJsonObjectRequest(mContext, Request.Method.POST, url, requestObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtils.DEBUG("Register Response ::" + response.toString());
                dismissDialog();
                mRegister.doRegister();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissDialog();
                LogUtils.DEBUG("Register Error ::" + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(objectRequest, "Register");
    }
}
