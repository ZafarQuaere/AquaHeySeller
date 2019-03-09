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
import com.aquaheyseller.utils.parser.ParseManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.Parser;

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
            validateServerOtp(otp);
        }
    }

    public void validateServerOtp(String otp) {
        String otpData = Utils.getOTPData(mContext);
      //  LogUtils.DEBUG("OTP DATA : "+otpData);
        try {
            JSONObject jObj = new JSONObject(otpData);
            String otpText = jObj.getString("sms_text");
            String serverOtp = otpText.substring(0, 4);
            LogUtils.DEBUG("OTP :: "+serverOtp);
            if (serverOtp.equals(otp)){
                iOtp.submitOtp();
            }else {
                iOtp.onValidationError(mContext.getString(R.string.enter_valid_otp));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
