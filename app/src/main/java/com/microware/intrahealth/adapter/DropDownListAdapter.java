package com.microware.intrahealth.adapter;

import java.util.ArrayList;

import com.microware.intrahealth.Global;
import com.microware.intrahealth.R;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstCommon;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;


@SuppressLint("InflateParams")
public class DropDownListAdapter extends BaseAdapter{
	public ArrayList<MstCommon> common=new ArrayList<MstCommon>();
//	public ArrayList<GetElementID_Object> FoodID=new ArrayList<GetElementID_Object>();
	Context context;
	Global global;
	DataProvider dataProvider;
	int count=0;
	String foodGroupvalue = "";
	 ArrayList<Integer> checked;
	public DropDownListAdapter(Context context,ArrayList<MstCommon> common)
	{
		this.context=context;
		this.common=common;
		
		
	}

	
	public int getCount() {
		// TODO Auto-generated method stub
		return common.size();
	}

	
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	@SuppressWarnings("unused")
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View gridView;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			gridView = new View(context);
			gridView = inflater.inflate(R.layout.pop_up_window, null);
		} else {
			gridView = convertView;
		}
		global=(Global)context.getApplicationContext();
		dataProvider=new DataProvider(context);
		final CheckBox chkPastIllness=(CheckBox)gridView.findViewById(R.id.chkPastIllness);
		TextView tvfillckeck1=(TextView)gridView.findViewById(R.id.gridscreen2fillid);
		tvfillckeck1.setText(String.valueOf(common.get(position).getID()));
		chkPastIllness.setText(String.valueOf(common.get(position).getValue()));
		
//		if (global.getFoodID() != null && global.getFoodID().length() > 0) {
//			foodGroupvalue = global.getFoodID();
//		} else {
//			foodGroupvalue = "";
//		}
			

	final	String scheckid = tvfillckeck1.getText().toString();
//	global.setFoodGroupCount(0);
	chkPastIllness.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (chkPastIllness.isChecked()){
                checked.add(common.get(position).getID());
                Log.d("checked",""+checked);
               
            }
             else {
               for(int i=0;i<checked.size();i++){
                   if(checked.get(i)==common.get(position).getID()){
                       checked.remove(i);
                       Log.d("uncheked",""+checked);
                   }
               }
            }
         }
     });
	return convertView;
	}
	
		
//		
//		
//		if(global.getsPlantGUID()!=null && global.getsPlantGUID().length()>0)
//		{
//			elementID=dataProvider.getRawWaterElementID(global.getsPlantGUID(), water.get(position).getiWaterQualityChallengeID());
//			if(elementID!=null && elementID.size()>0)
//			{
//				chkWaterQualityChallenge.setChecked(true);
//			}else{
//				chkWaterQualityChallenge.setChecked(false);
//			}
//			
//			if ((chkWaterQualityChallenge).isChecked()) {
//				if(water.get(position).getiWaterQualityChallengeID()==7)
//				{
//					screen1.showtblcontaminent(1);
//					
//				}
//			}
//		}
		
//		if(checkID(foodGroupvalue,common.get(position).getID())==1)
//
//		{
//			chkPastIllness.setChecked(true);
//		}
//
//		return gridView;
//	}
//	public int checkID(String Value,int ID)
//	{
//	int iValue=0;
//	if(Value!=null && !Value.equalsIgnoreCase("null") && Value.length()>0)
//	{
//	String visit[]=Value.split("\\,");
//	for(int i=0;i<visit.length;i++)
//	{
//	if(Integer.valueOf(visit[i])==ID)
//	{
//	iValue=1;
//	break;
//	}
//	}
//	}
//	return iValue;
//
//
//	}
	}