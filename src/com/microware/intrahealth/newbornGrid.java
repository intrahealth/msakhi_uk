package com.microware.intrahealth;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.microware.intrahealth.adapter.NewbornGridAdapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.tblChild;

public class newbornGrid extends Activity {
	
	Button btnAdd;
	GridView NewbornGrid;
	DataProvider dataProvider;
	Global global;
	
	ArrayList<tblChild> Child = new ArrayList<tblChild>();
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.newbornactivity);
		dataProvider = new DataProvider(this);
		global = (Global) getApplication();
		
		btnAdd=(Button)findViewById(R.id.btnAdd);
		NewbornGrid=(GridView)findViewById(R.id.NewbornGrid);
			
		btnAdd.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				global.setsGlobalChildGUID("");
				Intent i=new Intent(newbornGrid.this,
						WomenListHH.class);
				
				finish();
				startActivity(i);
			}
		});
		
		fillgrid();
		
		
	}
	
	public void fillgrid()
	{
		Child=dataProvider.gettblChild("", 2);
		if (Child != null) {
			android.view.ViewGroup.LayoutParams params = NewbornGrid
					.getLayoutParams();
			Resources r = getResources();
			float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
					100, r.getDisplayMetrics());
			int hi = Math.round(px);
			int gridHeight1 = 0;
			gridHeight1 = hi * Child.size();
			params.height = gridHeight1;
			NewbornGrid.setLayoutParams(params);
			NewbornGrid.setAdapter(new NewbornGridAdapter(this,Child));
		}
		else
		{
			NewbornGrid.setAdapter(new NewbornGridAdapter(this,Child));
		}
	}
	
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		

		Intent i = new Intent(newbornGrid.this, Dashboard_Activity.class);
		finish();
		startActivity(i);
	}

}
