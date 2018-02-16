package com.example.mcb.genesisapp.Layers.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Adapter for the Viewpager in FBaseList
 * Created by mcb on 14.02.16.
 */
public class BaseListViewPagerAdapter extends FragmentPagerAdapter {


    /**
     *  A list of {@link Fragment} which serves as the data for this
     *  adapter.
     */
    private List<Fragment> pagerFragments;

    public BaseListViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        pagerFragments =fragments;
    }

    public List<Fragment> getPagerFragments() {
        return pagerFragments;
    }

    public void setPagerFragments(final List<Fragment> pagerFragments) {
        this.pagerFragments = pagerFragments;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
       // return (pagerFragments != null && pagerFragments.size() > position) ?
         //       pagerFragments.get(position) : null;
        return pagerFragments.get(position);
    }

    @Override
    public int getCount() {
        return (pagerFragments != null) ? pagerFragments.size() : 0;
    }

//    @Override
//    public int getItemPosition(Object object) {
//
//        return POSITION_NONE;
//    }



    @Override
    public CharSequence getPageTitle(int position) {

        switch(position){
            case 0: return "Creator";
            case 1: return "Wallet";
            case 2: return "User Info";
            case 3: return "Browser";
            default: return "default";
        }

    }
}

