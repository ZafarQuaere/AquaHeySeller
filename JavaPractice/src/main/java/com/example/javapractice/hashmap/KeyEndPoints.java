package com.example.javapractice.hashmap;

public enum KeyEndPoints {

    KEY_ENDPOINT("tml_key_endpoint"),
    KEY_ENDPOINT_DD("tml_key_endpoint_dd"),
    KEY_ENDPOINT_FS("tml_key_endpoint_fs"),
    KEY_ENDPOINT_LS("tml_key_endpoint_ls"),
    KEY_ENDPOINT_PS("tml_key_endpoint_ps"),
    KEY_ENDPOINT_CONCIERGE("tml_key_endpoint_concierge"),
    KEY_ENDPOINT_WEATHER("tml_key_endpoint_weather");

    private String endPoint;


    KeyEndPoints(String ednPoint) {
        this.endPoint = ednPoint;
    }

    public String getUrl() {
        return endPoint;
    }
}
