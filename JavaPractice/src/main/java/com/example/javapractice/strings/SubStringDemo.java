package com.example.javapractice.strings;


public class SubStringDemo {
    public static String otpText = "5018 is your AQUAHEY OTP";

    static String otpData = "{\"total_sms\":1,\"duplicates_removed\":0,\"optedout_removed\":0,\"invalid_removed\":0,\"total_sent\":1,\"credits_deducted\":1,\"user_id\":\"9107\",\n" +
            "\"admin_id\":\"1\",\"campaign_id\":9303,\"sms_shoot_id\":\"inspiring5c798f5539efc\",\"submission_time\":\"2019-03-02 01:30:21\",\"per_sms_charge\":1,\"route_id\":20,\n" +
            "\"sms_text\":\"5018 is your AQUAHEY OTP\",\"sent_via\":\"HTTP-API\",\"status\":\"success\",\"msg\":\"Your message was sent successfully\"}";
    public static void main(String arg[]){

        System.out.println("OTP Text is  : "+otpText);
        System.out.println("OTP is :: "+otpText.substring(0,4));

    }
}
