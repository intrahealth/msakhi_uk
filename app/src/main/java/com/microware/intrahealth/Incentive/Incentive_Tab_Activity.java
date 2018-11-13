package com.microware.intrahealth.Incentive;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu.OnMenuItemClickListener;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.microware.intrahealth.Dashboard_Activity;
import com.microware.intrahealth.Global;
import com.microware.intrahealth.IncentiveFragment.AshaReport;
import com.microware.intrahealth.IncentiveFragment.Incentive_BB;
import com.microware.intrahealth.R;
import com.microware.intrahealth.dataprovider.DataProvider;

public class Incentive_Tab_Activity extends FragmentActivity implements
        ActionBar.TabListener, OnMenuItemClickListener {

    ActionBar mActionBar;
    ViewPager mViewPager;
    ImageView imgprevious, imgnext;
    DataProvider dataProvider;
    Global global;
    @SuppressLint("NewApi")
    ConnectivityManager connMgrCheckConnection;
    NetworkInfo networkInfoCheckConnection;
    int value = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Main view typically contains just the ViewPager
        setContentView(R.layout.intrahealth_activity);
        dataProvider = new DataProvider(this);
        global = (Global) getApplication();
        connMgrCheckConnection = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfoCheckConnection = connMgrCheckConnection
                .getActiveNetworkInfo();

        global.setIncentiveSurveyGUID("");
        imgprevious = (ImageView) findViewById(R.id.imgprevious);
        imgnext = (ImageView) findViewById(R.id.imgnext);
        // Tell the ActionBar to display tabs
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            value = extra.getInt("flag");
        }
        mActionBar = getActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mActionBar.setHomeButtonEnabled(false);

        // Set up the tab navigation
        PagerAdapter mAdapter = new TabsPagerAdapter(
                getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        // Make swiping set the view
        mViewPager.setAdapter(mAdapter);
        // Make swiping update the tab view
        mViewPager.setOnPageChangeListener(new SwipedListener());

        // Now that listeners are in place we can safely add the tabs
        mActionBar.addTab(mActionBar.newTab()
                .setText(getString(R.string.Report))
                .setTabListener(this));
        mActionBar.addTab(mActionBar.newTab()
                .setText(R.string.DataEntry).setTabListener(this));

        imgnext.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (mViewPager.getCurrentItem() < 1) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                }
            }
        });
        imgprevious.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (mViewPager.getCurrentItem() > 0) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
                }
            }
        });
        // trackevent();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, global.getsGlobalAshaName()).setShowAsAction(
                MenuItem.SHOW_AS_ACTION_IF_ROOM);
        // menu.add(0, 1, 0, "History").setIcon(R.drawable.logout1)
        // .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        //

        return true;
    }

    @SuppressWarnings("unused")
    public void ShowCustomToast(String sMessage) {
        LayoutInflater inflater = getLayoutInflater();


    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // switch (item.getItemId()) {

        return true;
    }

    public void onTabSelected(final Tab tab, android.app.FragmentTransaction ft) {
        System.out.println("MainActivity.onTabSelected()");

        mViewPager.setCurrentItem(tab.getPosition());


    }

    public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
        System.out.println("MainActivity.onTabUnselected()");
    }

    public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
        System.out.println("MainActivity.onTabReselected()");
    }

    class TabsPagerAdapter extends FragmentPagerAdapter {

        public TabsPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        /**
         * Consider caching instead of always instantiating; typical use case
         * will require all three
         */

        public Fragment getItem(int index) {
            switch (index) {
                case 0:
                    return new AshaReport();
                case 1:
                    return new Incentive_BB();
            }
            return null;
        }

        public int getCount() {
            return 2;
        }
    }

    class SwipedListener implements ViewPager.OnPageChangeListener {

        public void onPageSelected(int position) {
            // on changing the page
            // make respected tab selected
            // mActionBar.setSelectedNavigationItem(position);
            final int pos = position;

            swapscreen(position);


            imgnext.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    swapscreen(pos + 1);
                }
            });

            imgprevious.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    swapscreen(pos - 1);
                }
            });
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // not used
        }

        public void onPageScrollStateChanged(int arg0) {
            // not used
        }
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        if (networkInfoCheckConnection != null
                && networkInfoCheckConnection.isConnected()
                && networkInfoCheckConnection.isAvailable()) {
            try {
                GoogleAnalytics.getInstance(Incentive_Tab_Activity.this)
                        .reportActivityStart(this);

            } catch (Exception e) {
            }
        }
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if (networkInfoCheckConnection != null
                && networkInfoCheckConnection.isConnected()
                && networkInfoCheckConnection.isAvailable()) {
            try {
                GoogleAnalytics.getInstance(Incentive_Tab_Activity.this)
                        .reportActivityStop(this);

            } catch (Exception e) {
            }
        }
    }

    public void swapscreen(final int pos) {
        mActionBar.setSelectedNavigationItem(pos);
        mViewPager.setCurrentItem(pos);

    }

    public boolean onMenuItemClick(MenuItem arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub


        Intent i = new Intent(Incentive_Tab_Activity.this,
                Dashboard_Activity.class);
        finish();
        startActivity(i);

    }
}
