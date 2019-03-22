package com.aquaheyseller.ui.presenters.operations;


public interface IFragListing {

    void addProduct();
    void onValidationError(String msg);
    void callApi();
}
