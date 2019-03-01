package com.aquaheyseller.ui.presenters.operations;


import com.aquaheyseller.network_call.request_model.Address;

public interface ISellerAddress {

    void saveAddress();
    void onValidationError(String msg);
    void callApi(Address address);
}
