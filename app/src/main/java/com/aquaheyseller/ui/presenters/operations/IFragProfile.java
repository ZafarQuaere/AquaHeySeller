package com.aquaheyseller.ui.presenters.operations;


import com.aquaheyseller.network_call.response_model.login.Data;

public interface IFragProfile {

    void updateUI(Data data);
    void onValidationError(String msg);
    void callApi();
}
