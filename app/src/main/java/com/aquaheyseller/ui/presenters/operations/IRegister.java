package com.aquaheyseller.ui.presenters.operations;

import com.aquaheyseller.network_call.request_model.Register;

public interface IRegister {

    void doRegister();
    void onValidationError(String msg);
    void callApi(Register register);
}
