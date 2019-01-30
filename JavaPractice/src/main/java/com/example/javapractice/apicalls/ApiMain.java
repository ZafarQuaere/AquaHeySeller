package com.example.javapractice.apicalls;

import java.io.IOException;

public class ApiMain {
    public static void main(String arg[]){

        callGetApi();
        callPostApi();
    }

    private static void callPostApi() {

    }

    private static void callGetApi() {
        GetExample example = new GetExample();
        String response = null;
        try {
            // response = example.doGetApiCall("https://raw.github.com/square/okhttp/master/README.md");
            response = example.doGetApiCall(ApiUrls.baseUrl+ApiUrls.listUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(response);
    }
}
