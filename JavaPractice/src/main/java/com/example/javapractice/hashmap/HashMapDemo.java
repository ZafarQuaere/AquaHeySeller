package com.example.javapractice.hashmap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class HashMapDemo {
    public static String URL_ENDPOINT = "http://rapture-ws-stage.intelematics.com.au/LEXUS_TEST/wrs/LexusComm/getData/";
    public static String URL_DD_ENDPOINT = "https://rapture-ws-stage.intelematics.com.au/LEXUS_TEST/wrs/LexusComm/DestinationDownload/getData/";
    public static String URL_FS_ENDPOINT = "https://rapture-ws-stage.intelematics.com.au/LEXUS_TEST/wrs/LexusComm/FuelSearch/getData/";
    public static String URL_LS_ENDPOINT = "https://rapture-ws-stage.intelematics.com.au/LEXUS_TEST/wrs/LexusComm/LocalSearch/getData/";
    public static String URL_PS_ENDPOINT = "https://rapture-ws-stage.intelematics.com.au/LEXUS_TEST/wrs/LexusComm/PollingService/getData/";
    public static String URL_CONCIERGE_ENDPOINT = "https://rapture-ws-stage.intelematics.com.au/LEXUS_TEST/wrs/LexusComm/Concierge/getData/";
    public static String URL_WEATHER_ENDPOINT   = "https://rapture-ws-stage.intelematics.com.au/LEXUS_TEST/wrs/LexusComm/Weather/getData/";

    public static String KEY_ENDPOINT = "tml_key_endpoint";
    public static String KEY_ENDPOINT_DD = "tml_key_endpoint_dd";
    public static String KEY_ENDPOINT_FS = "tml_key_endpoint_fs";
    public static String KEY_ENDPOINT_LS = "tml_key_endpoint_ls";
    public static String KEY_ENDPOINT_PS = "tml_key_endpoint_ps";
    public static String KEY_ENDPOINT_CONCIERGE = "tml_key_endpoint_concierge";
    public static String KEY_ENDPOINT_WEATHER = "tml_key_endpoint_weather";

    public static void main(String arg[]) {
        HashMapDemo demo = new HashMapDemo();
        demo.storeHmData();

        display(KEY_ENDPOINT);
    }

    private static void display(String keyEndpoint) {
        switch (keyEndpoint){

        }
    }

    private void storeHmData() {
        HashMap map = new HashMap();
            map.put("URL_ENDPOINT",URL_ENDPOINT);
            map.put("URL_DD_ENDPOINT",URL_DD_ENDPOINT);
            map.put("URL_FS_ENDPOINT",URL_FS_ENDPOINT);
            map.put("URL_LS_ENDPOINT",URL_LS_ENDPOINT);
            map.put("URL_PS_ENDPOINT",URL_PS_ENDPOINT);
            map.put("URL_CONCIERGE_ENDPOINT",URL_CONCIERGE_ENDPOINT);
            map.put("URL_WEATHER_ENDPOINT",URL_WEATHER_ENDPOINT);

        fetchDataFromHm(map);
    }

    private void fetchDataFromHm(HashMap map) {

        System.out.println(map.get("URL_ENDPOINT"));
        System.out.println(map.get("URL_DD_ENDPOINT"));
        System.out.println(map.get("URL_FS_ENDPOINT"));
        System.out.println(map.get("URL_LS_ENDPOINT"));
        System.out.println(map.get("URL_PS_ENDPOINT"));

        Set set = map.keySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()){
        }
    }
}
