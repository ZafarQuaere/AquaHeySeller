package com.aquaheyseller.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aquaheyseller.R;
import com.aquaheyseller.ui.fragments.HomeFragment;
import com.aquaheyseller.ui.fragments.ListingsFragment;
import com.aquaheyseller.ui.fragments.OrdersFragment;
import com.aquaheyseller.ui.fragments.PaymentsFragment;
import com.aquaheyseller.ui.fragments.FragmentProfile;
import com.aquaheyseller.ui.presenters.MainPresenter;
import com.aquaheyseller.ui.presenters.operations.IMain;
import com.aquaheyseller.utils.LogUtils;
import com.aquaheyseller.utils.Utils;

public class MainActivity extends BaseActivity<MainPresenter>
        implements IMain {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private Context mContext;

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter(this, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        setUpToolbar();
        initUI();
        moveToHome();

    }

    private void moveToHome() {
        getPresenter().moveToFragment(HomeFragment.class.getSimpleName());
    }

    private void setUpToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initUI() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        ImageView imgDrawerIcon = (ImageView) findViewById(R.id.imgActionBarDrawerIcon);
        imgDrawerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer();
            }
        });
    }

    private void openDrawer() {
        drawer.openDrawer(GravityCompat.START);
    }


    public void onHomeClick(View view) {
        getPresenter().moveToFragment(HomeFragment.class.getSimpleName());
        closeDrawer();

    }

    public void ordersClick(View view) {
        getPresenter().moveToFragment(OrdersFragment.class.getSimpleName());
        closeDrawer();
    }

    public void onPaymentsClick(View view) {
        getPresenter().moveToFragment(PaymentsFragment.class.getSimpleName());
        closeDrawer();
    }

    public void onListingClick(View view) {
        getPresenter().moveToFragment(ListingsFragment.class.getSimpleName());
        closeDrawer();
    }
    public void onProfileClick(View view) {
        closeDrawer();
        getPresenter().moveToFragment(FragmentProfile.class.getSimpleName());

    }
    public void saveAddressClick(View view) {
        closeDrawer();
        startActivity(new Intent(MainActivity.this,AddSellerAddressActivity.class));
    }
    public void item3Click(View view) {
        closeDrawer();
        LogUtils.showToast(mContext,"Development under progress");
    }

    public void onLogoutClick(View view) {
        getPresenter().logoutUser();
        closeDrawer();
    }

    public void expandMyAccount(View view) {
        LinearLayout lytMyAccount = (LinearLayout)findViewById(R.id.lytMyAccount);
        ImageButton iconMyAccountExpand = (ImageButton)findViewById(R.id.iconMyAccountExpand);
        lytMyAccount.setVisibility(lytMyAccount.getVisibility()== View.VISIBLE ? View.GONE : View.VISIBLE);
        iconMyAccountExpand.setBackground(lytMyAccount.getVisibility()== View.VISIBLE ?getResources().getDrawable(R.drawable.ic_minus) : getResources().getDrawable(R.drawable.ic_plus));
    }

    public void closeDrawer() {
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            updateToolbar();
        }
        //getPresenter().updateToolbarTitle(mContext);
    }

    private void updateToolbar() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count > 0) {
            //Utils.clearBackStackTillHomeFragment(MainActivity.this);
           try {
                getSupportFragmentManager().popBackStack();
                FragmentManager.BackStackEntry a = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 2);//top
                Fragment baseFrag = (Fragment) getSupportFragmentManager().findFragmentByTag(a.getName());
                LogUtils.DEBUG("baseFrag Fragment : " + baseFrag.getClass().getSimpleName());
                getPresenter().updateActionBarTitleOnBackPress(mContext,baseFrag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            super.onBackPressed();
        }
        if (count == 1){
            finishAffinity();
        }

    }

    public void changePasswordClick(View view) {
        getPresenter().startActivity(mContext);

    }

    public void onShareClick(View view) {
        Utils.shareApp(MainActivity.this);
    }


}
