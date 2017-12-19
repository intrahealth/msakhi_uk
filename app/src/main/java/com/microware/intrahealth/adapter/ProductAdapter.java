package com.microware.intrahealth.adapter;

import java.util.ArrayList;

import com.microware.intrahealth.Global;
import com.microware.intrahealth.R;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.TblANCVisit;
import com.microware.intrahealth.object.tbl_pregnantwomen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint({ "ViewHolder", "InflateParams" })
public class ProductAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<tbl_pregnantwomen> listItem;
ListView gridVisits;
Global global;
@SuppressWarnings("unused")
private static final String IMAGE_DIRECTORY_NAME = "mSakhi";
DataProvider dataProvider;
ArrayList<TblANCVisit> VisitANC = new ArrayList<TblANCVisit>();
      public ProductAdapter(Context c,ArrayList<tbl_pregnantwomen> listItem,ListView gridVisits) {
          mContext = c;
          this.listItem = listItem;
          this.gridVisits=gridVisits;
      }

      public int getCount() {
          // TODO Auto-generated method stub
          return listItem.size();
      }

      public Object getItem(int position) {
          // TODO Auto-generated method stub
          return listItem;
      }

      public long getItemId(int position) {
          // TODO Auto-generated method stub
          return position;
      }

      public View getView(final int position, View convertView, ViewGroup parent) {
          // TODO Auto-generated method stub
          LayoutInflater inflater = (LayoutInflater) mContext
              .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

          View rowView=inflater.inflate(R.layout.list_mobile, null,true);
//          View smallrowView=inflater.inflate(R.layout.list_mobilevisit, null,true);
          dataProvider = new DataProvider(mContext);
  		global = (Global) mContext.getApplicationContext();
          final TextView tvGridhide = (TextView) rowView
  				.findViewById(R.id.tvGridhide);
//  		final ListView gridVisits = (ListView) rowView
//  				.findViewById(R.id.gridVisits);
              TextView textView = (TextView) rowView.findViewById(R.id.label);
              textView.setText(listItem.get(position).getPWName()+"\n"+mContext.getResources().getString(R.string.edddate)+":"+listItem.get(position).getEDDDate()+"\n"+mContext.getResources().getString(R.string.mobile_no)+":"+listItem.get(position).getMobileNo());
              textView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String anc_guid = listItem.get(position).getPWGUID();
					//		global.setsGlobalANCGUID(pregnant.get(position).getPW);
					if (Integer.valueOf(tvGridhide.getText().toString()) == 0) {
						gridVisits.setVisibility(View.VISIBLE);
						tvGridhide.setText("1");
						fillvisitdata(gridVisits, anc_guid);
					} else if (Integer.valueOf(tvGridhide.getText().toString()) == 1) {
						gridVisits.setVisibility(View.GONE);
						tvGridhide.setText("0");
					}
				}
			});
              return rowView;
      }
      public void fillvisitdata(ListView gridVisits, String sPWGUID){
  		VisitANC = dataProvider.getTbl_VisitANCData(sPWGUID,"",0);
  		if (VisitANC != null && VisitANC.size() > 0) {

  			android.view.ViewGroup.LayoutParams params = gridVisits
  					.getLayoutParams();
  			Resources r = mContext.getResources();
  			float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
  					50, r.getDisplayMetrics());
  			int hi = Math.round(px);
  			int gridHeight1 = 0;
  			gridHeight1 = hi * VisitANC.size();
  			params.height = gridHeight1;
  			gridVisits.setLayoutParams(params);
  			gridVisits.setAdapter(new ListViewVistAncAdapter(mContext, VisitANC));

  		}
  	}
}
