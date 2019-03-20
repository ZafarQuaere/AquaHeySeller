package com.aquaheyseller.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aquaheyseller.R;
import com.aquaheyseller.ui.presenters.ProfilePresenter;
import com.aquaheyseller.ui.presenters.operations.IFragProfile;

public class FragmentProfile extends BaseFragment<ProfilePresenter> implements IFragProfile {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected ProfilePresenter initPresenter() {
        return new ProfilePresenter(getActivity(),this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

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
