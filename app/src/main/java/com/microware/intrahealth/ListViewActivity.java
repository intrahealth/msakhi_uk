package com.microware.intrahealth;

import java.util.ArrayList;
import java.util.Locale;

import com.microware.intrahealth.adapter.ProductAdapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.tbl_pregnantwomen;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.ListView;

public class ListViewActivity extends Activity {
	ListView listCustom, gridVisits;
	Global global;
	Locale myLocale;
	DataProvider dataProvider;

	private ArrayList<tbl_pregnantwomen> pregnant = new ArrayList<tbl_pregnantwomen>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.anclist);
		dataProvider = new DataProvider(this);
		global = (Global) getApplication();
		listCustom = (ListView) findViewById(R.id.listView);
		gridVisits = (ListView) findViewById(R.id.gridVisits);
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
			android.view.ViewGroup.LayoutParams params = listCustom
					.getLayoutParams();
			Resources r = getResources();
			float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
					300, r.getDisplayMetrics());
			int hi = Math.round(px);
			int gridHeight1 = 0;
			gridHeight1 = hi * pregnant.size();
			params.height = gridHeight1;
			// params.height = LayoutParams.WRAP_CONTENT;
			listCustom.setLayoutParams(params);

			// totalcount.setText(getResources().getString(R.string.totalcountPwomen)+"-"+pregnant.size());
			listCustom
					.setAdapter(new ProductAdapter(this, pregnant, gridVisits));

		}
	}

}
