package com.aquaheyseller.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aquaheyseller.R;
import com.aquaheyseller.ui.fragments.HomeFragment;
import com.aquaheyseller.ui.fragments.ListingsFragment;
import com.aquaheyseller.ui.fragments.OrdersFragment;
import com.aquaheyseller.ui.interfaces.DialogButtonClick;
import com.aquaheyseller.utils.storage.AppSharedPrefs;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.zip.GZIPInputStream;


public class Utils {


    public static void hideKeyboard(Context context) {
        showKeyboard(context, false);
    }

    public static void showKeyboard(Context context) {
        showKeyboard(context, true);
    }

    private static void showKeyboard(Context context, boolean show) {
        if (!(context instanceof Activity)) {
            return;
        }
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context
                .INPUT_METHOD_SERVICE);
        View currentFocus = ((Activity) context).getCurrentFocus();
        if (currentFocus == null) {
            return;
        }
        if (show) {
            inputManager.showSoftInput(currentFocus, InputMethodManager.SHOW_IMPLICIT);
        } else {
            inputManager.hideSoftInputFromWindow(currentFocus.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static boolean isValidMobileNumber(String mobileNo) {
        return Patterns.PHONE.matcher(mobileNo)
                .matches();
    }

    public static boolean isValidEmail(String email) {
        /*String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();*/
         return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();



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

   public static void updateActionBar(final Context activity, final String className,
                                       String dynamicTitle, Object customHeaderData,  final DialogButtonClick actionBarItemClickListener) {

        if (activity == null)
            return;

        LogUtils.DEBUG(AppConstant.TAG + " Utils >> updateActionBar() called : " + className + "/" + dynamicTitle);

        RelativeLayout toolbarLayout = (RelativeLayout) ((Activity)activity).findViewById(R.id.lytToolbar);
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
                    ((Activity)activity).onBackPressed();
                }
            });
        }

        else if (className.equals(new ListingsFragment().getClass().getSimpleName())) {
            textBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity)activity).onBackPressed();
                }
            });
        }


    }

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

    public static String loadJSONFromAsset(Context context, String jsonFileName)
            throws IOException {

        AssetManager manager = context.getAssets();
        InputStream is = manager.open(jsonFileName);

        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        return new String(buffer, "UTF-8");
    }

    public static boolean isNetworkEnabled(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }

        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return true;
        }

        return false;
    }


}
