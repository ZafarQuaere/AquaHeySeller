package com.aquaheyseller.network_call;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.aquaheyseller.R;
import com.aquaheyseller.utils.AppConstant;
import com.aquaheyseller.utils.LogUtils;
import com.aquaheyseller.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;



public class MyJsonObjectRequest extends JsonObjectRequest {

    private static final String SET_COOKIE_KEY = "Set-Cookie";
    private static final String COOKIE_KEY = "Cookie";
    private static final String JSESSIONID_KEY = "JSESSIONID";
    private SharedPreferences _preferences = null;
    private Context context;
    private boolean isGZIPCompressed;

    public MyJsonObjectRequest(boolean isGZIPCompressed, Context context, int method, String url,
                               JSONObject requestBody, Response.Listener<JSONObject> listener,
                               Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);


        //to bypass server side reading if server is under development
        if (context.getResources().getBoolean(R.bool.bool_read_from_local)) {
           /* String fileName = null;
            if(url.contains(context.getString(R.string.URL_HOME_PASS_BANNER)))
            {
                fileName = "GetHomePassBanner.txt";
            }
            else if(url.contains(context.getString(R.string.URL_FPO_GET_PASS)))
            {
                fileName = "FpoGetPass.txt";
            }
            else if(url.contains(context.getString(R.string.URL_SESSION_ID)))
            {
                fileName = "GetSessionId.txt";
            }
            else if(url.contains(context.getString(R.string.URL_LOGIN)))
            {
                fileName = "setLoginDetails.txt";
            }
            else if(url.contains(context.getString(R.string.URL_ADD_USER)))
            {
                fileName = "AddUserDetails.txt";
            }
            else if(url.contains(context.getString(R.string.URL_COUNTRY_EXT_LIST)))
            {
                fileName = "CountryExtList.txt";
            }
            else if(url.contains(context.getString(R.string.URL_FPO_MOBILE_REVIEW_PASS)))
            {
                fileName = "FpoMobileReviewPass.txt";
            }
            else
            {
                Utils.ERROR("No file name found for api : " + url + ", please update the asset folder");
                return;
            }


            try {
                listener.onResponse(new JSONObject(Utils.readFromAssets(context, fileName)));
            } catch (JSONException e) {
                e.printStackTrace();
            }*/

        } else {
           // LogUtil.DEBUG(AppConstant.TAG+" json request : " + requestBody.toString());
            LogUtils.DEBUG(AppConstant.TAG+" url : " + url);
        }

        // Utils.isValidEmailAddress((Activity) context, Utils.getUsername((Activity) context));
        //for timeout add retry policy
        setRetryPolicy(new DefaultRetryPolicy((int) context.getResources().getInteger(R.integer.value_request_timeout),
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        this.context = context;
        _preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.isGZIPCompressed = isGZIPCompressed;


    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {


        LogUtils.DEBUG(AppConstant.TAG+" parseNetworkResponse >> " + response.statusCode);
        if (context.getResources().getBoolean(R.bool.bool_read_from_local)) {
            //if reading from local then return null to handle call
            return Response.success(null, HttpHeaderParser.parseCacheHeaders(response));
        }


        //LogUtil.DEBUG("parseNetworkResponse >> " + response.data);

        try {
            LogUtils.DEBUG(AppConstant.TAG+" [raw json]: " + (new String(response.data)));


            checkSessionCookie(response);
            if (!response.notModified) {// Added for 304 response
                String jsonString = new String(isGZIPCompressed ? Utils.decompress(response.data) : response.data,
                        HttpHeaderParser.parseCharset(response.headers));
                LogUtils.DEBUG(AppConstant.TAG+" header content-type : " + HttpHeaderParser.parseCharset(response.headers));
                if (jsonString == null || jsonString.length() < 3) {
                    Response.error(new ParseError(new NullPointerException()));
                }

                return Response.success(new JSONObject(jsonString),
                        HttpHeaderParser.parseCacheHeaders(response));
            } else // Added for 304 response
                return Response.error(new ParseError(new NullPointerException()));
        } catch (UnsupportedEncodingException e) {
            LogUtils.DEBUG(AppConstant.TAG+" parseNetworkResponse >> UnsupportedEncodingException " + response.statusCode);
            if (response.statusCode == 200)// Added for 200 response
                return Response.error(new ParseError(new NullPointerException()));
            else
                return Response.error(new ParseError(e));
        } catch (JSONException je) {
            LogUtils.DEBUG(AppConstant.TAG+" parseNetworkResponse >> JSONException " + response.statusCode);
            if (response.statusCode == 200)// Added for 200 response
                return Response.error(new ParseError(new NullPointerException()));
            else
                return Response.error(new ParseError(je));
        } catch (IOException e) {
            return Response.error(new ParseError(e));
        }
    }

    /**
     * Passing some request headers
     */
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();

        if (headers == null
                || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<String, String>();
        }

        addSessionCookie(headers);

        return headers;
    }

    /**
     * save header values in shared prefs
     *
     * @param response
     */
    public final void checkSessionCookie(NetworkResponse response) {

        Map<String, String> headers = response.headers;
        //{Content-Type=application/json;charset=UTF-8, Date=Tue, 21 Jun 2016 10:53:26 GMT, Server=Apache-Coyote/1.1, Set-Cookie=JSESSIONID=C155F33BBD4FB7FC140B71BACEBFB24C.sanjay; Path=/; Secure; HttpOnly, Transfer-Encoding=chunked, Vary=Accept-Encoding, X-Android-Received-Millis=1466506401400, X-Android-Response-Source=NETWORK 200, X-Android-Selected-Transport=http/1.1, X-Android-Sent-Millis=1466506400844}
        LogUtils.DEBUG(AppConstant.TAG+" checkSessionCookie() called >> header is : " + headers);

        try {
            JSONObject object = new JSONObject(new String(response.data));

            SharedPreferences.Editor prefEditor = _preferences.edit();
            prefEditor.putString(JSESSIONID_KEY, object.getString("sessionId"));
            prefEditor.commit();

        } catch (Exception e) {

        }

        for (String key : headers.keySet()) {
            LogUtils.DEBUG(AppConstant.TAG+" " + key + "," + headers.get(key));
        }
        if (headers.containsKey(SET_COOKIE_KEY) && headers.get(SET_COOKIE_KEY).startsWith(JSESSIONID_KEY)) {
            String cookie = headers.get(SET_COOKIE_KEY);
            if (cookie.length() > 0) {
                String[] splitCookie = cookie.split(";");
                String[] splitSessionId = splitCookie[0].split("=");
                cookie = splitSessionId[1];

                LogUtils.DEBUG(AppConstant.TAG+" cookie >> " + cookie);
                SharedPreferences.Editor prefEditor = _preferences.edit();
                prefEditor.putString(JSESSIONID_KEY, cookie);
                prefEditor.commit();
            }
        }
    }

    /**
     * add saved headers in request call
     *
     * @param headers
     */
    public final void addSessionCookie(Map<String, String> headers) {
        String sessionId = _preferences.getString(JSESSIONID_KEY, "");
        if (sessionId.length() > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append(JSESSIONID_KEY);
            builder.append("=");
            builder.append(sessionId);
            if (headers.containsKey(COOKIE_KEY)) {
                builder.append("; ");
                builder.append(headers.get(COOKIE_KEY));
            }
            headers.put(COOKIE_KEY, builder.toString());
            LogUtils.DEBUG(AppConstant.TAG+" cookie key, value : " + COOKIE_KEY + ", " + builder.toString());
        }
    }
}
