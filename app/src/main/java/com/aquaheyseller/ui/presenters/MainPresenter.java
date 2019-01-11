package com.aquaheyseller.ui.presenters;

import android.content.Context;
import android.view.View;

import com.aquaheyseller.ui.presenters.operations.IMain;
import com.aquaheyseller.utils.LogUtils;

public class MainPresenter extends BasePresenter {
    Context mContext;
    IMain iMain;

    public MainPresenter(Context context, IMain iLogin) {
        super(context);
        iMain = iLogin;
        mContext = context;
    }


    public void moveToFragment(String view) {
      /*  String tag = view.getTag().toString();

        LogUtils.showToast(mContext,tag);*/
    }
}
