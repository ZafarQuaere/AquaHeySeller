package com.aquaheyseller.ui.presenters;

import android.content.Context;

import com.aquaheyseller.ui.presenters.operations.IMain;

public class MainPresenter extends BasePresenter {
    Context mContext;
    IMain iMain;

    public MainPresenter(Context context, IMain iLogin) {
        super(context);
        iMain = iLogin;
        mContext = context;
    }


}
