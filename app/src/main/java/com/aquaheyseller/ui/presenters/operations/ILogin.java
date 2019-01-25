package com.aquaheyseller.ui.presenters.operations;

public interface ILogin {

    void doLogin();
    void onValidationError(String msg);
    void callLoginApi(String userId,String pswd);
}
