
package com.aquaheyseller.network_call.response_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginPojo {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("totalItem")
    @Expose
    private Integer totalItem;
    @SerializedName("resultArray")
    @Expose
    private ResultArray resultArray;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(Integer totalItem) {
        this.totalItem = totalItem;
    }

    public ResultArray getResultArray() {
        return resultArray;
    }

    public void setResultArray(ResultArray resultArray) {
        this.resultArray = resultArray;
    }

}
