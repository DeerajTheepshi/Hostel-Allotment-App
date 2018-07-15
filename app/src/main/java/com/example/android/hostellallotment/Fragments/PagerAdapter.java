package com.example.android.hostellallotment.Fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

    String[] title = new String[]{"Request","Options"};

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new makeARequest();
        }
        else if(position==1){
            return new match_fragment();
        }
        else
            return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0)
            return title[0];
        else if(position==1)
            return title[1];
        else
            return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
