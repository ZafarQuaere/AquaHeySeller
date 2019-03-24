package com.aquaheyseller.ui.presenters.operations;


import com.aquaheyseller.network_call.request_model.AddressData;

public interface ISellerAddress {

    void saveAddress();
    void onValidationError(String msg);
    void callApi(AddressData addressData);
}
