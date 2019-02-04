package com.aquaheyseller.ui.presenters.operations;

public interface IFrgtPswd {
    void submitMobile();
    void onValidationError(String msg);
    void callSubmitMobileApi(String mobile);
}
