package com.aquaheyseller.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.aquaheyseller.R;
import com.aquaheyseller.ui.fragments.HomeFragment;
import com.aquaheyseller.utils.storage.AppSharedPrefs;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.zip.GZIPInputStream;


public class Utils {

    public static boolean isNetConnected(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());

    }


    public static void hideKeyboard(Context context, View view) {
        if (context == null || view == null) {
            return;
        }
        try {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            LogUtils.ERROR("Error in hideKeyboard() : " + e.toString());
        }

    }


    public static boolean isValidMobileNumber(String mobileNo) {
        return Patterns.PHONE.matcher(mobileNo)
                .matches();
    }

    public static boolean isValidEmail(Activity activity, String email) {
        if (activity == null)
            return false;

        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();

    }

    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    @SuppressLint("all")
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getTimeStamp() {
        return new SimpleDateFormat(AppConstant.TIMESTAMP_FORMAT, Locale.US).format(new Date());
    }

    public static void setLoggedIn(Context mContext, boolean b) {
        if (mContext == null)
            return;
        AppSharedPrefs prefs = AppSharedPrefs.getInstance(mContext);
        prefs.put(mContext.getString(R.string.key_logged_in), b);
    }

    public static boolean isLoggedIn(Context context) {
        AppSharedPrefs prefs = AppSharedPrefs.getInstance(context);
        boolean isLogIn = false;
        try {
            isLogIn = (boolean) prefs.get(context.getString(R.string.key_logged_in));
        } catch (Exception e) {
            e.printStackTrace();
            return isLogIn;
        }
        return isLogIn;

    }

    public static void moveToFragment(Context context, Fragment fragment, String fragName, Object data) {
        LogUtils.DEBUG("moveToFragment() called : " + fragment.getClass().getSimpleName());
        if (context == null || fragment == null)
            return;

        FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
       // ((Activity)context).getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.lytMain, fragment, fragment.getClass().getSimpleName());

        //if data is also transferring
        Bundle bundle = new Bundle();
        if (data != null) {
            bundle.putSerializable(context.getString(R.string.key_serializable), (Serializable) data);
        }
        fragment.setArguments(bundle);

        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();

    }

    public static byte[] decompress(byte[] str) throws IOException, UnsupportedEncodingException {
        if (str == null || str.length == 0) {
            return str;
        }
        GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(str));
        BufferedReader bf = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
        StringBuilder outStr = new StringBuilder();
        String line;
        while ((line = bf.readLine()) != null) {
            outStr.append(line);
        }
        return new String(outStr).getBytes();
    }

   /* public static void updateActionBar(final Activity activity, final String className,
                                       String dynamicTitle, Object customHeaderData,  final ActionBarItemClickListener actionBarItemClickListener) {

        if (activity == null)
            return;

        LogUtils.DEBUG(AppConstant.TAG + " Utils >> updateActionBar() called : " + className + "/" + dynamicTitle);

        RelativeLayout toolbarLayout = (RelativeLayout) activity.findViewById(R.id.toolbar);
        TextView textTitle = (TextView) toolbarLayout.findViewById(R.id.textTitle);
        TextView textBack = (TextView) toolbarLayout.findViewById(R.id.textBack);
        textTitle.setText(dynamicTitle);
        textBack.setVisibility(View.GONE);

        if (className.equals(new HomeFragment().getClass().getSimpleName())) {
            textBack.setVisibility(View.GONE);
            textBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //activity.startActivity(new Intent(activity, LoginActivity.class));

                }
            });
        } else if (className.equals(new OrdersFragment().getClass().getSimpleName())) {
            textBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onBackPressed();
                }
            });
        }

        else if (className.equals(new ListingsFragment().getClass().getSimpleName())) {
            textBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onBackPressed();
                }
            });
        }


    }*/

    public static void clearBackStackTillHomeFragment(Activity activity) {

        LogUtils.DEBUG("Utils >> clearBackStackTillHomeFragment() >> activity : " + activity);
        if (activity == null) {
            return;
        }
        android.app.FragmentManager fm = ((Activity) activity).getFragmentManager();

        for (int i = fm.getBackStackEntryCount() - 1; i > 0; i--) {
            String fragmentName = (fm.getBackStackEntryAt(i)).getName();
            if (!fragmentName.equals(new HomeFragment().getClass().getName())) {
                fm.popBackStack();
                LogUtils.DEBUG("Utils >> clearBackStackTillHomeFragment() >> removed fragment : " + fragmentName);
            } else {
                break;
            }
        }
        // updateActionBar(activity, new HomeFragment().getClass().getSimpleName(), activity.getString(R.string.reddy_ice), null, null, null);
        // updateBottomBar(activity, new HomeFragment().getClass().getSimpleName());
    }
/*
    @SuppressLint("ResourceType")
    public static void updateBottomBar(final Activity activity, final String className) {

        LinearLayout lytBottomBar = (LinearLayout) activity.findViewById(R.id.lytBottomBar);

        LinearLayout lytHomeBar = (LinearLayout) activity.findViewById(R.id.lytHomeBar);
        LinearLayout lytOrders = (LinearLayout) activity.findViewById(R.id.lytOrders);
        LinearLayout lytVegOrder = (LinearLayout) activity.findViewById(R.id.lytVegOrder);
        LinearLayout lytNonvegOrders = (LinearLayout) activity.findViewById(R.id.lytNonvegOrders);
        LinearLayout lytHistoryBar = (LinearLayout) activity.findViewById(R.id.lytHistoryBar);


        TextView textBottomHomeIcon = (TextView) lytBottomBar.findViewById(R.id.textBottomHomeIcon);
        TextView textIconOrders = (TextView) lytBottomBar.findViewById(R.id.textIconOrders);
        TextView textBottomHistoryIcon = (TextView) lytBottomBar.findViewById(R.id.textBottomHistoryIcon);
        TextView textBottomNonVegIcon = (TextView) lytBottomBar.findViewById(R.id.textBottomNonVegIcon);
        TextView textBottomVegIcon = (TextView) lytBottomBar.findViewById(R.id.textBottomVegIcon);

        TextView textHomeLable = (TextView) lytBottomBar.findViewById(R.id.textHomeLable);
        TextView textOrderLable = (TextView) lytBottomBar.findViewById(R.id.textOrderLable);
        TextView textVegLable = (TextView) lytBottomBar.findViewById(R.id.textVegLable);
        TextView textNonVegLable = (TextView) lytBottomBar.findViewById(R.id.textNonVegLable);
        TextView textHistoryLable = (TextView) lytBottomBar.findViewById(R.id.textHistoryLable);

        lytBottomBar.setVisibility(View.VISIBLE);

        lytHomeBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!AppDialogLoader.getLoader(activity).CheckLoaderStatus()) {
                        if (className.equalsIgnoreCase(new HomeFragment().getClass().getSimpleName())){

                        }else {

                            Utils.moveToFragment(activity, new HomeFragment(), null);
                            updateBottomBar(activity, new HomeFragment().getClass().getSimpleName());
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        lytOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (className.equalsIgnoreCase(new OrdersFragment().getClass().getSimpleName())){

                }else {
                    if (isNetConnected(activity)) {
                        if (!AppDialogLoader.getLoader(activity).CheckLoaderStatus()) {
                            clearBackStackTillHomeFragment(activity);
                            Utils.moveToFragment(activity, new OrdersFragment(), null);
                            updateBottomBar(activity, new OrdersFragment().getClass().getSimpleName());

                        }
                    } else {
                        LogUtils.showToast(activity, activity.getString(R.string.please_check_network_connection));
                    }
                }
            }
        });

        lytHistoryBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (className.equalsIgnoreCase(new HistoryFragment().getClass().getSimpleName())){

                }else {
                    if (Utils.isNetConnected(activity)) {
                        clearBackStackTillHomeFragment(activity);
                        Utils.moveToFragment(activity, new HistoryFragment(), null);
                        updateBottomBar(activity, new HistoryFragment().getClass().getSimpleName());
                    } else {
                        LogUtils.showToast(activity, activity.getString(R.string.please_check_network_connection));
                    }
                }
            }
        });

        lytVegOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!AppDialogLoader.getLoader(activity).CheckLoaderStatus()) {
                    if (className.equalsIgnoreCase(new VegOrdersFragment().getClass().getSimpleName())){

                    }else {
                        clearBackStackTillHomeFragment(activity);
                        Utils.moveToFragment(activity, new VegOrdersFragment(), null);
                    }
                }

            }
        });

        lytNonvegOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (className.equalsIgnoreCase(new NonVegOrdersFragment().getClass().getSimpleName())){

                }else {
                    clearBackStackTillHomeFragment(activity);
                    Utils.moveToFragment(activity, new NonVegOrdersFragment(), null);
                    updateBottomBar(activity, new NonVegOrdersFragment().getClass().getSimpleName());
                }

            }
        });


        if (className.equals(new HomeFragment().getClass().getSimpleName())) {

           *//* textBottomHomeIcon.setTextColor(Color.parseColor(activity.getString(R.color.color_app_theme)));
            textIconOrders.setTextColor(Color.parseColor(activity.getString(R.color.color_dark_grey)));
            textBottomHistoryIcon.setTextColor(Color.parseColor(activity.getString(R.color.color_dark_grey)));
            textBottomNonVegIcon.setTextColor(Color.parseColor(activity.getString(R.color.color_dark_grey)));
            textBottomVegIcon.setTextColor(Color.parseColor(activity.getString(R.color.color_dark_grey)));*//*

            textHomeLable.setTextColor(Color.parseColor(activity.getString(R.color.color_app_theme)));
            textOrderLable.setTextColor(Color.parseColor(activity.getString(R.color.color_black)));
            textVegLable.setTextColor(Color.parseColor(activity.getString(R.color.color_black)));
            textNonVegLable.setTextColor(Color.parseColor(activity.getString(R.color.color_black)));
            textHistoryLable.setTextColor(Color.parseColor(activity.getString(R.color.color_black)));
        } else if (className.equals(new OrdersFragment().getClass().getSimpleName())) {
           *//* textBottomHomeIcon.setTextColor(Color.parseColor(activity.getString(R.color.color_dark_grey)));
            textIconOrders.setTextColor(Color.parseColor(activity.getString(R.color.color_app_theme)));
            textBottomHistoryIcon.setTextColor(Color.parseColor(activity.getString(R.color.color_dark_grey)));
            textBottomNonVegIcon.setTextColor(Color.parseColor(activity.getString(R.color.color_dark_grey)));
            textBottomVegIcon.setTextColor(Color.parseColor(activity.getString(R.color.color_dark_grey)));*//*

            textHomeLable.setTextColor(Color.parseColor(activity.getString(R.color.color_black)));
            textOrderLable.setTextColor(Color.parseColor(activity.getString(R.color.color_app_theme)));
            textVegLable.setTextColor(Color.parseColor(activity.getString(R.color.color_black)));
            textNonVegLable.setTextColor(Color.parseColor(activity.getString(R.color.color_black)));
            textHistoryLable.setTextColor(Color.parseColor(activity.getString(R.color.color_black)));
        } else if (className.equals(new VegOrdersFragment().getClass().getSimpleName()) ) {
           *//*textBottomHomeIcon.setTextColor(Color.parseColor(activity.getString(R.color.color_dark_grey)));
            textIconOrders.setTextColor(Color.parseColor(activity.getString(R.color.color_dark_grey)));
            textBottomHistoryIcon.setTextColor(Color.parseColor(activity.getString(R.color.color_dark_grey)));
            textBottomNonVegIcon.setTextColor(Color.parseColor(activity.getString(R.color.color_dark_grey)));
            textBottomVegIcon.setTextColor(Color.parseColor(activity.getString(R.color.color_app_theme)));*//*

            textHomeLable.setTextColor(Color.parseColor(activity.getString(R.color.color_black)));
            textOrderLable.setTextColor(Color.parseColor(activity.getString(R.color.color_black)));
            textVegLable.setTextColor(Color.parseColor(activity.getString(R.color.color_app_theme)));
            textNonVegLable.setTextColor(Color.parseColor(activity.getString(R.color.color_black)));
            textHistoryLable.setTextColor(Color.parseColor(activity.getString(R.color.color_black)));

        } else if (className.equals(new NonVegOrdersFragment().getClass().getSimpleName())) {
            //textBottomHomeIcon.setTextColor(Color.parseColor(activity.getString(R.color.color_dark_grey)));
            //textIconOrders.setTextColor(Color.parseColor(activity.getString(R.color.color_dark_grey)));
            //textBottomVegIcon.setTextColor(Color.parseColor(activity.getString(R.color.color_dark_grey)));
          *//*  textBottomHistoryIcon.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.icon_history));
            textBottomNonVegIcon.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.icon_nonveg_colored));*//*


            textHomeLable.setTextColor(Color.parseColor(activity.getString(R.color.color_black)));
            textOrderLable.setTextColor(Color.parseColor(activity.getString(R.color.color_black)));
            textHistoryLable.setTextColor(Color.parseColor(activity.getString(R.color.color_black)));
            textNonVegLable.setTextColor(Color.parseColor(activity.getString(R.color.color_app_theme)));
            textVegLable.setTextColor(Color.parseColor(activity.getString(R.color.color_black)));

        } else if (className.equals(new HistoryFragment().getClass().getSimpleName())) {
           *//* textBottomHomeIcon.setTextColor(Color.parseColor(activity.getString(R.color.color_dark_grey)));
            textIconOrders.setTextColor(Color.parseColor(activity.getString(R.color.color_dark_grey)));
            textBottomVegIcon.setTextColor(Color.parseColor(activity.getString(R.color.color_dark_grey)));*//*
     *//*textBottomHistoryIcon.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.icon_history_colored));
            textBottomNonVegIcon.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.icon_nonveg));*//*

            textHomeLable.setTextColor(Color.parseColor(activity.getString(R.color.color_black)));
            textOrderLable.setTextColor(Color.parseColor(activity.getString(R.color.color_black)));
            textVegLable.setTextColor(Color.parseColor(activity.getString(R.color.color_black)));
            textNonVegLable.setTextColor(Color.parseColor(activity.getString(R.color.color_black)));
            textHistoryLable.setTextColor(Color.parseColor(activity.getString(R.color.color_app_theme)));
        }
    }


    public static void updateActionBar(final Activity activity, final String className,
                                       String dynamicTitle, Object customHeaderData, final ActionBarItemClickListener actionBarItemClickListener) {

        if (activity == null)
            return;

        LogUtils.DEBUG(AppConstant.TAG + " Utils >> updateActionBar() called : " + className + "/" + dynamicTitle);

        RelativeLayout toolbarLayout = (RelativeLayout) activity.findViewById(R.id.toolbar);
        TextView textTitle = (TextView) toolbarLayout.findViewById(R.id.textTitle);
        TextView textBack = (TextView) toolbarLayout.findViewById(R.id.textBack);
        textTitle.setText(dynamicTitle);
        textBack.setVisibility(View.GONE);

        if (className.equals(new HomeFragment().getClass().getSimpleName())) {
            textBack.setVisibility(View.GONE);
            textBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //activity.startActivity(new Intent(activity, LoginActivity.class));

                }
            });
        } else if (className.equals(new OrdersFragment().getClass().getSimpleName())) {
            textBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onBackPressed();
                }
            });
        }

        else if (className.equals(new VegOrdersFragment().getClass().getSimpleName())) {
            textBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onBackPressed();
                }
            });
        }

        else if (className.equals(new NonVegOrdersFragment().getClass().getSimpleName())) {
            textBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onBackPressed();
                }
            });
        }
        else if (className.equals(new HistoryFragment().getClass().getSimpleName())) {
            textBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onBackPressed();
                }
            });
        }
    }

    public static void clearBackStackTillHomeFragment(Activity activity) {

        LogUtils.DEBUG("Utils >> clearBackStackTillHomeFragment() >> activity : " + activity);
        if (activity == null) {
            return;
        }
        FragmentManager fm = ((Activity) activity).getFragmentManager();

        for (int i = fm.getBackStackEntryCount()-1 ; i > 0; i--) {
            String fragmentName = (fm.getBackStackEntryAt(i)).getName();
            if (!fragmentName.equals(new HomeFragment().getClass().getName())) {
                fm.popBackStack();
                LogUtils.DEBUG("Utils >> clearBackStackTillHomeFragment() >> removed fragment : " + fragmentName);
            } else {
                break;
            }
        }
       // updateActionBar(activity, new HomeFragment().getClass().getSimpleName(), activity.getString(R.string.reddy_ice), null, null, null);
        updateBottomBar(activity, new HomeFragment().getClass().getSimpleName());
    }*/


}
