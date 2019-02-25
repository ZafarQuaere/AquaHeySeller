package com.aquaheyseller.ui.presenters.operations;


public interface IAddSellerAddress {

    void saveAddress();
    void onValidationError(String msg);
    void callApi();
}
