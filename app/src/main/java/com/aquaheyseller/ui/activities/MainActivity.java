package com.aquaheyseller.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aquaheyseller.R;
import com.aquaheyseller.ui.presenters.MainPresenter;
import com.aquaheyseller.ui.presenters.operations.IMain;

public class MainActivity extends BaseActivity<MainPresenter> implements IMain {

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter(this,this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
