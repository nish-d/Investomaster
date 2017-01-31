package com.nishitadutta.investomaster;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nishitadutta.investomaster.compare.CompareFragment;
import com.nishitadutta.investomaster.news.NewsFragment;

import com.nishitadutta.investomaster.stocks.StockFragment;

/**
 * Created by Nishita on 07-01-2017.
 */

public class CustomPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "CustomPagerAdapter";
    private final String[] tab_names = {"News", "Stocks", "Favorite"};
    Context context;

    public CustomPagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NewsFragment();
            case 1:
                return new StockFragment();
            case 2:
                //return new StockFragment();
                return new CompareFragment();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tab_names[position];
    }


    @Override
    public int getCount() {
        return tab_names.length;
    }
}
