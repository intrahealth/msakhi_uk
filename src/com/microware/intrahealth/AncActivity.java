package com.microware.intrahealth;

import java.util.ArrayList;
import java.util.Locale;
import com.microware.intrahealth.adapter.AncAdapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.tbl_pregnantwomen;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

public class AncActivity extends Activity {

	TextView ltvWomenID, ltvWomenName, ltvNxtvisitdate, totalcount;
	Button btnaddanc, btncloseanc, btnanc, btnsearchFilter;
	GridView gridanc;
	EditText etFamilyIDSearch;
	Global global;
	Locale myLocale;
	DataProvider dataProvider;
	ArrayAdapter<String> adapter;
	ConnectivityManager connMgrCheckConnection;
	NetworkInfo networkInfoCheckConnection;
	public ArrayList<tbl_pregnantwomen> pregnant = new ArrayList<tbl_pregnantwomen>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.anc_activity);

		dataProvider = new DataProvider(this);
		global = (Global) getApplication();
		btnaddanc = (Button) findViewById(R.id.btnaddanc);
		btncloseanc = (Button) findViewById(R.id.btncloseanc);
		btnanc = (Button) findViewById(R.id.btnanc);
		gridanc = (GridView) findViewById(R.id.gridanc);
		btnsearchFilter = (Button) findViewById(R.id.btnsearchFilter);
		etFamilyIDSearch = (EditText) findViewById(R.id.etFamilyIDSearch);
		totalcount = (TextView) findViewById(R.id.totalcount);
		// ltvWomenID = (TextView) findViewById(R.id.ltvWomenID);
		ltvWomenName = (TextView) findViewById(R.id.ltvWomenName);
		// ltvNxtvisitdate = (TextView) findViewById(R.id.ltvNxtvisitdate);

		btnsearchFilter.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (etFamilyIDSearch.getText().toString().length() > 0) {
					fillgrid(etFamilyIDSearch.getText().toString());
				}
			}
		});
		etFamilyIDSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (etFamilyIDSearch.getText().toString().length() == 0) {
					fillgrid();
				}
			}
		});
		btnaddanc.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				/*
				 * global.setsGlobalANCGUID("");
				 * global.setsGlobalANCVisitGUID("");
				 * global.setiGlobalANCEnableFragment(0);
				 * global.setiGlobalFacilityVisit(0);
				 * global.setiCurrentActiveTab(0);
				 */
				Intent i = new Intent(AncActivity.this, PregWomenList.class);
				finish();
				startActivity(i);
			}
		});

		btncloseanc.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent i = new Intent(AncActivity.this, MCH_Dashboard.class);
				finish();
				startActivity(i);
			}
		});
		fillgrid();

	}

	public void fillgrid() {
		// TODO Auto-generated method stub
		int ashaid = 0;
		if (global.getsGlobalAshaCode() != null
				&& global.getsGlobalAshaCode().length() > 0) {
			ashaid = Integer.valueOf(global.getsGlobalAshaCode());
		}

		pregnant = dataProvider.getPregnantWomendata("", 5, ashaid);

		if (pregnant != null && pregnant.size() > 0) {
			android.view.ViewGroup.LayoutParams params = gridanc
					.getLayoutParams();
			Resources r = getResources();
			float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
					300, r.getDisplayMetrics());
			int hi = Math.round(px);
			int gridHeight1 = 0;
			gridHeight1 = hi * pregnant.size();
			params.height = gridHeight1;
			// params.height = LayoutParams.WRAP_CONTENT;
			gridanc.setLayoutParams(params);

			totalcount.setText(getResources().getString(
					R.string.totalcountPwomen)
					+ "-" + pregnant.size());
			gridanc.setAdapter(new AncAdapter(this, pregnant));

		}
	}

	public void fillgrid(String Woman_name) {
		// TODO Auto-generated method stub

		int ashaid = 0;
		if (global.getsGlobalAshaCode() != null
				&& global.getsGlobalAshaCode().length() > 0) {
			ashaid = Integer.valueOf(global.getsGlobalAshaCode());
		}
		String name = "%" + Woman_name + "%";
		pregnant = dataProvider.getPregnantWomendata(name, 4, ashaid);

		if (pregnant != null && pregnant.size() > 0) {
			android.view.ViewGroup.LayoutParams params = gridanc
					.getLayoutParams();
			Resources r = getResources();
			float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
					300, r.getDisplayMetrics());
			int hi = Math.round(px);
			int gridHeight1 = 0;
			gridHeight1 = hi * pregnant.size();
			params.height = gridHeight1;
			gridanc.setLayoutParams(params);
			gridanc.setAdapter(new AncAdapter(this, pregnant));

		}
	}

	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		Intent i = new Intent(AncActivity.this, MCH_Dashboard.class);
		finish();
		startActivity(i);
	}
}
