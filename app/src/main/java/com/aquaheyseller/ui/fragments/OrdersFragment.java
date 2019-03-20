package com.aquaheyseller.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.aquaheyseller.R;
import com.aquaheyseller.network_call.MyJsonObjectRequest;
import com.aquaheyseller.ui.adapters.OrdresPagerAdapter;
import com.aquaheyseller.ui.presenters.BaseFragmentPresenter;
import com.aquaheyseller.utils.AppConstant;
import com.aquaheyseller.utils.AppController;
import com.aquaheyseller.utils.LogUtils;
import com.aquaheyseller.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class OrdersFragment extends BaseFragment implements TabLayout.OnTabSelectedListener {

    private Context mContext;
    private ViewPager viewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected BaseFragmentPresenter initPresenter() {
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        mContext = getActivity();
        initUI(view);
        callOrderApi();
        return view;
    }

    private void initUI(View view) {
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);

        //Initializing viewPager
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);

        //Creating our pager adapter
        OrdresPagerAdapter adapter = new OrdresPagerAdapter(getActivity(),this.getChildFragmentManager(),tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        //Adding onTabSelectedListener to swipe views
        tabLayout.addOnTabSelectedListener(this);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }


    public void callOrderApi() {

        String url = AppConstant.baseUrl + AppConstant.listUrl+""+2;
        LogUtils.DEBUG("URL : " + url );
        MyJsonObjectRequest objectRequest = new MyJsonObjectRequest(mContext, Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
               /* Gson gson = new GsonBuilder().create();
                String string = gson.toJson(response);*/
                LogUtils.DEBUG("List Response ::" + response.toString());
                Utils.saveListResponse(getActivity(),response.toString());

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtils.DEBUG("List Error ::" + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(objectRequest, "List");
    }
}
