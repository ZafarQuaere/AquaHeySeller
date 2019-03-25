package com.aquaheyseller.ui.presenters.operations;


import org.json.JSONArray;

public interface IFragListing {

    void onResponseSuccess();
    void onResponseFailure(String msg);
    void editItem();

    void updateList(JSONArray data);
}
