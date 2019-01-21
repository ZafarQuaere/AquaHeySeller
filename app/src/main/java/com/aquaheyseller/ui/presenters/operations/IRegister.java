package com.aquaheyseller.ui.presenters.operations;

import com.aquaheyseller.network_call.request.Register;

public interface IRegister {

    void doRegister();
    void onValidationError(String msg);
    void callApi(Register register);
}
