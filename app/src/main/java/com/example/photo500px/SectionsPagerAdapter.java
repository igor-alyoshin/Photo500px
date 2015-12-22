package com.example.photo500px;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

/**
 * Created by igor on 05.12.15.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final String FRAGMENTS_UNIQUE_SAVING_KEY = "FRAGMENTS_UNIQUE_SAVING_KEY";

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titlesList = new ArrayList<>();
    private int currentPosition = 0;
    private FragmentManager fm;

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
    }

    public void addFragment(MainTabFragment fragment, String title) {
        fragmentList.add(fragment);
        titlesList.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        super.restoreState(state, loader);
        Bundle bundle = (Bundle) state;
        fragmentList.clear();
        for (String key : bundle.keySet()) {
            if (key.startsWith(FRAGMENTS_UNIQUE_SAVING_KEY)) {
                Fragment f = fm.getFragment(bundle, key);
                fragmentList.add(f);
            }
        }
    }

    @Override
    public Parcelable saveState() {
        Bundle state = (Bundle) super.saveState();
        if (state == null)
            state = new Bundle();

        for (int i = 0; i < fragmentList.size(); i++) {
            fm.putFragment(state, FRAGMENTS_UNIQUE_SAVING_KEY + i, fragmentList.get(i));
        }
        return state;
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titlesList.get(position);
    }

    public Action1<MenuItem> getOnActionBarItemClickAction() {
        return menuItem -> fragmentList.get(currentPosition).onOptionsItemSelected(menuItem);
    }

    public Action1<Integer> getOnPageChangeAction() {
        return position -> currentPosition = position;
    }
}
