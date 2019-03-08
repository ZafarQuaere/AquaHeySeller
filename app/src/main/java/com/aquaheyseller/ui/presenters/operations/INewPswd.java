package com.aquaheyseller.ui.presenters.operations;

public interface INewPswd {

    void changePswd();
    void onValidationError(String msg);
    void callApi(String password);
}
