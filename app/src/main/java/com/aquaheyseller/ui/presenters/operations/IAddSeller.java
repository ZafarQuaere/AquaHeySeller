package com.aquaheyseller.ui.presenters.operations;



public interface IAddSeller {

    void addSeller();
    void onValidationError(String msg);
    void callApi();
}
