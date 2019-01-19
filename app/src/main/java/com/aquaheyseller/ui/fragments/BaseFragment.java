package com.aquaheyseller.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BaseFragment extends Fragment {

    private ProgressFragment progressDialog ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView view = new TextView(getActivity());
        view.setText("AquaHey");
        return view;
    }

    public void openDialog(){
        progressDialog = new ProgressFragment();
        progressDialog.setCancelable(false);
        progressDialog.showNow(getFragmentManager(),"Progress");
    }

    public void hideDialog(){
        progressDialog.dismiss();
    }
}
