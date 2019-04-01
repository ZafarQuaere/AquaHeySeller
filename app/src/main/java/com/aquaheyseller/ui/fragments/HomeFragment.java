package com.aquaheyseller.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aquaheyseller.R;
import com.aquaheyseller.ui.presenters.HomePresenter;
import com.aquaheyseller.ui.presenters.operations.IFragHome;

public class HomeFragment extends BaseFragment<HomePresenter> implements IFragHome {


    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected HomePresenter initPresenter() {
        return new HomePresenter(getActivity(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        getPresenter().callTodaySalesApi();
        getPresenter().callTotalSalesApi();
        return view;
    }

    @Override
    public void updateTodaySalesData(String todaySalesData) {

    }

    @Override
    public void updateTotalSalesData(String totalSalesData) {
        TextView textTotalSalesAmount = (TextView) view.findViewById(R.id.textTotalSalesAmount);
        textTotalSalesAmount.setText("");
    }
}
