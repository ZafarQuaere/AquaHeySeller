package com.aquaheyseller.network_call.response_model.product_list;

import java.util.ArrayList;

public class MyProductsData {

    private ArrayList<Data> data;

    private String message;

    private String status;


    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
