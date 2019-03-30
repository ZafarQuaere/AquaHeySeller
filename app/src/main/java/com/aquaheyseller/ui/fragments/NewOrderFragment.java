package com.aquaheyseller.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.aquaheyseller.R;
import com.aquaheyseller.network_call.MyJsonObjectRequest;
import com.aquaheyseller.utils.AppController;
import com.aquaheyseller.utils.AppLoaderFragment;
import com.aquaheyseller.utils.LogUtils;
import com.aquaheyseller.utils.Utils;

import org.json.JSONObject;

import static com.aquaheyseller.utils.AppConstant.ORDER_STATUS_NEW;
import static com.aquaheyseller.utils.AppConstant.URL_BASE;
import static com.aquaheyseller.utils.AppConstant.URL_ORDERS;
import static com.aquaheyseller.utils.AppConstant.URL_ORDER_STATUS;

public class NewOrderFragment extends Fragment {

    private Context mContext;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_orders, container, false);
        mContext = getActivity();

        return view;
    }

}
