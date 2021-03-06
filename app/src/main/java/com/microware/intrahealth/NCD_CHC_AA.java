package com.microware.intrahealth;

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
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.microware.intrahealth.Global.TrackerName;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.fragment.NCD_CHC_Member;
import com.microware.intrahealth.fragment.NCD_Member;
import com.microware.intrahealth.fragment.NCD_Patient;
import com.microware.intrahealth.fragment.OutSidePatient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NCD_CHC_AA extends FragmentActivity implements ActionBar.TabListener,
        OnMenuItemClickListener {

    ActionBar mActionBar;
    ViewPager mViewPager;
    ImageView imgprevious, imgnext;
    DataProvider dataProvider;
    Global global;
    @SuppressLint("NewApi")
    ConnectivityManager connMgrCheckConnection;
    NetworkInfo networkInfoCheckConnection;
    int flag = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Main view typically contains just the ViewPager
        setContentView(R.layout.ncd_aa);
        dataProvider = new DataProvider(this);
        global = (Global) getApplication();
        setTitle(global.getVersionName());
        connMgrCheckConnection = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfoCheckConnection = connMgrCheckConnection
                .getActiveNetworkInfo();
        Tracker t = ((Global) getApplication())
                .getTracker(TrackerName.APP_TRACKER);
        t.setScreenName("NCD");
        String dimensionValue = "5";
        if (global.getsGlobalUserName() != null
                && global.getsGlobalUserName().length() > 0) {
            dimensionValue = global.getsGlobalUserName();
        }
        t.set("&userid", dimensionValue);
        t.enableAutoActivityTracking(true);
        t.send(new HitBuilders.EventBuilder().setCategory(dimensionValue)
                .setAction("NCD").setNewSession()
                .setCustomDimension(1, dimensionValue).build());
        t.send(new HitBuilders.ScreenViewBuilder().build());
        imgprevious = (ImageView) findViewById(R.id.imgprevious);
        imgnext = (ImageView) findViewById(R.id.imgnext);
        // Tell the ActionBar to display tabs

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
                .setText(getString(R.string.NCDPatient)).setTabListener(this));
        mActionBar.addTab(mActionBar.newTab()
                .setText(R.string.Outsidepatient)
                .setTabListener(this));
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
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            flag = extras.getInt("flag");

        Calendar cal = Calendar.getInstance();
        Date currentLocalTime = cal.getTime();
        SimpleDateFormat date1 = new SimpleDateFormat("HH:mm a");

        String localTime = date1.format(currentLocalTime);
        String FP_GUID = Validate.random();
        global.setFP_GUID(String.valueOf(FP_GUID));
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateStrings = sdf.format(date);
        dataProvider.getUserLogin(FP_GUID, global.getUserID(), "NCD", "NCD",
                localTime, dateStrings);
        // trackevent();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, global.getsGlobalAshaName()).setShowAsAction(
                MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return true;
    }

    @SuppressWarnings("unused")
    public void ShowCustomToast(String sMessage) {
        LayoutInflater inflater = getLayoutInflater();

    }

    public boolean onOptionsItemSelected(MenuItem item) {

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
                    return new NCD_CHC_Member();
                case 1:
                    return new OutSidePatient();
            }
            return null;
        }

        public int getCount() {
            return 2;
        }
    }

    class SwipedListener implements ViewPager.OnPageChangeListener {

        public void onPageSelected(int position) {
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
                GoogleAnalytics.getInstance(NCD_CHC_AA.this).reportActivityStart(
                        this);

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
                GoogleAnalytics.getInstance(NCD_CHC_AA.this)
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
        super.onBackPressed();
        Calendar cal = Calendar.getInstance();
        Date currentLocalTime = cal.getTime();
        SimpleDateFormat date1 = new SimpleDateFormat("HH:mm a");

        String endTime = date1.format(currentLocalTime);
        dataProvider.getUserLoginUpdate(global.getFP_GUID(), endTime);
        if (flag == 1) {
            Intent i = new Intent(NCD_CHC_AA.this, DirectSearch.class);
            finish();
            startActivity(i);
        } else {
            Intent i = new Intent(NCD_CHC_AA.this, AshaListForCHC.class);
            finish();
            startActivity(i);
        }


    }
}
