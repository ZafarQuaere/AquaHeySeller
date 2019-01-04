package com.aquaheyseller.ui.presenters.operations;


public interface IBaseDataOperation {

    void onPreRequest(String message);

    void onRequestFinish();
}
