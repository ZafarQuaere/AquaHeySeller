package com.aquaheyseller.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aquaheyseller.R;
import com.aquaheyseller.network_call.response_model.home.Sales;
import com.aquaheyseller.ui.presenters.HomePresenter;
import com.aquaheyseller.ui.presenters.operations.IFragHome;
import com.aquaheyseller.utils.AppConstant;

import java.util.List;

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

    @SuppressLint("SetTextI18n")
    @Override
    public void updateTodaySalesData(List<Sales> todaySalesData) {
        if (todaySalesData != null) {
            TextView textTodayAmount = (TextView) view.findViewById(R.id.textTodayAmount);
            TextView textTodayUnit = (TextView) view.findViewById(R.id.textTodayUnit);
            textTodayAmount.setText(todaySalesData.get(0).getTotalAmount());
            textTodayUnit.setText(todaySalesData.get(0).getTotalUnit()+" "+getString(R.string.units));
        }
    }

    @Override
    public void updateTotalSalesData(List<Sales> data) {
        if (data!= null){
            TextView textTotalSalesAmount = (TextView) view.findViewById(R.id.textTotalSalesAmount);
            textTotalSalesAmount.setText(getAmountandUnit(data));
        }
    }

    private String getAmountandUnit(List<Sales> data) {
        return AppConstant.RUPEES_SYMBOL+" "+data.get(0).getTotalAmount()+" Of "+data.get(0).getTotalUnit()+" "+getString(R.string.units);
    }
}
