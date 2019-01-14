package com.aquaheyseller.ui.presenters;

import android.content.Context;
import android.view.View;

import com.aquaheyseller.R;
import com.aquaheyseller.ui.fragments.HomeFragment;
import com.aquaheyseller.ui.fragments.ListingsFragment;
import com.aquaheyseller.ui.fragments.OrdersFragment;
import com.aquaheyseller.ui.presenters.operations.IMain;
import com.aquaheyseller.utils.LogUtils;
import com.aquaheyseller.utils.Utils;

public class MainPresenter extends BasePresenter {
    Context mContext;
    IMain iMain;

    public MainPresenter(Context context, IMain iLogin) {
        super(context);
        iMain = iLogin;
        mContext = context;
    }


    public void moveToFragment(String fragName) {
        switch (fragName){
            case "HomeFragment":
                Utils.moveToFragment(mContext,new HomeFragment(), HomeFragment.class.getSimpleName(),null);
                break;
            case "OrdersFragment":
                Utils.moveToFragment(mContext,new OrdersFragment(), OrdersFragment.class.getSimpleName(),null);
                break;
            case "ListingsFragment":
                Utils.moveToFragment(mContext,new ListingsFragment(), ListingsFragment.class.getSimpleName(),null);
                break;
        }
        LogUtils.showToast(mContext,fragName);
    }
}
