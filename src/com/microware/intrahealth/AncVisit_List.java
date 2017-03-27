package com.microware.intrahealth;

import java.util.ArrayList;

import com.microware.intrahealth.adapter.Anc_DueListadapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.tbl_pregnantwomen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.TextView;

@SuppressLint("ClickableViewAccessibility")
public class AncVisit_List extends Activity {

	GridView gridanc;
	DataProvider dataProvider;
	Global global;
	ArrayList<tbl_pregnantwomen> member = new ArrayList<tbl_pregnantwomen>();
	String tvcount1 = "", tvcount2 = "", tvcount3 = "", tvcount4 = "",
			tvcount5 = "", tvcount6 = "", tvcount7 = "";
	TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.visitlist);

		dataProvider = new DataProvider(this);
		global = (Global) getApplication();

		TextView tv1 = (TextView) findViewById(R.id.tv1);
		TextView tv2 = (TextView) findViewById(R.id.tv2);
		TextView tv3 = (TextView) findViewById(R.id.tv3);
		TextView tv4 = (TextView) findViewById(R.id.tv4);
		TextView tv5 = (TextView) findViewById(R.id.tv5);
		TextView tv6 = (TextView) findViewById(R.id.tv6);
		TextView tv7 = (TextView) findViewById(R.id.tv7);
		String sql = "SELECT count(c.CHILDGUID) cnt,cast(round(julianday('NOW')-julianday(CHILD_dob)+.5) as int) day  from tblchild  c left join tblPNChomevisit_ANS  p where day between 0 and 1 and place_of_birth!=2 and  c.childguid not in  (select p.childguid from tblPNChomevisit_ANS where visitno=1)";
		tvcount1 = String.valueOf(dataProvider.getcountRecord(sql));
		String sql1 = "SELECT count(c.CHILDGUID) cnt,cast(round(julianday('NOW')-julianday(CHILD_dob)+.5) as int) day  from tblchild  c left join tblPNChomevisit_ANS  p where day between 2 and 6   and  c.childguid not in  (select p.childguid from tblPNChomevisit_ANS where visitno=2)";
		tvcount2 = String.valueOf(dataProvider.getcountRecord(sql1));
		String sql6 = "SELECT count(c.CHILDGUID) cnt,cast(round(julianday('NOW')-julianday(CHILD_dob)+.5) as int) day  from tblchild  c left join tblPNChomevisit_ANS  p where day between 42 and 43   and  c.childguid not in  (select p.childguid from tblPNChomevisit_ANS where visitno=7)";
		tvcount7 = String.valueOf(dataProvider.getcountRecord(sql6));
		String sql2 = "SELECT count(c.CHILDGUID) cnt,cast(round(julianday('NOW')-julianday(CHILD_dob)+.5) as int) day  from tblchild  c left join tblPNChomevisit_ANS  p where day between 7 and 13   and  c.childguid not in  (select p.childguid from tblPNChomevisit_ANS where visitno=3)";
		tvcount3 = String.valueOf(dataProvider.getcountRecord(sql2));
		String sql3 = "SELECT count(c.CHILDGUID) cnt,cast(round(julianday('NOW')-julianday(CHILD_dob)+.5) as int) day  from tblchild  c left join tblPNChomevisit_ANS  p where day between 14 and 20   and  c.childguid not in  (select p.childguid from tblPNChomevisit_ANS where visitno=4)";
		tvcount4 = String.valueOf(dataProvider.getcountRecord(sql3));
		String sql4 = "SELECT count(c.CHILDGUID) cnt,cast(round(julianday('NOW')-julianday(CHILD_dob)+.5) as int) day  from tblchild  c left join tblPNChomevisit_ANS  p where day between 21 and 27   and  c.childguid not in  (select p.childguid from tblPNChomevisit_ANS where visitno=5)";
		tvcount5 = String.valueOf(dataProvider.getcountRecord(sql4));
		String sql5 = "SELECT count(c.CHILDGUID) cnt,cast(round(julianday('NOW')-julianday(CHILD_dob)+.5) as int) day  from tblchild  c left join tblPNChomevisit_ANS  p where day between 28 and 41   and  c.childguid not in  (select p.childguid from tblPNChomevisit_ANS where visitno=6)";
		tvcount6 = String.valueOf(dataProvider.getcountRecord(sql5));
		tv1.setText("1st " + getResources().getString(R.string.visit) + " "
				+ tvcount1);
		tv2.setText("2nd " + getResources().getString(R.string.visit) + " "
				+ tvcount2);
		tv3.setText("3rd " + getResources().getString(R.string.visit) + " "
				+ tvcount3);
		tv4.setText("4th " + getResources().getString(R.string.visit) + " "
				+ tvcount4);
		tv5.setText("5th " + getResources().getString(R.string.visit) + " "
				+ tvcount5);
		tv6.setText("6th " + getResources().getString(R.string.visit) + " "
				+ tvcount6);
		tv7.setText("7th " + getResources().getString(R.string.visit) + " "
				+ tvcount7);

		tv1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(v.getContext(), Anc_duelist.class);
				i.putExtra("Flag", 4);
				i.putExtra("visit", 1);
				startActivity(i);
			}
		});
		tv2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(v.getContext(), Anc_duelist.class);
				i.putExtra("Flag", 4);
				i.putExtra("visit", 2);
				startActivity(i);
			}
		});
		tv3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(v.getContext(), Anc_duelist.class);
				i.putExtra("Flag", 4);
				i.putExtra("visit", 3);
				startActivity(i);
			}
		});
		tv4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(v.getContext(), Anc_duelist.class);
				i.putExtra("Flag", 4);
				i.putExtra("visit", 4);
				startActivity(i);
			}
		});
		tv5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(v.getContext(), Anc_duelist.class);
				i.putExtra("Flag", 4);
				i.putExtra("visit", 5);
				startActivity(i);
			}
		});
		tv6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(v.getContext(), Anc_duelist.class);
				i.putExtra("Flag", 4);
				i.putExtra("visit", 6);
				startActivity(i);
			}
		});
		tv7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(v.getContext(), Anc_duelist.class);
				i.putExtra("Flag", 4);
				i.putExtra("visit", 7);
				startActivity(i);
			}
		});

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, global.getsGlobalAshaName()).setShowAsAction(
				MenuItem.SHOW_AS_ACTION_IF_ROOM);

		return true;
	}

	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		Intent i = new Intent(AncVisit_List.this, Dashboard_Activity.class);
		finish();
		startActivity(i);

	}

}
