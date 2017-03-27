package com.microware.intrahealth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.microware.intrahealth.Global.TrackerName;
import com.microware.intrahealth.dataprovider.DataProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData.Item;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("InflateParams")
public class Dashboard_Activity extends Activity {

	Button btnLanguage, hh, mnch, cd, ncd, ah, fp, incentives, sync, vhnd, rd;
	// LinearLayout layoutsurvey, layoutsync,layoutmch;
	// TextView tvDashboardSynchronisation,tvDashboardSurvey;
	DataProvider dataProvider;
	Global global;
	String sCurrentLanguage = "";
	private Locale myLocale;
	String username = "";
	int iRoleID = 0;
	@SuppressWarnings("unused")
	private Menu menu;
	ConnectivityManager connMgrCheckConnection;
	NetworkInfo networkInfoCheckConnection;
	int hbnccount = 0, pregcount = 0, ancchkccount = 0, anccount = 0,
			fpcount = 0, total = 0;
	LinearLayout ll1, ll2;// Add Herojit
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	Date date = new Date();
	String CurrentDate = "", MonthNo_C = "", MonthNo_A = "", VHNDDate = "";
	ImageView uttarakhand, jharkhand;

	// private EasyTracker easyTracker1 = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.dashboard1);

		// send our data!
		ll1 = (LinearLayout) findViewById(R.id.ll1);
		ll2 = (LinearLayout) findViewById(R.id.ll2);
		uttarakhand = (ImageView) findViewById(R.id.uttarakhand);
		jharkhand = (ImageView) findViewById(R.id.jharkhand);
		btnLanguage = (Button) findViewById(R.id.btnLanguage);
		// hh = (Button) findViewById(R.id.hh);
		mnch = (Button) findViewById(R.id.mnch);
		cd = (Button) findViewById(R.id.cd);
		// ncd = (Button) findViewById(R.id.ncd);
		ah = (Button) findViewById(R.id.ah);
		fp = (Button) findViewById(R.id.fp);
		incentives = (Button) findViewById(R.id.incentives);
		// sync = (Button) findViewById(R.id.sync);
		vhnd = (Button) findViewById(R.id.vhnd);
		// rd = (Button) findViewById(R.id.rd);
		// tvDashboardSurvey = (TextView) findViewById(R.id.tvDashboardSurvey);
		// tvDashboardSynchronisation = (TextView)
		// findViewById(R.id.tvDashboardSynchronisation);
		// ll1.setVisibility(View.GONE);
		// ll2.setVisibility(View.VISIBLE);

		dataProvider = new DataProvider(this);
		global = (Global) getApplication();
		// global.setLanguage(2);
		iRoleID = global.getiGlobalRoleID();
		try {
			int statecode = 0;
			if (global.getStateCode() != null
					&& global.getStateCode().length() > 0) {

				statecode = Integer.valueOf(global.getStateCode());
			}
			if (statecode == 20) {
				hh = (Button) findViewById(R.id.hh1);

				ncd = (Button) findViewById(R.id.ncd1);

				sync = (Button) findViewById(R.id.sync1);

				rd = (Button) findViewById(R.id.rd1);

				ll1.setVisibility(View.GONE);
				ll2.setVisibility(View.VISIBLE);
				uttarakhand.setVisibility(View.GONE);
				jharkhand.setVisibility(View.VISIBLE);
			} else {
				hh = (Button) findViewById(R.id.hh);

				ncd = (Button) findViewById(R.id.ncd);

				sync = (Button) findViewById(R.id.sync);

				rd = (Button) findViewById(R.id.rd);
				ll1.setVisibility(View.VISIBLE);
				ll2.setVisibility(View.GONE);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		connMgrCheckConnection = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		networkInfoCheckConnection = connMgrCheckConnection
				.getActiveNetworkInfo();
		if (networkInfoCheckConnection != null
				&& networkInfoCheckConnection.isConnected()
				&& networkInfoCheckConnection.isAvailable()) {
			Tracker t = ((Global) getApplication())
					.getTracker(TrackerName.APP_TRACKER);
			t.setScreenName("Dashboard");
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
		}
		// t.send(new HitBuilders.EventBuilder()
		// .setCategory("msakhi")
		// .setAction("User Sign In")
		// .build());
		// t.send(new HitBuilders.AppViewBuilder().build());

		// String dimensionValue = "2";
		// t.set("userid", dimensionValue);
		username = global.getsGlobalUserName();
		if (global.getLanguage() == 1) {
			btnLanguage.setText("hi");
			global.setLanguage(1);
			changeLang("en");

		} else if (global.getLanguage() == 2) {
			btnLanguage.setText("en");
			global.setLanguage(2);
			changeLang("hi");

		} else {
			// String sLanguage = "";
			// sLanguage = "hi";
			sCurrentLanguage = "en";
			btnLanguage.setText(sCurrentLanguage);
			global.setLanguage(2);
			changeLang("hi");
		}

		// layoutsurvey = (LinearLayout) findViewById(R.id.layoutsurvey);
		// layoutsync = (LinearLayout) findViewById(R.id.layoutsynchronization);
		// layoutmch = (LinearLayout) findViewById(R.id.layoutmch);

		btnLanguage.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String sLang = "";
				sLang = btnLanguage.getText().toString();
				if (sLang.equalsIgnoreCase("en")) {

					global.setLanguage(1);
					btnLanguage.setText("hi");
					changeLang("en");

				} else if (sLang.equalsIgnoreCase("hi")) {

					global.setLanguage(2);
					btnLanguage.setText("en");
					changeLang("hi");

				}

			}
		});

		hh.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (iRoleID == 3) {
					Intent i = new Intent(Dashboard_Activity.this,
							AshaListForAnm.class);
					finish();

					i.putExtra("Flag", 1);
					startActivity(i);
				} else {
					Intent i = new Intent(Dashboard_Activity.this,
							Survey_Activity.class);
					finish();
					startActivity(i);
				}
			}
		});
		mnch.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (iRoleID == 3) {
					Intent i = new Intent(Dashboard_Activity.this,
							AshaListForAnm.class);
					finish();

					i.putExtra("Flag", 2);
					startActivity(i);
				} else {
					Intent i = new Intent(Dashboard_Activity.this,
							MCH_Dashboard.class);
					finish();
					startActivity(i);
				}
			}
		});
		cd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Intent i = new Intent(Dashboard_Activity.this,
				// ListViewActivity.class);
				// finish();
				// startActivity(i);
			}
		});
		sync.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent i = new Intent(Dashboard_Activity.this,
						Synchronization.class);
				finish();
				startActivity(i);
			}
		});
		fp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (iRoleID == 3) {
					Intent i = new Intent(Dashboard_Activity.this,
							AshaListForAnm.class);
					finish();

					i.putExtra("Flag", 3);
					startActivity(i);
				} else {
					Intent i = new Intent(Dashboard_Activity.this, FP_AA.class);
					finish();
					startActivity(i);
				}

			}
		});
		rd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent i = new Intent(Dashboard_Activity.this,
						Report_Module.class);
				finish();
				startActivity(i);

			}
		});
		// add herojit
//		incentives.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent i = new Intent(Dashboard_Activity.this, Incentives.class);
//				finish();
//				startActivity(i);
//			}
//		});
//		vhnd.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent i = new Intent(Dashboard_Activity.this,
//						VHND_Record.class);
//				finish();
//				startActivity(i);
//			}
//		});

		String sql = "SELECT count(c.CHILDGUID) cnt,cast(round(julianday('NOW')-julianday(CHILD_dob)+.5) as int) day  from tblchild  c left join tblPNChomevisit_ANS  p where day between 0 and 42 and place_of_birth!=2 and  c.childguid not in  (select p.childguid from tblPNChomevisit_ANS where visitno=day)";
		String sql1 = "SELECT count(c.CHILDGUID) cnt,cast(round(julianday('NOW')-julianday(CHILD_dob)+.5) as int)day  from tblchild  c left join tblPNChomevisit_ANS  p where day between 3 and 42 and place_of_birth=2 and  c.childguid not in  (select p.childguid from tblPNChomevisit_ANS where visitno=day)";

		hbnccount = dataProvider.getcountRecord(sql)
				+ dataProvider.getcountRecord(sql1);
		String sql2 = "SELECT count(pwguid)  from tblPregnant_woman where  IsPregnant=1 and cast(round(julianday(EDDDate)-julianday('NOW')+.5) as int) between 0 and 2";
		pregcount = dataProvider.getMaxRecord(sql2);
		String sql3 = "SELECT count(a.pwguid) cnt,visit_no  from tblPregnant_woman a inner join tblANCVisit b on  cast(round((julianday('NOW')-julianday(a.lmpdate))/90+.5)  as int) =b.visit_no and  a.pwguid=b.pwguid where homevisitdate in('',null) and ispregnant=1 order by b.pwguid";

		String sql4 = "SELECT count(a.pwguid) cnt,visit_no  from tblPregnant_woman a inner join tblANCVisit b on  cast(round((julianday('NOW')-julianday(a.lmpdate))/90+.5)  as int) =b.visit_no and  a.pwguid=b.pwguid where checkupvisitdate in('',null)and ispregnant=1 order by b.pwguid";
		ancchkccount = dataProvider.getcountRecord(sql4);
		anccount = dataProvider.getcountRecord(sql3);
		String sql5 = "select count(*) from tblFP_followup where methodadopted in(1,2,3) and cast(round((julianday('NOW')-julianday(methodadopteddate))/30+.5)  as int) in(1,2,3,5,7,9,11,13) and uid in(select max(uid) from tblFP_followup group by womenname_guid) and cast(round((julianday('NOW')-julianday(createdon))+.5)  as int) >30";
		String sql51 = "select count(*) from tblFP_followup where methodadopted in(4,5) and cast(round((julianday('NOW')-julianday(methodadopteddate))/30+.5)  as int) in(1,4,7,10,13) and uid in(select max(uid) from tblFP_followup group by womenname_guid) and cast(round((julianday('NOW')-julianday(createdon))+.5)  as int) >30";
		String sql52 = "select count(*) from tblFP_followup where methodadopted in(6,7) and cast(round((julianday('NOW')-julianday(methodadopteddate))/30+.5)  as int) in(1,4) and uid in(select max(uid) from tblFP_followup group by womenname_guid) and cast(round((julianday('NOW')-julianday(createdon))+.5)  as int) >30";

		// String sql51 =
		// "select count(distinct(womenname_guid)) from tblfp_visit where  cast(round((julianday('NOW')-julianday(visitdate))+.5)  as int) between 30 and 32 and womenname_guid not in (select womenname_guid from tblFP_followup) ";
		fpcount = dataProvider.getMaxRecord(sql5)
				+ dataProvider.getMaxRecord(sql51)
				+ dataProvider.getMaxRecord(sql52);
		// + dataProvider.getMaxRecord(sql51);
		total = hbnccount + pregcount + ancchkccount + anccount + fpcount;

	}

	// public void trackevent(){
	// easyTracker1.send(MapBuilder.createEvent("your_action",
	// "Dashboard", global.getsGlobalUserID(), null).build());
	// }

	public void changeLang(String lang) {
		if (lang.equalsIgnoreCase(""))
			return;
		myLocale = new Locale(lang);
		saveLocale(lang);
		Locale.setDefault(myLocale);
		android.content.res.Configuration config = new android.content.res.Configuration();
		config.locale = myLocale;
		getBaseContext().getResources().updateConfiguration(config,
				getBaseContext().getResources().getDisplayMetrics());

		updateTexts();
	}

	public void saveLocale(String lang) {
		String langPref = "Language";
		SharedPreferences prefs = getSharedPreferences("CommonPrefs",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(langPref, lang);
		editor.commit();
	}

	public void loadLocale() {
		String langPref = "Language";
		SharedPreferences prefs = getSharedPreferences("CommonPrefs",
				Context.MODE_PRIVATE);
		String language = prefs.getString(langPref, "");
		changeLang(language);
	}

	private void updateTexts() {
		hh.setText(R.string.HouseholdDetails);
		mnch.setText(R.string.MNCH);
		cd.setText(R.string.cd);
		ncd.setText(R.string.ncd);
		ah.setText(R.string.ah);
		fp.setText(R.string.fp);
		incentives.setText(R.string.incentives);
		sync.setText(R.string.synchronization);
		vhnd.setText(R.string.vhnd);
		rd.setText(R.string.rd);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (global.getiGlobalRoleID() == 3) {
			menu.add(0, 0, 1, global.getsGlobalANMName()).setShowAsAction(
					MenuItem.SHOW_AS_ACTION_IF_ROOM);
			menu.add(0, 1, 2, "History").setIcon(R.drawable.logout1)
					.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		} else {
			if (total == 0) {
				menu.add(0, 0, 0, global.getsGlobalAshaName())
						.setIcon(R.drawable.bell)
						.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

				menu.add(0, 0, 1, global.getsGlobalAshaName()).setShowAsAction(
						MenuItem.SHOW_AS_ACTION_IF_ROOM);

				menu.add(0, 1, 2, "History").setIcon(R.drawable.logout1)
						.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
			} else {
				menu.add(0, 0, 0, global.getsGlobalAshaName())
						.setIcon(R.drawable.bell1)
						.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
				menu.add(1, 1, 1, global.getsGlobalAshaName()).setShowAsAction(
						MenuItem.SHOW_AS_ACTION_IF_ROOM);

				menu.add(0, 2, 2, "").setIcon(R.drawable.logout1)
						.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
			}
		}

		// menu.setGroupVisible(0, false);
		//

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getOrder()) {
		case 0:
			CustomAlert1();
			break;
		case 2:
			CustomAlert();

			break;
		// action with ID action_settings was selected
		// case 0:
		//
		// break;
		default:
			break;
		}

		return true;
	}

	public void CustomAlert() {

		// Create custom dialog object
		final Dialog dialog = new Dialog(this);
		// hide to default title for Dialog
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// inflate the layout dialog_layout.xml and set it as contentView
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.logout_alert, null, false);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setContentView(view);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
		// ImageView image=(ImageView)findViewById(R.id.img_dialog_icon);
		// image.setVisibility(false);

		// Retrieve views from the inflated dialog layout and update their
		// values
		TextView txtTitle = (TextView) dialog
				.findViewById(R.id.txt_alert_message);
		txtTitle.setText(getResources().getString(R.string.Doyouwanttologout));

		// TextView txtMessage = (TextView)
		// dialog.findViewById(R.id.txt_dialog_message);
		// txtMessage.setText("Do you want to Leave the page ?");

		Button btnyes = (Button) dialog.findViewById(R.id.btn_yes);
		btnyes.setOnClickListener(new android.view.View.OnClickListener() {
			public void onClick(View v) {
				// Dismiss the dialog
				// int iLoggedin = 0;

				Intent i = new Intent(Dashboard_Activity.this, Login.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
				dialog.dismiss();
			}
		});

		Button btnno = (Button) dialog.findViewById(R.id.btn_no);
		btnno.setOnClickListener(new android.view.View.OnClickListener() {
			public void onClick(View v) {
				// Dismiss the dialog
				dialog.dismiss();
			}
		});

		// Display the dialog
		dialog.show();

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (networkInfoCheckConnection != null
				&& networkInfoCheckConnection.isConnected()
				&& networkInfoCheckConnection.isAvailable()) {
			try {
				GoogleAnalytics.getInstance(Dashboard_Activity.this)
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
				GoogleAnalytics.getInstance(Dashboard_Activity.this)
						.reportActivityStop(this);
			} catch (Exception e) {

			}
		}
	}

	public void onResume() {
		super.onResume();
		// ztrackevent();
	}

	public void CustomAlert1() {
		// Create custom dialog object
		final Dialog dialog = new Dialog(this);
		// hide to default title for Dialog
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// inflate the layout dialog_layout.xml and set it as contentView
		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.duelist_dialog, null, false);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setContentView(view);
		// pwindo = new PopupWindow(view, 700, 1000, true);
		// pwindo.showAtLocation(view, Gravity.CENTER, 0, 0);

		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
		TableRow tbl1 = (TableRow) dialog.findViewById(R.id.tbl1);
		TableRow tbl2 = (TableRow) dialog.findViewById(R.id.tbl2);
		TableRow tbl3 = (TableRow) dialog.findViewById(R.id.tbl3);
		TableRow tbl4 = (TableRow) dialog.findViewById(R.id.tbl4);
		TableRow tbl5 = (TableRow) dialog.findViewById(R.id.tbl5);
		Button btn_noofHBNC = (Button) dialog.findViewById(R.id.btn_noofHBNC);
		Button btn_VHNDDate = (Button) dialog.findViewById(R.id.btn_VHNDDate);
		TableRow tbl6 = (TableRow) dialog.findViewById(R.id.tbl6);

		TextView tv1 = (TextView) dialog.findViewById(R.id.tv1);

		TextView tv2 = (TextView) dialog.findViewById(R.id.tv2);

		TextView tv3 = (TextView) dialog.findViewById(R.id.tv3);

		TextView tv4 = (TextView) dialog.findViewById(R.id.tv4);

		TextView tv5 = (TextView) dialog.findViewById(R.id.tv5);

		Button btn_noofdeliveries = (Button) dialog
				.findViewById(R.id.btn_noofdeliveries);
		Button btn_noofanccheckup = (Button) dialog
				.findViewById(R.id.btn_noofanccheckup);
		Button btn_noofancvisit = (Button) dialog
				.findViewById(R.id.btn_noofancvisit);
		Button btn_nooffamilyplanning = (Button) dialog
				.findViewById(R.id.btn_nooffamilyplanning);
		if (hbnccount == 0) {
			tbl1.setVisibility(view.GONE);
		} else {
			tbl1.setVisibility(view.VISIBLE);
			tv1.setText(String.valueOf(hbnccount));
		}
		if (pregcount == 0) {
			tbl2.setVisibility(view.GONE);
		} else {
			tbl2.setVisibility(view.VISIBLE);
			tv2.setText(String.valueOf(pregcount));
		}
		if (ancchkccount == 0) {
			tbl3.setVisibility(view.GONE);
		} else {
			tbl3.setVisibility(view.VISIBLE);
			tv3.setText(String.valueOf(ancchkccount));
		}
		if (anccount == 0) {
			tbl4.setVisibility(view.GONE);
		} else {
			tbl4.setVisibility(view.VISIBLE);
			tv4.setText(String.valueOf(anccount));
		}
		if (fpcount == 0) {
			tbl5.setVisibility(view.GONE);
		} else {
			tbl5.setVisibility(view.VISIBLE);
			tv5.setText(String.valueOf(fpcount));
		}
		CurrentDate = dateFormat.format(date);
		String aa[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
				"Sep", "Oct", "Nov", "Dec" };
		String mon[] = {};
		mon = CurrentDate.split("-");
		final int monthi, Days, Year;
		Year = Integer.valueOf(mon[0]);
		monthi = Integer.valueOf(mon[1]) - 1;
		Days = Integer.valueOf(mon[2]);
		if (Days > 0 && monthi > 0 && monthi != 11) {
			MonthNo_C = aa[monthi];
			MonthNo_A = aa[monthi + 1];
		} else if (Days > 0 && monthi == 11) {
			MonthNo_C = aa[monthi];
			MonthNo_A = aa[1];
		}
		String sql = "";
		sql = "Select Village_ID from VHND_Schedule where ASHA_ID="
				+ global.getsGlobalAshaCode()
				+ " and ((cast(round(julianday("
				+ MonthNo_C
				+ ")-julianday('NOW'))  as int) <=22) or (cast(round(julianday("
				+ MonthNo_A + ")-julianday('NOW'))  as int) <=22)) and Year='"
				+ Year + "'";
		final String VillageID = dataProvider.getRecord(sql);
		// if (VillageID == null || VillageID.equalsIgnoreCase("")) {
		// btn_VHNDDate.setVisibility(View.GONE);
		// }

		btn_VHNDDate
				.setOnClickListener(new android.view.View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						String sql1 = "", sql2 = "", sql3 = "", sql4 = "", sql5 = "", sql6 = "";
						int count5 = 0, count6 = 0;
						int NoofDay_C = 0, NoofDay_A = 0, count = 0;
						sql5 = "Select count(*) from VHND_Schedule where ASHA_ID="
								+ global.getsGlobalAshaCode()
								+ " and Village_ID='"
								+ VillageID
								+ "' and (cast(round(julianday("
								+ MonthNo_A
								+ ")-julianday('NOW'))  as int) <=22) and Year='"
								+ Year + "'";
						sql6 = "Select count(*) from VHND_Schedule where ASHA_ID="
								+ global.getsGlobalAshaCode()
								+ " and Village_ID='"
								+ VillageID
								+ "' and (cast(round(julianday("
								+ MonthNo_C
								+ ")-julianday('NOW'))  as int) <=22) and Year='"
								+ Year + "'";
						count5 = dataProvider.getMaxRecord(sql5);
						count6 = dataProvider.getMaxRecord(sql6);
						if (count5 != 0) {
							sql2 = "Select round(julianday("
									+ MonthNo_A
									+ ")-julianday('NOW')) from VHND_Schedule where ASHA_ID="
									+ global.getsGlobalAshaCode()
									+ " and Village_ID='"
									+ VillageID
									+ "' and (cast(round(julianday("
									+ MonthNo_A
									+ ")-julianday('NOW'))  as int) <=22) and Year='"
									+ Year + "'";
							NoofDay_C = Integer.valueOf(dataProvider
									.getRecord(sql2));
						}
						if (count6 != 0) {
							sql3 = "Select round(julianday("
									+ MonthNo_C
									+ ")-julianday('NOW')) from VHND_Schedule where ASHA_ID="
									+ global.getsGlobalAshaCode()
									+ " and Village_ID='"
									+ VillageID
									+ "' and (cast(round(julianday("
									+ MonthNo_C
									+ ")-julianday('NOW'))  as int) <=22) and Year='"
									+ Year + "'";
							NoofDay_A = Integer.valueOf(dataProvider
									.getRecord(sql3));
						}
						String Date = "", Month = "";
						if (NoofDay_C < NoofDay_A) {
							Month = MonthNo_C;
						} else {
							Month = MonthNo_A;
						}
						sql4 = "select "
								+ Month
								+ " from VHND_Schedule where ASHA_ID="
								+ global.getsGlobalAshaCode()
								+ " and Village_ID='"
								+ VillageID
								+ "' and (cast(round(julianday("
								+ Month
								+ ")-julianday('NOW'))  as int) <=22) and Year='"
								+ Year + "'";
						Date = dataProvider.getRecord(sql4);
						sql1 = "select count(*) from tbl_VHND_DueList where ASHA_ID="
								+ global.getsGlobalAshaCode()
								+ " and Village_ID='"
								+ VillageID
								+ "' and Date='" + Date + "'";
						count = dataProvider.getMaxRecord(sql1);
						// if (count == 0) {
						// Save_VHND(Integer.valueOf(VillageID), Date, "I");
						// } else {
						// Save_VHND(Integer.valueOf(VillageID), Date, "U");
						// }
						global.setVHND_ID(Validate.random());
						global.setVHND_Date(Date);
						global.setVHND_MonthField(Month);
						global.setVHND_VillageID(VillageID);
						Intent i = new Intent(v.getContext(),
								VHND_DueList.class);
						i.putExtra("Flag", 6);
						startActivity(i);
					}
				});
		btn_noofHBNC
				.setOnClickListener(new android.view.View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(v.getContext(),
								AncVisit_List.class);

						startActivity(i);
					}
				});
		btn_noofdeliveries
				.setOnClickListener(new android.view.View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(v.getContext(), Anc_duelist.class);
						i.putExtra("Flag", 2);
						startActivity(i);
					}
				});
		btn_noofanccheckup
				.setOnClickListener(new android.view.View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(v.getContext(), Anc_duelist.class);
						i.putExtra("Flag", 0);
						startActivity(i);
					}
				});
		btn_noofancvisit
				.setOnClickListener(new android.view.View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(v.getContext(), Anc_duelist.class);
						i.putExtra("Flag", 1);
						startActivity(i);
					}
				});
		btn_nooffamilyplanning
				.setOnClickListener(new android.view.View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(v.getContext(), Anc_duelist.class);
						i.putExtra("Flag", 5);
						startActivity(i);
					}
				});

		// Display the dialog
		dialog.show();

	}

	public void Save_VHND(int Village_ID, String Datee, String VHND_Flag) {
		String VHND_ID = global.getVHND_ID();
		int SS_ID = 0;
		int VillageId = Village_ID;
		int AshaID = Integer.valueOf(global.getsGlobalAshaCode());
		int AWWID = 0;
		int ANMID = Integer.valueOf(global.getsGlobalANMCODE());
		String Date = Datee;

		int Returnvalue = 0;
		Returnvalue = dataProvider.VHND_Perform(VHND_ID, SS_ID, VillageId,
				AshaID, AWWID, ANMID, Date, VHND_Flag);
		if (Returnvalue > 0) {
			alerttoast(R.string.savesuccessfully);
		} else {
			alerttoast(R.string.NotSave);
		}
	}

	private void alerttoast(int i) {
		Toast.makeText(this, getResources().getString(i), Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		Intent i = new Intent(Dashboard_Activity.this, Login.class);
		finish();
		startActivity(i);
	}

}
