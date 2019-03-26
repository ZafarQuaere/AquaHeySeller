package com.aquaheyseller.ui.presenters;

import android.content.Context;
import android.os.Handler;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.aquaheyseller.network_call.MyJsonObjectRequest;
import com.aquaheyseller.ui.presenters.operations.IFragHome;
import com.aquaheyseller.utils.AppConstant;
import com.aquaheyseller.utils.AppController;
import com.aquaheyseller.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class HomePresenter extends BaseFragmentPresenter {
    private Context mContext;
    private IFragHome mIFragHome;

    public HomePresenter(Context context, IFragHome iFragHome) {
        super(context);
        mIFragHome = iFragHome;
        mContext = context;
    }


    public void callDashboardApi() {
        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("userId", "20");
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
                mIFragHome.updateUI(response.toString());
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

      //  openProgressDialog();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
        //        hideProgressDialog();
            }
        }, 5000);

    }
}
