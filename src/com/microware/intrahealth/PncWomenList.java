package com.microware.intrahealth;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.microware.intrahealth.adapter.Pnc_WomenAdapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.tblChild;

public class PncWomenList extends Activity {

	TextView ltvWomenID, ltvWomenName, ltvNxtvisitdate, totalcount;
	Button btnanc, btnsearchFilter;
	GridView gridanc;
	EditText etFamilyIDSearch;
	Global global;
	Locale myLocale;
	DataProvider dataProvider;
	ArrayAdapter<String> adapter;
	public ArrayList<tblChild> Child = new ArrayList<tblChild>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.home_activity);

		dataProvider = new DataProvider(this);
		global = (Global) getApplication();
		// btnaddanc = (Button) findViewById(R.id.btnaddanc);
		// btncloseanc = (Button) findViewById(R.id.btncloseanc);
		btnanc = (Button) findViewById(R.id.btnanc);
		gridanc = (GridView) findViewById(R.id.gridanc);
		btnsearchFilter = (Button) findViewById(R.id.btnsearchFilter);
		etFamilyIDSearch = (EditText) findViewById(R.id.etFamilyIDSearch);

		// ltvWomenID = (TextView) findViewById(R.id.ltvWomenID);
		ltvWomenName = (TextView) findViewById(R.id.ltvWomenName);
		totalcount = (TextView) findViewById(R.id.totalcount);
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

		fillgrid();

	}

	public void fillgrid() {
		// TODO Auto-generated method stub

		Child = dataProvider.gettblChild("", 5);
		if (Child != null) {
			totalcount.setText(getResources().getString(R.string.totalcount)
					+ "-" + Child.size());
			gridanc.setAdapter(new Pnc_WomenAdapter(this, Child));
		}
	}

	@SuppressWarnings("unused")
	public void fillgrid(String Woman_name) {
		// TODO Auto-generated method stub
		String name = "%" + Woman_name + "%";
		Child = dataProvider.gettblChild("", 5);

		if (Child != null && Child.size() > 0) {
			// android.view.ViewGroup.LayoutParams params =
			// gridanc.getLayoutParams();
			// Resources r = getResources();
			// float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
			// 300,
			// r.getDisplayMetrics());
			// int hi = Math.round(px);
			// int gridHeight1 = 0;
			// gridHeight1 = hi * pregnant.size();
			// params.height = gridHeight1;
			// gridanc.setLayoutParams(params);
			gridanc.setAdapter(new Pnc_WomenAdapter(this, Child));

		}
	}

	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		Intent i = new Intent(PncWomenList.this, MCH_Dashboard.class);
		finish();
		startActivity(i);
	}
}