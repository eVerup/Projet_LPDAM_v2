package com.example.lucasabadie.projetandroidtp;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Activity_Game extends AppCompatActivity implements SelectorFragment.OnActionListener {

    ViewPager mViewPager;
    ExamplePagerAdapter mExamplePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final ActionBar actionBar = getSupportActionBar();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Remove title bar
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mExamplePagerAdapter = new ExamplePagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mExamplePagerAdapter);

        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                // show the given tab
                mViewPager.setCurrentItem(tab.getPosition());
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // hide the given tab
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // probably ignore this event
            }
        };

        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // Add 2 tabs, specifying the tab’s text and TabListener
        actionBar.addTab(
                actionBar.newTab()
                        .setText("Selector")
                        .setTabListener(tabListener));
        actionBar.addTab(
                actionBar.newTab()
                        .setText("Game")
                        .setTabListener(tabListener));

        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.
                        getSupportActionBar().setSelectedNavigationItem(position);
                    }
                });

    }

    @Override
    public void onAction(Bitmap object, String color, int numObject, int speed){
        ((GameFragment)mExamplePagerAdapter.getItem(1)).changerObject(object);
        ((GameFragment)mExamplePagerAdapter.getItem(1)).changerColor(color);
        ((GameFragment)mExamplePagerAdapter.getItem(1)).changerNombre(numObject);
        ((GameFragment)mExamplePagerAdapter.getItem(1)).changerSpeed(speed);

        ((GameFragment)mExamplePagerAdapter.getItem(1)).Game();
    }
}

class ExamplePagerAdapter extends FragmentStatePagerAdapter {

    Fragment f1, f2;

    public ExamplePagerAdapter(FragmentManager fm) {
        super(fm);

        f1 = new SelectorFragment();
        f2 = new GameFragment();
    }
    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;

        switch( i ) {
            case 0 :  {
                fragment = f1;
                break;
            }
            case 1 : {
                fragment = f2;
                break;
            }
        }

        return fragment;
    }
    @Override
    public int getCount() {
        //le nombre d’onglet
        return 2;
    }
}
