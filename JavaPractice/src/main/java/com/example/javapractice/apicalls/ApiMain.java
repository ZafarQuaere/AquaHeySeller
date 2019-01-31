package com.example.javapractice.apicalls;

import java.io.IOException;

public class ApiMain {
    public static void main(String arg[]){

     //   callGetApi();
        callPostApi();
        callLoginApi();
    }

    private static void callLoginApi() {
        PostExample postExample = new PostExample();
        String response = postExample.doPostApiCall(ApiUrls.baseUrl + ApiUrls.registerUrl, createLoginBody());
    }

    private static String createLoginBody() {
        /*{
            "email": "peter@klaven",
                "password": "cityslicka"
        }*/
        return null;
    }

    private static void callPostApi() {
        PostExample postExample = new PostExample();
        String response = postExample.doPostApiCall(ApiUrls.baseUrl + ApiUrls.registerUrl, createBody());
        System.out.println("Post Response : "+response);

        String json = bowlingJson("Zafar", "Sami");
        String response1 = null;
        try {
            response1 = postExample.post("http://www.roundsapp.com/post", json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Post Response : "+response1);

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
        System.out.println("Get Response : "+response);
    }

    private static String createBody() {


        return "{'email':'peter@klaven',"
                +"'password':'cityslicka'"
                +"}";
    }

    static String bowlingJson(String player1, String player2) {
        return "{'winCondition':'HIGH_SCORE',"
                + "'name':'Bowling',"
                + "'round':4,"
                + "'lastSaved':1367702411696,"
                + "'dateStarted':1367702378785,"
                + "'players':["
                + "{'name':'" + player1 + "','history':[10,8,6,7,8],'color':-13388315,'total':39},"
                + "{'name':'" + player2 + "','history':[6,10,5,10,10],'color':-48060,'total':41}"
                + "]}";
    }
}
