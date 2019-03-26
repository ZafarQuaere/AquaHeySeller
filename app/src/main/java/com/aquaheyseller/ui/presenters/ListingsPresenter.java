package com.aquaheyseller.ui.presenters;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.aquaheyseller.network_call.MyJsonObjectRequest;
import com.aquaheyseller.network_call.response_model.product_list.MyProductsData;
import com.aquaheyseller.ui.presenters.operations.IFragListing;
import com.aquaheyseller.utils.AppConstant;
import com.aquaheyseller.utils.AppController;
import com.aquaheyseller.utils.AppLoaderFragment;
import com.aquaheyseller.utils.LogUtils;
import com.aquaheyseller.utils.Utils;
import com.aquaheyseller.utils.parser.ParseManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListingsPresenter extends BaseFragmentPresenter {
    private Context mContext;
    private IFragListing mProductFrag;
    private AppLoaderFragment loader;

    public ListingsPresenter(Context context, IFragListing productList) {
        super(context);
        mProductFrag = productList;
        mContext = context;
        loader = AppLoaderFragment.getInstance(mContext);
    }

    public void callItemsApi() {
        loader.show();
        String url = AppConstant.URL_BASE + AppConstant.URL_PRODUCT_LIST + Utils.getDealerId(mContext);
        LogUtils.DEBUG("URL : " + url + "\nRequest Body ::");
        MyJsonObjectRequest objectRequest = new MyJsonObjectRequest(mContext, Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtils.DEBUG("ProductList Response ::" + response.toString());
                int status = response.optInt("status");
                try {
                    JSONArray array = response.getJSONArray("data");
                    MyProductsData productsData = ParseManager.getInstance().fromJSON(response.toString(), MyProductsData.class);
                    if (productsData.getStatus().equals(AppConstant.SUCCESS)) {
                        LogUtils.DEBUG("ProductList Response ::" + productsData.getData().toString());
                        mProductFrag.updateList(productsData.getData());
                    }
                    /*if (status == AppConstant.SUCCESS) {
                        mProductFrag.updateList(array);
                    } */else {
                        LogUtils.showToast(mContext, "NO Data to show..");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loader.dismiss();

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.dismiss();
                LogUtils.DEBUG("ProductList Error ::" + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(objectRequest, "ProductList");
    }

}
