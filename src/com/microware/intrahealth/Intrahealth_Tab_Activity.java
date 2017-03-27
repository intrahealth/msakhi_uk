package com.microware.intrahealth;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.microware.intrahealth.Global.TrackerName;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.fragment.Family_Fragment;
import com.microware.intrahealth.fragment.Household_Fragment;

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

public class Intrahealth_Tab_Activity extends FragmentActivity implements
		ActionBar.TabListener, OnMenuItemClickListener {

	ActionBar mActionBar;
	ViewPager mViewPager;
	ImageView imgprevious, imgnext;
	DataProvider dataProvider;
	Global global;
	@SuppressLint("NewApi")
	ConnectivityManager connMgrCheckConnection;
	NetworkInfo networkInfoCheckConnection;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Main view typically contains just the ViewPager
		setContentView(R.layout.intrahealth_activity);
		dataProvider = new DataProvider(this);
		global = (Global) getApplication();
		connMgrCheckConnection = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		networkInfoCheckConnection = connMgrCheckConnection
				.getActiveNetworkInfo();
		Tracker t = ((Global) getApplication())
				.getTracker(TrackerName.APP_TRACKER);
		t.setScreenName("Survey List");
		String dimensionValue = "5";
		if (global.getsGlobalUserName() != null
				&& global.getsGlobalUserName().length() > 0) {
			dimensionValue = global.getsGlobalUserName();
		}

		t.set("&userid", dimensionValue);
		t.enableAutoActivityTracking(true);

		t.send(new HitBuilders.EventBuilder().setCategory(dimensionValue)
				.setAction("User Sign In").setNewSession()
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
				.setText(getString(R.string.HouseholdDetails))
				.setTabListener(this));
		mActionBar.addTab(mActionBar.newTab()
				.setText(getString(R.string.familyindex)).setTabListener(this));

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

		// View layout = inflater.inflate(R.layout.custom_toast,
		// (ViewGroup) findViewById(R.id.custom_toast_layout_id));

		// set a message
		// TextView text = (TextView) layout.findViewById(R.id.text);
		// text.setText(sMessage);

		// Toast...
		// Toast toast = new Toast(getApplicationContext());
		// toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		// toast.setDuration(Toast.LENGTH_LONG);
		// toast.setView(layout);
		// toast.show();
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// switch (item.getItemId()) {
		// // action with ID action_refresh was selected
		// case R.id.homebutton:
		// Intent i = new Intent(Intrahealth_Tab_Activity.this,
		// Dashboard_Activity.class);
		// finish();
		// startActivity(i);
		// break;
		// // action with ID action_settings was selected
		//
		// default:
		// break;
		// }
		//
		return true;
	}

	public void onTabSelected(final Tab tab, android.app.FragmentTransaction ft) {
		System.out.println("MainActivity.onTabSelected()");
		// global.setiCurrentActiveTab(tab.getPosition()+1);
		// if (global.getiGlobalEnableFragment() == 0) {
		// mViewPager.setCurrentItem(0);
		//
		// } else if (global.getiGlobalEnableFragment() >= tab.getPosition()) {
		mViewPager.setCurrentItem(tab.getPosition());
		// }
		// global.setiCurrentActiveTab(tab.getPosition()+1);
		// if (tab.getPosition() > global.getiGlobalEnableFragment()) {
		// String sPageSaveCount = "0";
		// sPageSaveCount = String.valueOf(global
		// .getiGlobalEnableFragment() + 1);
		// String sMesg = "Please save page " + sPageSaveCount
		// + " of anc registration. ";
		// ShowCustomToast(sMesg);
		// }
		// if (global.getiGlobalEnableFragment() >= tab.getPosition()) {
		// if (tab.getPosition() == 0) {
		// imgprevious.setVisibility(View.GONE);
		// } else {
		// imgprevious.setVisibility(View.VISIBLE);
		//
		// }
		// if (tab.getPosition() == 3) {
		// imgnext.setVisibility(View.GONE);
		// } else {
		// imgnext.setVisibility(View.VISIBLE);
		//
		// }
		// }

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
				return new Household_Fragment();
			case 1:
				return new Family_Fragment();
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
			// if(global.getiGlobalEnableFragment()==0)
			// {
			// swapscreen(0);
			// }else if(global.getiGlobalEnableFragment()>=position)
			// {
			swapscreen(position);
			// }
			// else
			// {
			// swapscreen(position-1);
			// }
			// global.setiCurrentActiveTab(position + 1);
			// if (position > global.getiGlobalEnableFragment()) {
			// String sPageSaveCount = "0";
			// sPageSaveCount = String.valueOf(global
			// .getiGlobalEnableFragment() + 1);
			// String sMesg = "Please save page " + sPageSaveCount
			// + " of anc registration. ";
			// ShowCustomToast(sMesg);
			// }

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
				GoogleAnalytics.getInstance(Intrahealth_Tab_Activity.this)
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
				GoogleAnalytics.getInstance(Intrahealth_Tab_Activity.this)
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

		if (global.getiGlobalRoleID() == 3) {
			Intent i = new Intent(Intrahealth_Tab_Activity.this,
					Survey_Activity.class);
			finish();
			startActivity(i);
		} else {
			Intent i = new Intent(Intrahealth_Tab_Activity.this,
					Survey_Activity.class);
			finish();
			startActivity(i);
		}
	}
}
