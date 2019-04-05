package com.aquaheyseller.ui.presenters.operations;


import com.aquaheyseller.network_call.response_model.product_list.MyProductsData;

public interface IFragListing {

    void onResponseSuccess();
    void onResponseFailure(String msg);
    void editItem();

    void updateList(MyProductsData data);
}
