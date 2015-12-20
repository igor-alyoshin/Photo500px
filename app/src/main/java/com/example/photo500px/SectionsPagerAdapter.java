package com.example.photo500px;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

/**
 * Created by igor on 05.12.15.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private List<MainTabFragment> fragmentList = new ArrayList<>();
    private List<String> titlesList = new ArrayList<>();
    private int currentPosition = 0;

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
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
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titlesList.get(position);
    }

    public Action1<Integer> getOnActionBarItemClickAction() {
        return id -> fragmentList.get(currentPosition).onActionBarClick(id);
    }

    public Action1<Integer> getOnPageChangeAction() {
        return position -> currentPosition = position;
    }
}
