package com.example.javapractice.apicalls;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginApi {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

   public String doLoginApiCall(String url, String body){
        RequestBody requestBody = RequestBody.create(JSON,body);
        System.out.println("Url : "+url +"\nBody : "+body);
        Request request = new Request.Builder().
                url(url).
                post(requestBody).
                build();

        try {

            Response response = client.newCall(request).execute();
            return response.body().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "there is something wrong";
    }
}
