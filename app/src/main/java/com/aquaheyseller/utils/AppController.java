package com.aquaheyseller.utils;

import android.app.Application;

import com.aquaheyseller.utils.validator.Validator;
import com.aquaheyseller.utils.validator.ValidatorImpl;

public class AppController extends Application {

    public static AppController mInstance;
    private Validator mValidator;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        // create validator
        mValidator = new ValidatorImpl();
    }

    public static Validator getValidator() {
        return mInstance.mValidator;
    }
}
