package com.aquaheyseller.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aquaheyseller.R;
import com.aquaheyseller.network_call.response_model.product_list.Data;
import com.aquaheyseller.ui.adapters.ProductRecylcerAdapter;
import com.aquaheyseller.ui.presenters.ListingsPresenter;
import com.aquaheyseller.ui.presenters.operations.IFragListing;
import com.aquaheyseller.utils.AppLoaderFragment;
import com.aquaheyseller.utils.LogUtils;

import java.util.ArrayList;

public class ListingsFragment extends BaseFragment<ListingsPresenter> implements IFragListing {

    private RecyclerView recylcerProducts;
    private RecyclerView.LayoutManager layoutManager;
    private AppLoaderFragment loader;
    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mContext = getActivity();
    }

    @Override
    protected ListingsPresenter initPresenter() {
        return new ListingsPresenter(getActivity(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listings, container, false);
        initUI(view);

        getPresenter().callItemsApi();

        return view;
    }

    private void initUI(View view) {
        recylcerProducts = (RecyclerView) view.findViewById(R.id.recylcerProducts);
        recylcerProducts.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recylcerProducts.setLayoutManager(layoutManager);
        recylcerProducts.setItemAnimator(new DefaultItemAnimator());
    }


    @Override
    public void onResponseSuccess() {

    }

    @Override
    public void onResponseFailure(String msg) {

    }

    @Override
    public void editItem() {

    }

    @Override
    public void updateList(Data[] data) {
        ArrayList<Data> productList = new ArrayList<>();

        for (int i = 0; i < data.length; i++) {
            productList.add(data[i]);
          /*  try {
                JSONObject object = data.getJSONObject(i);
                Data productsData = ParseManager.getInstance().fromJSON(object.toString(), Data.class);
                productList.add(productsData);
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
        }
        ProductRecylcerAdapter adapter = new ProductRecylcerAdapter(productList, new OnListFragmentInteractionListener() {
            @Override
            public void onListFragmentInteraction(Data item) {
                LogUtils.showToast(mContext,item.getPName());
            }
        });
        recylcerProducts.setAdapter(adapter);

    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Data item);
    }
}
