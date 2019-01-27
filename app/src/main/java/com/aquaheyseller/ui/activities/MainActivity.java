package com.aquaheyseller.ui.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.aquaheyseller.R;
import com.aquaheyseller.ui.fragments.HomeFragment;
import com.aquaheyseller.ui.fragments.ListingsFragment;
import com.aquaheyseller.ui.fragments.OrdersFragment;
import com.aquaheyseller.ui.presenters.MainPresenter;
import com.aquaheyseller.ui.presenters.operations.IMain;

public class MainActivity extends BaseActivity<MainPresenter>
        implements IMain {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter(this, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        navigationView = (NavigationView) findViewById(R.id.nav_view);
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void onHomeClick(View view) {
        getPresenter().moveToFragment(HomeFragment.class.getSimpleName());
        closeDrawer();

    }

    public void ordersClick(View view) {
        getPresenter().moveToFragment(OrdersFragment.class.getSimpleName());
        closeDrawer();
    }

    public void item3Click(View view) {
        getPresenter().moveToFragment(ListingsFragment.class.getSimpleName());
        closeDrawer();
    }

    public void onLogoutClick(View view) {
        getPresenter().logoutUser();
        closeDrawer();
    }

    public void closeDrawer() {
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
    }
}
