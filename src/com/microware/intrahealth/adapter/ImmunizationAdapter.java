package com.microware.intrahealth.adapter;

import java.util.ArrayList;

import android.R.color;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.microware.intrahealth.Global;
import com.microware.intrahealth.ImmunizationCounselling;
import com.microware.intrahealth.ImmunizationQuestionActivity;
import com.microware.intrahealth.R;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.Child_Imunization_Object;

@SuppressLint("InflateParams")
public class ImmunizationAdapter extends BaseAdapter {

	Context context;
	ArrayList<Child_Imunization_Object> ChildImmun = new ArrayList<Child_Imunization_Object>();
	ArrayList<Child_Imunization_Object> ChildImmun2 = new ArrayList<Child_Imunization_Object>();
	Global global;
	TextView bcg,hep1,opv0,opv1,opv2,opv3,dpt1,dpt2,dpt3,hep0,hep2,hep3,
	Pentavalent1,Pentavalent2,Pentavalent3,measles,vitamin;
	DataProvider dataProvider;
	Dialog PicVideo_PreviewPopUp;
	
	String mothername = "";
	public ImmunizationAdapter(Context context,
			ArrayList<Child_Imunization_Object> ChildImmun) {
		this.ChildImmun = ChildImmun;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ChildImmun.size();
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

	@SuppressWarnings("static-access")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View gridview = null;
		if (convertView == null) {
			LayoutInflater layoutInflater = (LayoutInflater) context
					.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			gridview = new View(context);
			gridview = layoutInflater.inflate(
					R.layout.immunizationgridadapter, null);
		} else {
			gridview = convertView;
		}

		global = (Global) context.getApplicationContext();
		dataProvider = new DataProvider(context);
		TextView tvparentsname = (TextView) gridview
				.findViewById(R.id.tvParentsname);
		TextView tvAge = (TextView) gridview.findViewById(R.id.tvAge);
		TextView tvcounselling = (TextView) gridview.findViewById(R.id.tvcounselling);
		ImageView btnimmune = (ImageView) gridview.findViewById(R.id.btnimmune);

		String Sparentsname = "";
		
		if(ChildImmun.get(position).getWomenname()!=null && ChildImmun.get(position).getWomenname().length()>0){

			mothername = ChildImmun.get(position).getWomenname();
			
		}else{

			ChildImmun2 = dataProvider.gettblCHildImmunization();
			if(ChildImmun2 != null && ChildImmun2.size() > 0) {

				for(int i=0;i<ChildImmun2.size();i++) {
					if ((ChildImmun.get(position).getChildGUID()).equalsIgnoreCase(ChildImmun2.get(i).getChildGUID())) {
						mothername = ChildImmun2.get(i).getFamilyMemberName();

					}
				}
			}
		}
		
		Sparentsname = String.valueOf(mothername+"/"+ChildImmun.get(position).getChild_name());

		tvparentsname.setText(Sparentsname);
		String currentdate=Validate.getcurrentdate();
		String age=Validate.getContractMonth(ChildImmun.get(position).getDob(),currentdate);
		tvAge.setText(String.valueOf(age));
		global.setDateMonth(age);

		tvcounselling.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String sql="select ImmunizationGUID from tblmstimmunizationAns where ChildGUID='"+ChildImmun.get(position).getChildGUID()+"'";
				String guid=dataProvider.getRecord(sql);
				global.setImmunizationGUID(guid);
				global.setsGlobalChildGUID(ChildImmun.get(position).getChildGUID());
				Intent i = new Intent(v.getContext(),
						ImmunizationQuestionActivity.class);
				context.startActivity(i);
			}
		});
		btnimmune.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(ChildImmun.get(position).getChildGUID() != null && ChildImmun.get(position).getChildGUID().length()> 0) {
				showimmune(ChildImmun.get(position).getChildGUID());
				}
			}
		});
		return gridview;
	}
	@SuppressWarnings("unused")
	public void showimmune(String ChildGUID) {
		PicVideo_PreviewPopUp = new Dialog(context);
		Window window = PicVideo_PreviewPopUp.getWindow();
		//		PicVideo_PreviewPopUp.getWindow().setLayout(900, 400);
		// PicVideo_PreviewPopUp.getWindow().setBackgroundDrawable(
		// new ColorDrawable(Color.TRANSPARENT));
		PicVideo_PreviewPopUp.getWindow().setBackgroundDrawableResource(
				color.white);
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		Double width = metrics.widthPixels*20.2;
		Double height = metrics.heightPixels*5.3;
		Window win = PicVideo_PreviewPopUp.getWindow();
		win.setLayout(width.intValue(), height.intValue());
		//		window.requestFeature(Window.FEATURE_NO_TITLE);

		PicVideo_PreviewPopUp.setContentView(R.layout.vaccination);
		PicVideo_PreviewPopUp.setCancelable(true);
		PicVideo_PreviewPopUp.setCanceledOnTouchOutside(true);
		bcg=(TextView) PicVideo_PreviewPopUp.findViewById(R.id.bcg);
		hep1=(TextView) PicVideo_PreviewPopUp.findViewById(R.id.hep);
		hep0=(TextView) PicVideo_PreviewPopUp.findViewById(R.id.hep0);
		hep2=(TextView) PicVideo_PreviewPopUp.findViewById(R.id.hep2);
		hep3=(TextView) PicVideo_PreviewPopUp.findViewById(R.id.hep3);
		opv0=(TextView) PicVideo_PreviewPopUp.findViewById(R.id.opv0);
		opv1=(TextView) PicVideo_PreviewPopUp.findViewById(R.id.opv1);
		opv2=(TextView) PicVideo_PreviewPopUp.findViewById(R.id.opv2);
		opv3=(TextView) PicVideo_PreviewPopUp.findViewById(R.id.opv3);
		dpt1=(TextView) PicVideo_PreviewPopUp.findViewById(R.id.dpt1);
		dpt2=(TextView) PicVideo_PreviewPopUp.findViewById(R.id.dpt2);
		dpt3=(TextView) PicVideo_PreviewPopUp.findViewById(R.id.dpt3);
		Pentavalent1=(TextView) PicVideo_PreviewPopUp.findViewById(R.id.Pentavalent1);
		Pentavalent2=(TextView) PicVideo_PreviewPopUp.findViewById(R.id.Pentavalent2);
		Pentavalent3=(TextView) PicVideo_PreviewPopUp.findViewById(R.id.Pentavalent3);
		measles=(TextView) PicVideo_PreviewPopUp.findViewById(R.id.measles);
		vitamin=(TextView) PicVideo_PreviewPopUp.findViewById(R.id.vitamin);


		filldata(ChildGUID);
		
		PicVideo_PreviewPopUp.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				
				((ImmunizationCounselling)context).fillgrid();
			}
		});
		
		PicVideo_PreviewPopUp.show();
	}
	
	public void filldata(String childGUID) {
		// TODO Auto-generated method stub

		ChildImmun = dataProvider.ShowCHildImmunizationdata(childGUID);

		if (ChildImmun != null && ChildImmun.size() > 0) {
			bcg.setText(String.valueOf(Validate.changeDateFormat(ChildImmun.get(0).getBcg())));
			opv1.setText(String.valueOf(Validate.changeDateFormat(ChildImmun.get(0).getOpv2())));
			dpt1.setText(String.valueOf(Validate.changeDateFormat(ChildImmun.get(0)
					.getDpt1())));
			hep0.setText(String.valueOf(Validate.changeDateFormat(ChildImmun.get(0).getHepb2())));
			opv2.setText(String.valueOf(Validate.changeDateFormat(ChildImmun.get(0).getOpv3())));
			dpt2.setText(String.valueOf(Validate.changeDateFormat(ChildImmun.get(0)
					.getDpt2())));
			hep2.setText(String.valueOf(Validate.changeDateFormat(ChildImmun.get(0).getHepb3())));
			opv3.setText(String.valueOf(Validate.changeDateFormat(ChildImmun.get(0).getOpv4())));
			dpt3.setText(String.valueOf(Validate.changeDateFormat(ChildImmun.get(0)
					.getDpt3())));
			hep3.setText(String.valueOf(Validate.changeDateFormat(ChildImmun.get(0).getHepb4())));
			measles
			.setText(String.valueOf(Validate.changeDateFormat(ChildImmun.get(0).getMeaseals())));
			vitamin
			.setText(String.valueOf(Validate.changeDateFormat(ChildImmun.get(0).getVitaminA())));

			Pentavalent1.setText(String.valueOf(Validate.changeDateFormat(ChildImmun.get(0)
					.getPentavalent1())));
			Pentavalent2.setText(String.valueOf(Validate.changeDateFormat(ChildImmun.get(0)
					.getPentavalent2())));
			Pentavalent3.setText(String.valueOf(Validate.changeDateFormat(ChildImmun.get(0)
					.getPentavalent3())));

			opv0.setText(String.valueOf(Validate.changeDateFormat(ChildImmun.get(0).getOpv1())));
			hep1.setText(String.valueOf(Validate.changeDateFormat(ChildImmun.get(0).getHepb1())));





		}

	}

}
