package com.aquaheyseller.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aquaheyseller.R;
import com.aquaheyseller.network_call.response_model.orders.OrderList;
import com.aquaheyseller.ui.fragments.PendingsFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OrdersRecylcerAdapter extends RecyclerView.Adapter<OrdersRecylcerAdapter.ViewHolder> {

    private final List<OrderList> mValues;
    private final PendingsFragment.OnListFragmentInteractionListener mListener;

    public OrdersRecylcerAdapter(ArrayList<OrderList> items, PendingsFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getDeliverDate());
        holder.mContentView.setText(mValues.get(position).getAmount());
        //Picasso.get().load(mValues.get(position).getImagePath()).into(holder.imgItem);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mIdView;
        final TextView mContentView;
        final ImageView imgItem;
        OrderList mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
            imgItem = (ImageView) view.findViewById(R.id.imgItem);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
