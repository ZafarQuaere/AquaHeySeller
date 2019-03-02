package com.aquaheyseller.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aquaheyseller.R;
import com.aquaheyseller.ui.presenters.ListingsPresenter;
import com.aquaheyseller.ui.presenters.operations.IListingFrag;

public class ListingsFragment extends BaseFragment<ListingsPresenter> implements IListingFrag {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected ListingsPresenter initPresenter() {
        return new ListingsPresenter(getActivity(),this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listings, container, false);

        view.findViewById(R.id.btnAddProduct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().starTestDialog();
            }
        });
        return view;
    }

    @Override
    public void addProduct() {

    }

    @Override
    public void onValidationError(String msg) {

    }

    @Override
    public void callApi() {

    }
}
