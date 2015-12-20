package com.example.photo500px;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jakewharton.rxbinding.support.v4.view.RxViewPager;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity {

    private final static String EXTRA_TYPE_NEW = "fresh_today";
    private final static String EXTRA_TYPE_POPULAR = "popular";

    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.sliding_tabs)
    TabLayout tabLayout;

    private PublishSubject<Integer> actionBarItemClickSubject = PublishSubject.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        RxViewPager.pageSelections(viewPager).subscribe(adapter.getOnPageChangeAction());
        actionBarItemClickSubject.subscribe(adapter.getOnActionBarItemClickAction());
        adapter.addFragment(MainTabFragment.newInstance(EXTRA_TYPE_NEW), getString(R.string.tab_new));
        adapter.addFragment(MainTabFragment.newInstance(EXTRA_TYPE_POPULAR), getString(R.string.tab_popular));
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            actionBarItemClickSubject.onNext(id);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
