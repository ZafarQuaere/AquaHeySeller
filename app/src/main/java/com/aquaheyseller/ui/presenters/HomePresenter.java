package com.aquaheyseller.ui.presenters;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.aquaheyseller.network_call.MyJsonObjectRequest;
import com.aquaheyseller.network_call.response_model.home.Sales;
import com.aquaheyseller.network_call.response_model.home.SalesData;
import com.aquaheyseller.ui.presenters.operations.IFragHome;
import com.aquaheyseller.utils.AppConstant;
import com.aquaheyseller.utils.AppController;
import com.aquaheyseller.utils.AppLoaderFragment;
import com.aquaheyseller.utils.LogUtils;
import com.aquaheyseller.utils.Utils;
import com.aquaheyseller.utils.parser.ParseManager;

import org.json.JSONObject;

public class HomePresenter extends BaseFragmentPresenter {
    private Context mContext;
    private IFragHome mIFragHome;
    private AppLoaderFragment loader;

    public HomePresenter(Context context, IFragHome iFragHome) {
        super(context);
        mIFragHome = iFragHome;
        mContext = context;
        loader = AppLoaderFragment.getInstance(mContext);
    }


    public void callTodaySalesApi() {
        String url = AppConstant.URL_BASE + AppConstant.URL_TODAY_SALES+ Utils.getDealerId(mContext);
        LogUtils.DEBUG("URL : " + url + "\nRequest Body ::" );
        MyJsonObjectRequest objectRequest = new MyJsonObjectRequest(mContext, Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtils.DEBUG("TodaySales Response ::" + response.toString());
                SalesData data = ParseManager.getInstance().fromJSON(response.toString(), SalesData.class);
                if (data.getStatus().equals(AppConstant.SUCCESS)){
                    mIFragHome.updateTodaySalesData(data.getData());
                }else {
                    mIFragHome.updateTodaySalesData(null);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtils.DEBUG("TodaySales Error ::" + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(objectRequest, "TodaySales");
    }

    public void callTotalSalesApi() {
        loader.show();
        String url = AppConstant.URL_BASE + AppConstant.URL_TOTAL_SALES+ Utils.getDealerId(mContext);
        LogUtils.DEBUG("URL : " + url + "\nRequest Body ::" );
        MyJsonObjectRequest objectRequest = new MyJsonObjectRequest(mContext, Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtils.DEBUG("TotalSales Response ::" + response.toString());
                SalesData data = ParseManager.getInstance().fromJSON(response.toString(), SalesData.class);
                if (data.getStatus().equals(AppConstant.SUCCESS)){
                    mIFragHome.updateTotalSalesData(data.getData());
                }else {
                    mIFragHome.updateTotalSalesData(null);
                }
                loader.dismiss();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtils.DEBUG("TotalSales Error ::" + error.getMessage());
                loader.dismiss();
            }
        });
        AppController.getInstance().addToRequestQueue(objectRequest, "TotalSales");
    }

}
