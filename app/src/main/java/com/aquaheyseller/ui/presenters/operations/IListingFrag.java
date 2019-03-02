package com.aquaheyseller.ui.presenters.operations;


import com.aquaheyseller.network_call.request_model.Address;

public interface IListingFrag {

    void addProduct();
    void onValidationError(String msg);
    void callApi();
}
