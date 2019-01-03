package com.aquaheyseller.ui.presenters.operations;

public interface ILogin {

    void doLogin();

    boolean validateUserName();

    boolean validatePassword();
}
