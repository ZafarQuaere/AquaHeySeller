package com.aquaheyseller.utils;

public class AppConstant {

    // Splash screen timer
    public static int SPLASH_TIME_OUT = 2000;
    public static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";
    public static final String TAG = "AquaHey";


    public static final String URL_BASE = "http://www.aquahey.com";
    public static final String URL_REGISTER = "/signup";
    public static final String URL_LOGIN = "/login";
    public static final String URL_NEW_BRAND = "/new-brand"; //(brandname,banner_image)
    public static final String URL_DEALER_ADDRESS = "/insert-dealer-address"; //(brandname,banner_image)
    public static final String URL_INSERT_DELAER = "/insert-dealer"; //(dName,mobile,brandIds,rating,addressId)
    public static final String URL_INSERT_PRODUCT = "/insert-product"; //{pName,imagePath,dealerId,name,price,productType,isBrand}
    public static final String URL_VERIFY_MOBILE = "/mobile-verify?mobile=";
    public static final String URL_OTP_SERVICE = "/otp_service?mobile=";
    public static final String URL_CHANGE_PASSWORD = "/change-password?mobile=";
    public static final String URL_CP_PASSWORD = "&password=";


    public static final String baseUrl = "https://reqres.in";
    public static final String listUrl = "/api/users?page=";
    public static final String userByIdUrl = "/api/users/2";
    public static final String registerUrl = "/api/register";
    public static final String loginUrl = "/api/login";

}
