package com.cui.mymineclearance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * @author CHL1963
 * @version 1.0
 * @description: TODO
 * @date 2022/5/11 15:52
 */
public class MyFragmentTitleAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;

    public MyFragmentTitleAdapter(@NonNull FragmentManager fm, ArrayList<Fragment> fragments, ArrayList<String> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments == null ? null : fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles == null ? " " : titles.get(position);

    }
}
