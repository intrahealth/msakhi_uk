package com.microware.intrahealth.adapter;

import java.util.ArrayList;

import com.google.android.gms.plus.model.people.Person.RelationshipStatus;
import com.microware.intrahealth.Global;
import com.microware.intrahealth.MigrationSelf;
import com.microware.intrahealth.R;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstCommon;
import com.microware.intrahealth.object.Tbl_HHFamilyMember;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.provider.ContactsContract.CommonDataKinds.Relation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Migrationselfadapter extends BaseAdapter {
	ArrayList<Tbl_HHFamilyMember> HHFamilyMember;
	Context context;
	Global global;
	DataProvider dataProvider;
	MigrationSelf migrationself;
	ArrayAdapter<String> adapter;
	ArrayList<MstCommon> Common = new ArrayList<MstCommon>();
	int relation = 0;

	public Migrationselfadapter(Context context,
			ArrayList<Tbl_HHFamilyMember> hHFamilyMember) {
		this.HHFamilyMember = hHFamilyMember;
		this.context = context;

		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return HHFamilyMember.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View gridview = null;
		if (convertView == null) {
			LayoutInflater layoutInflater = (LayoutInflater) context
					.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			gridview = new View(context);
			gridview = layoutInflater.inflate(R.layout.migrationselfadapter,
					null);
		} else {
			gridview = convertView;
		}
		dataProvider = new DataProvider(context);
		global = (Global) context.getApplicationContext();
		TextView tv_name = (TextView) gridview.findViewById(R.id.tv_name);
		Spinner spin_Relation = (Spinner) gridview
				.findViewById(R.id.spin_Relation);
		tv_name.setText(HHFamilyMember.get(position).getFamilyMemberName());
		fillCommongender(spin_Relation, 6, global.getLanguage(), HHFamilyMember
				.get(position).getGenderID());

		spin_Relation.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position == 1) {
					if (relation == 0) {
						relation = 1;
						
					} else {
						CustomAlertSave2(context.getResources().getString(
								R.string.selectotherrelation));
					}
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		return gridview;
	}

	public void CustomAlertSave2(String msg) {
		// Create custom dialog object
		final Dialog dialog = new Dialog(context);
		// hide to default title for Dialog
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// inflate the layout dialog_layout.xml and set it as contentView
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.dialog_layout, null, false);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setContentView(view);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
		TextView txtTitle = (TextView) dialog
				.findViewById(R.id.txt_alert_message);
		txtTitle.setText(msg);

		Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(new android.view.View.OnClickListener() {

			public void onClick(View v) {
				dialog.dismiss();

			}

		});
		dialog.show();
	}

	private void fillCommongender(Spinner spin, int iflag, int Language, int pos) {
		Common = dataProvider.getCommonRecord1(Language, iflag, pos);

		String sValue[] = new String[Common.size() + 1];
		sValue[0] = context.getResources().getString(R.string.select);
		for (int i = 0; i < Common.size(); i++) {
			sValue[i + 1] = Common.get(i).getValue();
		}
		adapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_dropdown_item, sValue);
		spin.setAdapter(adapter);
	}

	private int returnPos1(int ID, int iFlag, int pos1) {
		// TODO Auto-generated method stub
		int pos = 0;
		Common = dataProvider.getCommonRecord1(1, iFlag, pos1);
		if (Common != null && Common.size() > 0) {
			for (int i = 0; i < Common.size(); i++) {
				if (Common.get(i).getID() == ID) {
					pos = i + 1;
				}
			}
		}
		return pos;
	}

}
