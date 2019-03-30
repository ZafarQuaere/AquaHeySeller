package com.aquaheyseller.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aquaheyseller.R;
import com.aquaheyseller.network_call.response_model.orders.OrderData;
import com.aquaheyseller.network_call.response_model.orders.OrderList;
import com.aquaheyseller.ui.adapters.OrdersRecylcerAdapter;
import com.aquaheyseller.utils.LogUtils;
import com.aquaheyseller.utils.Utils;
import com.aquaheyseller.utils.parser.ParseManager;

import java.util.ArrayList;


public class PendingsFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";

    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private Context mContext;
    private RecyclerView recyclerPendings;
    private LinearLayoutManager layoutManager;
    private TextView emptyTextView;
    private OrderData orderData;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pendings, container, false);

        String pendingOrderData = Utils.getPendingOrderData(mContext);
        LogUtils.DEBUG("pendingOrderData >>>> " + pendingOrderData);
        orderData = ParseManager.getInstance().fromJSON(pendingOrderData, OrderData.class);
        try {
            LogUtils.DEBUG(">>>>>>>>>> status : " + orderData.getStatus() + " " + orderData.getData().get(0).getAddressId() + " " + orderData.getData().get(0).getOrderDate() + "\n"
                    + orderData.getData().get(0).getDeliverDate() + " " + orderData.getData().get(0).getPaymentId() + " ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        initUI(view);

        return view;
    }

    private void initUI(View view) {
        emptyTextView = (TextView) view.findViewById(R.id.emptyTextView);
        recyclerPendings = (RecyclerView) view.findViewById(R.id.recyclerPendings);
        recyclerPendings.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerPendings.setLayoutManager(layoutManager);
        recyclerPendings.setItemAnimator(new DefaultItemAnimator());
        updateList();
    }

    private void updateList() {
        OrdersRecylcerAdapter adapter = new OrdersRecylcerAdapter((ArrayList<OrderList>) orderData.getData(), new OnListFragmentInteractionListener() {

            @Override
            public void onListFragmentInteraction(OrderList item) {
                LogUtils.showToast(mContext,item.getAmount());
            }
        });
        recyclerPendings.setAdapter(adapter);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            /*throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");*/
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(OrderList item);
    }
}
