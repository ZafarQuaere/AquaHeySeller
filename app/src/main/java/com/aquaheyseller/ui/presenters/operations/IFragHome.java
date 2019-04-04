package com.aquaheyseller.ui.presenters.operations;


import com.aquaheyseller.network_call.response_model.home.Sales;

import java.util.List;

public interface IFragHome {
    void updateTodaySalesData(List<Sales> todaySalesData);
    void updateTotalSalesData(List<Sales> totalSalesData);
}
