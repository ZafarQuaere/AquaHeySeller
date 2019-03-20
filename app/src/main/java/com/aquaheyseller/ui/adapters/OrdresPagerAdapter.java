package com.aquaheyseller.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.aquaheyseller.R;
import com.aquaheyseller.ui.fragments.CompletedFragment;
import com.aquaheyseller.ui.fragments.PendingFragment;
import com.aquaheyseller.ui.fragments.ShippedFragment;

public class OrdresPagerAdapter extends FragmentStatePagerAdapter {

    private int mTabCount;
    private Context mContext;

    public OrdresPagerAdapter(Context context, FragmentManager childFragmentManager, int tabCount) {
        super(childFragmentManager);
        mTabCount = tabCount;
        mContext = context;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                PendingFragment tab1 = new PendingFragment();
                return tab1;
            case 1:
                ShippedFragment tab2 = new ShippedFragment();
                return tab2;
            case 2:
                CompletedFragment tab3 = new CompletedFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mTabCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = mContext.getString(R.string.new_order);
        } else if (position == 1) {
            title = mContext.getString(R.string.pending);
        } else if (position == 2) {
            title = mContext.getString(R.string.completed);
        }
        return title;
    }
}
