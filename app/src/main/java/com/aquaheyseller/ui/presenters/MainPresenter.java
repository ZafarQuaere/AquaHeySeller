package com.aquaheyseller.ui.presenters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.aquaheyseller.R;
import com.aquaheyseller.ui.activities.LoginActivity;
import com.aquaheyseller.ui.fragments.HomeFragment;
import com.aquaheyseller.ui.fragments.ListingsFragment;
import com.aquaheyseller.ui.fragments.OrdersFragment;
import com.aquaheyseller.ui.fragments.PaymentsFragment;
import com.aquaheyseller.ui.interfaces.DialogButtonClick;
import com.aquaheyseller.ui.presenters.operations.IMain;
import com.aquaheyseller.utils.LogUtils;
import com.aquaheyseller.utils.Utils;
import com.aquaheyseller.utils.storage.AppSharedPrefs;

public class MainPresenter extends BasePresenter {
    Context mContext;
    IMain iMain;

    public MainPresenter(Context context, IMain iLogin) {
        super(context);
        iMain = iLogin;
        mContext = context;
    }


    public void moveToFragment(String fragName) {
        switch (fragName) {
            case "HomeFragment":
                Utils.moveToFragment(mContext, new HomeFragment(), HomeFragment.class.getSimpleName(), null);
                Utils.updateActionBar(mContext,HomeFragment.class.getSimpleName(),"AquaHey",null,null);
                break;
            case "OrdersFragment":
                Utils.moveToFragment(mContext, new OrdersFragment(), OrdersFragment.class.getSimpleName(), null);
                Utils.updateActionBar(mContext,HomeFragment.class.getSimpleName(),"Orders",null,null);
                break;
            case "ListingsFragment":
                Utils.moveToFragment(mContext, new ListingsFragment(), ListingsFragment.class.getSimpleName(), null);
                Utils.updateActionBar(mContext,HomeFragment.class.getSimpleName(),"Order List",null,null);
                break;
            case "PaymentsFragment":
                Utils.moveToFragment(mContext, new PaymentsFragment(), PaymentsFragment.class.getSimpleName(), null);
                Utils.updateActionBar(mContext,PaymentsFragment.class.getSimpleName(),"Payments",null,null);
                break;
        }
        //LogUtils.showToast(mContext, fragName);
    }

    public void logoutUser() {
        LogUtils.showDialogDoubleButton(mContext, mContext.getString(R.string.cancel), mContext.getString(R.string.ok),
                mContext.getString(R.string.do_you_really_want_to_logout), new DialogButtonClick() {
                    @Override
                    public void onOkClick() {
                        AppSharedPrefs prefs = AppSharedPrefs.getInstance(mContext);
                        prefs.clear(mContext.getString(R.string.key_logged_in));
                        mContext.startActivity(new Intent(mContext, LoginActivity.class));
                        LogUtils.showToast(mContext, mContext.getString(R.string.you_are_sucessfully_logout));
                        ((Activity) mContext).finish();
                    }

                    @Override
                    public void onCancelClick() {
                    }
                });
    }
}
