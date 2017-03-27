package com.microware.intrahealth.adapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import com.microware.intrahealth.Delivery;
import com.microware.intrahealth.Global;
import com.microware.intrahealth.PregnantWomen;
import com.microware.intrahealth.R;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstCommon;
import com.microware.intrahealth.object.TblANCVisit;
import com.microware.intrahealth.object.Tbl_HHFamilyMember;
import com.microware.intrahealth.object.tbl_pregnantwomen;

import android.R.color;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class AncAdapter extends BaseAdapter {

	ArrayList<tbl_pregnantwomen> pregnant;
	Context context;
	String imageUri;
	Global global;
	@SuppressWarnings("unused")
	private static final String IMAGE_DIRECTORY_NAME = "mSakhi";
	DataProvider dataProvider;
	ArrayList<TblANCVisit> VisitANC = new ArrayList<TblANCVisit>();
	ArrayList<Tbl_HHFamilyMember> masterEntery1 = new ArrayList<Tbl_HHFamilyMember>();
	ArrayList<TblANCVisit> VisitANC2 = new ArrayList<TblANCVisit>();
	int iflag = 0;
	String sDueVisits = "";
	String LMPDate = "";
	String RegistrationDate = "";
	String CurrentDate = "";
	Dialog PicVideo_PreviewPopUp;
	TextView weight1, weight2, weight3, weight4, bp1, bp2, bp3, bp4, hb1, hb2,
			hb3, hb4, us1, us2, us3, us4, ua1, ua2, ua3, ua4, BMI1, BMI2, BMI3,
			BMI4, tt1, tt2, tt3, tt4, IFA1, IFA2, IFA3, IFA4, calcium1,
			calcium2, calcium3, calcium4, dangersigns1, dangersigns2,
			dangersigns3, dangersigns4, HRP1, HRP2, HRP3, HRP4, Height1,
			Height2, Height3, Height4;
	String arr[] = { "अत्याधिक/गंभीर एनीमिया",
			"गर्भावस्था में योनि से रक्तस्राव", "तेज बुखार",
			"पेट में तेज दर्द", "दौरे आना या फिट्स पड़ना" };
	String arr1[] = { "अत्याधिक/गंभीर एनीमिया",
			"गर्भावस्था में योनि से रक्तस्राव", "तेज बुखार",
			"पेट में तेज दर्द", "सिर में तेज दर्द के साथ  धुंधला दिखना",
			"चेहरे, हाथों, पांवों में सूजन ", "दौरे आना या फिट्स पड़ना", };

	public AncAdapter(Context context, ArrayList<tbl_pregnantwomen> pregnant) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.pregnant = pregnant;
		// this.iflag = iflag;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pregnant.size();
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View gridview = null;
		if (convertView == null) {
			LayoutInflater layoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			gridview = new View(context);
			gridview = layoutInflater.inflate(R.layout.ancgridadapter, null);
		} else {
			gridview = convertView;
		}

		dataProvider = new DataProvider(context);
		global = (Global) context.getApplicationContext();

		TextView tvwname = (TextView) gridview.findViewById(R.id.tvwname);
		Button checkup = (Button) gridview.findViewById(R.id.checkup);
		Button report = (Button) gridview.findViewById(R.id.report);
		/*
		 * TextView tvlastvisit = (TextView) gridview
		 * .findViewById(R.id.tvlastvisit); ImageView ivaddvisit = (ImageView)
		 * gridview .findViewById(R.id.imageancaddvisit); // ImageView ivdelete
		 * = (ImageView) gridview .findViewById(R.id.imageancdelete);
		 */
		final TextView tvGridhide = (TextView) gridview
				.findViewById(R.id.tvGridhide);
		final GridView gridVisits = (GridView) gridview
				.findViewById(R.id.gridVisits);
		int count = 0;
		// tvwname.setEnabled(false);
		String sql = "select count(*) from tblancvisit where PWGUID='"
				+ pregnant.get(position).getPWGUID()
				+ "' and checkupVisitDate!=''";
		count = dataProvider.getMaxRecord(sql);
		report.setText(context.getResources().getString(R.string.report) + "  "
				+ count);
		ImageView ivedit = (ImageView) gridview.findViewById(R.id.imageancedit);
		// ImageView imagePreg = (ImageView)
		// gridview.findViewById(R.id.imagePreg);
		ImageView ivoutcome = (ImageView) gridview
				.findViewById(R.id.imageancaddvisit);
		ivoutcome.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				global.setGlobalHHFamilyMemberGUID(pregnant.get(position)
						.getHHFamilyMemberGUID());
				global.setGlobalHHSurveyGUID(pregnant.get(position).getHHGUID());
				global.setsGlobalPWGUID(pregnant.get(position).getPWGUID());
				global.setsGlobalLMPDate(pregnant.get(position).getLMPDate());
				Intent i = new Intent(context, Delivery.class);
				context.startActivity(i);
			}
		});
		//
		report.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				global.setGlobalHHFamilyMemberGUID(pregnant.get(position)
						.getHHFamilyMemberGUID());
				global.setGlobalHHSurveyGUID(pregnant.get(position).getHHGUID());
				global.setsGlobalPWGUID(pregnant.get(position).getPWGUID());
				global.setsGlobalLMPDate(pregnant.get(position).getLMPDate());
				showreport(pregnant.get(position).getPWGUID(), position);
			}
		});
		ivedit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				global.setGlobalHHFamilyMemberGUID(pregnant.get(position)
						.getHHFamilyMemberGUID());
				global.setGlobalHHSurveyGUID(pregnant.get(position).getHHGUID());
				masterEntery1 = dataProvider.getAllmalename(global
						.getGlobalHHFamilyMemberGUID());
				if (masterEntery1 != null && masterEntery1.size() > 0) {
					global.setsGlobalSpouseGUID(masterEntery1.get(0)
							.getSpouse());
				}

				global.setsGlobalPWGUID(pregnant.get(position).getPWGUID());
				Intent i = new Intent(context, PregnantWomen.class);
				context.startActivity(i);
			}
		});
		// if (tblwomenData != null && tblwomenData.size() > 0) {
		// int iDiagnosis = tblwomenData.get(position).getLastDiagnosis();
		//
		// if (iDiagnosis == 1) {
		// tvwomenname.setBackgroundResource(R.drawable.yellowtextbox);
		// } else if (iDiagnosis == 2) {
		// tvwomenname.setBackgroundResource(R.drawable.orangetextbox);
		// } else if (iDiagnosis == 3) {
		// tvwomenname.setBackgroundResource(R.drawable.redtextbox);
		// } else if (iDiagnosis == 4) {
		// tvwomenname.setBackgroundResource(R.drawable.greentextbox);
		// }
		//
		// }
		//
		if (pregnant.get(position).getPWName().length() > 0
				&& pregnant.get(0).getHusbandName().length() > 0) {
			tvwname.setText(String.valueOf(pregnant.get(position).getPWName())
					+ " " + "w/o" + " "
					+ String.valueOf(pregnant.get(position).getHusbandName()));
		} else if (pregnant.get(position).getPWName().length() > 0
				&& pregnant.get(0).getHusbandName().length() == 0) {
			tvwname.setText(String.valueOf(pregnant.get(position).getPWName()));
		}
		if (pregnant.get(position).getHighRisk() == 1) {
			tvwname.setTextColor(Color.RED);
		}
		tvwname.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String anc_guid = pregnant.get(position).getPWGUID();
				// global.setsGlobalANCGUID(pregnant.get(position).getPW);
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
		checkup.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String anc_guid = pregnant.get(position).getPWGUID();
				// global.setsGlobalANCGUID(pregnant.get(position).getPW);
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

		return gridview;
	}

	@SuppressWarnings("unused")
	public void showreport(String PWGUID, int position) {
		PicVideo_PreviewPopUp = new Dialog(context);
		Window window = PicVideo_PreviewPopUp.getWindow();
		// PicVideo_PreviewPopUp.getWindow().setLayout(900, 400);
		// PicVideo_PreviewPopUp.getWindow().setBackgroundDrawable(
		// new ColorDrawable(Color.TRANSPARENT));
		PicVideo_PreviewPopUp.getWindow().setBackgroundDrawableResource(
				color.white);
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		Double width = metrics.widthPixels * 20.2;
		Double height = metrics.heightPixels * 5.3;
		Window win = PicVideo_PreviewPopUp.getWindow();
		win.setLayout(width.intValue(), height.intValue());
		// window.requestFeature(Window.FEATURE_NO_TITLE);

		PicVideo_PreviewPopUp.setContentView(R.layout.popupcheckup);
		PicVideo_PreviewPopUp.setCancelable(true);
		PicVideo_PreviewPopUp.setCanceledOnTouchOutside(true);
		PicVideo_PreviewPopUp.show();

		weight1 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.weight1);
		weight2 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.weight2);
		weight3 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.weight3);
		weight4 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.weight4);
		bp1 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.bp1);
		bp2 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.bp2);
		bp3 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.bp3);
		bp4 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.bp4);
		hb1 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.hb1);
		hb2 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.hb2);
		hb3 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.hb3);
		hb4 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.hb4);
		us1 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.us1);
		us2 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.us2);
		us3 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.us3);
		us4 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.us4);
		ua1 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.ua1);
		ua2 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.ua2);
		ua3 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.ua3);
		ua4 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.ua4);
		BMI1 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.BMI1);
		BMI2 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.BMI2);
		BMI3 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.BMI3);
		BMI4 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.BMI4);
		tt1 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tt1);
		tt2 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tt2);
		tt3 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tt3);
		tt4 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tt4);
		IFA1 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.IFA1);
		IFA2 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.IFA2);
		IFA3 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.IFA3);
		IFA4 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.IFA4);
		calcium1 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.calcium1);
		calcium2 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.calcium2);
		calcium3 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.calcium3);
		calcium4 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.calcium4);
		dangersigns1 = (TextView) PicVideo_PreviewPopUp
				.findViewById(R.id.dangersigns1);
		dangersigns2 = (TextView) PicVideo_PreviewPopUp
				.findViewById(R.id.dangersigns2);
		dangersigns3 = (TextView) PicVideo_PreviewPopUp
				.findViewById(R.id.dangersigns3);
		dangersigns4 = (TextView) PicVideo_PreviewPopUp
				.findViewById(R.id.dangersigns4);
		HRP1 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.HRP1);
		HRP2 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.HRP2);
		HRP3 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.HRP3);
		HRP4 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.HRP4);
		Height1 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.Height1);
		Height2 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.Height2);
		Height3 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.Height3);
		Height4 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.Height4);
		if (pregnant.get(position).getPWHeight() > 0) {
			Height1.setText(String
					.valueOf(pregnant.get(position).getPWHeight()));
			Height2.setText(String
					.valueOf(pregnant.get(position).getPWHeight()));
			Height3.setText(String
					.valueOf(pregnant.get(position).getPWHeight()));
			Height4.setText(String
					.valueOf(pregnant.get(position).getPWHeight()));
		}
		if (pregnant.get(0).getHighRisk() > 0) {
			String hr = "";
			if (pregnant.get(0).getHighRisk() == 1) {
				hr = "Yes";
			} else {
				hr = "No";
			}
			HRP1.setText(String.valueOf(hr));
			HRP2.setText(String.valueOf(hr));
			HRP3.setText(String.valueOf(hr));
			HRP4.setText(String.valueOf(hr));
		}
		filldata(PWGUID);
	}

	public void filldata(String PWGUID) {
		VisitANC2 = dataProvider.getTbl_VisitANCData(PWGUID, "0", 0);
		DecimalFormat df = new DecimalFormat("####0.00");

		if (VisitANC2 != null && VisitANC2.size() > 0) {
			if (VisitANC2.get(0).getBirthWeight() > 0) {
				weight1.setText(String.valueOf(VisitANC2.get(0)
						.getBirthWeight()));
				double bmiCalculated = 0;
				bmiCalculated = bmi(weight1.getText().toString(), Height1
						.getText().toString());
				BMI1.setText(String.valueOf(df.format(bmiCalculated)));
			} else {
				weight1.setText("");
			}
			if (VisitANC2.get(1).getBirthWeight() > 0) {
				weight2.setText(String.valueOf(VisitANC2.get(1)
						.getBirthWeight()));
				double bmiCalculated = 0;
				bmiCalculated = bmi(weight2.getText().toString(), Height2
						.getText().toString());

				BMI2.setText(String.valueOf(df.format(bmiCalculated)));
			} else {
				weight2.setText("");
			}
			if (VisitANC2.get(2).getBirthWeight() > 0) {
				weight3.setText(String.valueOf(VisitANC2.get(2)
						.getBirthWeight()));
				double bmiCalculated = 0;
				bmiCalculated = bmi(weight3.getText().toString(), Height3
						.getText().toString());

				BMI3.setText(String.valueOf(df.format(bmiCalculated)));
			} else {
				weight3.setText("");
			}
			if (VisitANC2.get(3).getBirthWeight() > 0) {
				weight4.setText(String.valueOf(VisitANC2.get(3)
						.getBirthWeight()));
				double bmiCalculated = 0;
				bmiCalculated = bmi(weight4.getText().toString(), Height4
						.getText().toString());
				BMI4.setText(String.valueOf(df.format(bmiCalculated)));
			} else {
				weight4.setText("");
			}
			if (VisitANC2.get(0).getBPResult() != null
					&& VisitANC2.get(0).getBPResult().length() > 0) {
				String ss = VisitANC2.get(0).getBPResult();
				ss = ss.replace(",", "/");
				bp1.setText(ss);
			} else {
				bp1.setText("");
			}
			if (VisitANC2.get(1).getBPResult() != null
					&& VisitANC2.get(1).getBPResult().length() > 0) {
				String ss = VisitANC2.get(1).getBPResult();
				ss = ss.replace(",", "/");
				bp2.setText(ss);
			} else {
				bp2.setText("");
			}
			if (VisitANC2.get(2).getBPResult() != null
					&& VisitANC2.get(2).getBPResult().length() > 0) {
				String ss = VisitANC2.get(2).getBPResult();
				ss = ss.replace(",", "/");
				bp3.setText(ss);
			} else {
				bp3.setText("");
			}
			if (VisitANC2.get(3).getBPResult() != null
					&& VisitANC2.get(3).getBPResult().length() > 0) {
				String ss = VisitANC2.get(3).getBPResult();
				ss = ss.replace(",", "/");
				bp4.setText(ss);
			} else {
				bp4.setText("");
			}
			if (VisitANC2.get(0).getHemoglobin() > 0) {
				hb1.setText(String.valueOf(VisitANC2.get(0).getHemoglobin()));
			} else {
				hb1.setText("");
			}
			if (VisitANC2.get(1).getHemoglobin() > 0) {
				hb2.setText(String.valueOf(VisitANC2.get(1).getHemoglobin()));
			} else {
				hb2.setText("");
			}
			if (VisitANC2.get(2).getHemoglobin() > 0) {
				hb3.setText(String.valueOf(VisitANC2.get(2).getHemoglobin()));
			} else {
				hb3.setText("");
			}
			if (VisitANC2.get(3).getHemoglobin() > 0) {
				hb4.setText(String.valueOf(VisitANC2.get(3).getHemoglobin()));
			} else {
				hb4.setText("");
			}
			if (VisitANC2.get(0).getUrineSugar() > 0) {
				us1.setText(returnPosition(102, VisitANC2.get(0)
						.getUrineSugar()));

			} else {
				us1.setText("");
			}
			if (VisitANC2.get(1).getUrineSugar() > 0) {
				us2.setText(returnPosition(102, VisitANC2.get(1)
						.getUrineSugar()));

			} else {
				us2.setText("");
			}
			if (VisitANC2.get(2).getUrineSugar() > 0) {
				us3.setText(returnPosition(102, VisitANC2.get(2)
						.getUrineSugar()));

			} else {
				us3.setText("");
			}
			if (VisitANC2.get(3).getUrineSugar() > 0) {
				us4.setText(returnPosition(102, VisitANC2.get(3)
						.getUrineSugar()));

			} else {
				us4.setText("");
			}
			if (VisitANC2.get(0).getUrineAlbumin() > 0) {
				ua1.setText(returnPosition(102, VisitANC2.get(0)
						.getUrineAlbumin()));

			} else {
				ua1.setText("");
			}
			if (VisitANC2.get(1).getUrineAlbumin() > 0) {
				ua2.setText(returnPosition(102, VisitANC2.get(1)
						.getUrineAlbumin()));

			} else {
				ua2.setText("");
			}
			if (VisitANC2.get(2).getUrineAlbumin() > 0) {
				ua3.setText(returnPosition(102, VisitANC2.get(2)
						.getUrineAlbumin()));

			} else {
				ua3.setText("");
			}
			if (VisitANC2.get(3).getUrineAlbumin() > 0) {
				ua4.setText(returnPosition(102, VisitANC2.get(3)
						.getUrineAlbumin()));

			} else {
				ua4.setText("");
			}
			if (VisitANC2.get(0).getTT1() > 0) {
				tt1.setText(String.valueOf(VisitANC2.get(0).getTT1()));

			} else {
				tt1.setText("");
			}
			if (VisitANC2.get(1).getTT1() > 0) {
				tt2.setText(String.valueOf(VisitANC2.get(1).getTT1()));

			} else {
				tt2.setText("");
			}
			if (VisitANC2.get(2).getTT1() > 0) {
				tt3.setText(String.valueOf(VisitANC2.get(2).getTT1()));

			} else {
				tt3.setText("");
			}
			if (VisitANC2.get(3).getTT1() > 0) {
				tt4.setText(String.valueOf(VisitANC2.get(3).getTT1()));

			} else {
				tt4.setText("");
			}
			if (VisitANC2.get(0).getIFARecieved() > 0) {
				IFA1.setText(String.valueOf(VisitANC2.get(0)
						.getNumberIFARecieved()));

			} else {
				IFA1.setText("");
			}
			if (VisitANC2.get(1).getIFARecieved() > 0) {
				IFA2.setText(String.valueOf(VisitANC2.get(1)
						.getNumberIFARecieved()));

			} else {
				IFA2.setText("");
			}
			if (VisitANC2.get(2).getIFARecieved() > 0) {
				IFA3.setText(String.valueOf(VisitANC2.get(2)
						.getNumberIFARecieved()));

			} else {
				IFA3.setText("");
			}
			if (VisitANC2.get(3).getIFARecieved() > 0) {
				IFA4.setText(String.valueOf(VisitANC2.get(3)
						.getNumberIFARecieved()));

			} else {
				IFA4.setText("");
			}
			if (VisitANC2.get(0).getCalciumReceived() > 0) {
				calcium1.setText(String.valueOf(VisitANC2.get(0)
						.getCalciumTablet()));

			} else {
				calcium1.setText("");
			}
			if (VisitANC2.get(1).getCalciumReceived() > 0) {
				calcium2.setText(String.valueOf(VisitANC2.get(1)
						.getCalciumTablet()));

			} else {
				calcium2.setText("");
			}
			if (VisitANC2.get(2).getCalciumReceived() > 0) {
				calcium3.setText(String.valueOf(VisitANC2.get(2)
						.getCalciumTablet()));

			} else {
				calcium3.setText("");
			}
			if (VisitANC2.get(3).getCalciumReceived() > 0) {
				calcium4.setText(String.valueOf(VisitANC2.get(3)
						.getCalciumTablet()));

			} else {
				calcium4.setText("");
			}
			if (VisitANC2.get(0).getDangerSign() > 0) {
				if (Integer.valueOf(VisitANC2.get(0).getDangerSign()) <= arr.length)
					dangersigns1.setText(arr[Integer.valueOf(VisitANC2.get(0)
							.getDangerSign()) - 1]);

			} else {
				dangersigns1.setText("");
			}
			if (VisitANC2.get(1).getDangerSign() > 0) {
				if (Integer.valueOf(VisitANC2.get(1).getDangerSign()) <= arr1.length)
					dangersigns2.setText(arr1[Integer.valueOf(VisitANC2.get(1)
							.getDangerSign()) - 1]);

			} else {
				dangersigns2.setText("");
			}
			if (VisitANC2.get(2).getDangerSign() > 0) {
				if (Integer.valueOf(VisitANC2.get(2).getDangerSign()) <= arr1.length)
					dangersigns3.setText(arr1[Integer.valueOf(VisitANC2.get(2)
							.getDangerSign()) - 1]);

			} else {
				dangersigns3.setText("");
			}
			if (VisitANC2.get(3).getDangerSign() > 0) {
				if (Integer.valueOf(VisitANC2.get(3).getDangerSign()) <= arr1.length)
					dangersigns4.setText(arr1[Integer.valueOf(VisitANC2.get(3)
							.getDangerSign()) - 1]);

			} else {
				dangersigns4.setText("");
			}
		}
	}

	public double bmi(String weight, String height) {

		// Double height = pregnant.get(0).getPWHeight();
		double Bmi = 0;
		double ht = 1;
		if (!weight.equalsIgnoreCase("") && weight.length() > 0
				&& !height.equalsIgnoreCase("") && height.length() > 0) {
			if (!height.equalsIgnoreCase("null")) {
				ht = Double.valueOf(height);
			}
			double newHeight = Double.valueOf(ht * ht);
			Bmi = (Double.valueOf(weight) / newHeight) * 10000;
		}
		return Bmi;
	}

	public String returnPosition(int flag, int ID) {
		String ipos = "";
		ArrayList<MstCommon> combobox = new ArrayList<MstCommon>();
		combobox = dataProvider.getCommonRecord(1, flag);
		for (int i = 0; i < combobox.size(); i++) {
			if (ID == combobox.get(i).getID()) {
				ipos = combobox.get(i).getValue();
			}
		}

		return ipos;
	}

	/*
	 * public void CustomAlert() {
	 * 
	 * // Create custom dialog object final Dialog dialog = new Dialog(context);
	 * // hide to default title for Dialog
	 * dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	 * 
	 * // inflate the layout dialog_layout.xml and set it as contentView
	 * LayoutInflater inflater = (LayoutInflater) context
	 * .getSystemService(Context.LAYOUT_INFLATER_SERVICE); View view =
	 * inflater.inflate(R.layout.logout_alert, null, false);
	 * dialog.setCanceledOnTouchOutside(true); dialog.setContentView(view);
	 * dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0)); //
	 * ImageView image=(ImageView)findViewById(R.id.img_dialog_icon); //
	 * image.setVisibility(false);
	 * 
	 * // Retrieve views from the inflated dialog layout and update their //
	 * values TextView txtTitle = (TextView) dialog
	 * .findViewById(R.id.txt_alert_message); //
	 * txtTitle.setText("Do you want to add visit?");
	 * txtTitle.setText(context.getResources().getString(R.string.addvisit)); //
	 * TextView txtMessage = (TextView) //
	 * dialog.findViewById(R.id.txt_dialog_message); //
	 * txtMessage.setText("Do you want to Leave the page ?");
	 * 
	 * Button btnyes = (Button) dialog.findViewById(R.id.btn_yes);
	 * btnyes.setOnClickListener(new android.view.View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // Dismiss the dialog
	 * global.setsGlobalANCVisitGUID(""); global.setiGlobalTTFlag(1);
	 * global.setiGlobalFacilityVisit(0); Intent i = new Intent(context,
	 * AncVisitTabActivity.class); ((Activity) context).finish();
	 * context.startActivity(i); dialog.dismiss();
	 * 
	 * } });
	 * 
	 * Button btnno = (Button) dialog.findViewById(R.id.btn_no);
	 * btnno.setOnClickListener(new android.view.View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // Dismiss the dialog
	 * dialog.dismiss(); } });
	 * 
	 * // Display the dialog dialog.show();
	 * 
	 * }
	 */
	/*
	 * public void CustomAlertFacility() {
	 * 
	 * // Create custom dialog object final Dialog dialog = new Dialog(context);
	 * // hide to default title for Dialog
	 * dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	 * 
	 * // inflate the layout dialog_layout.xml and set it as contentView
	 * LayoutInflater inflater = (LayoutInflater) context
	 * .getSystemService(Context.LAYOUT_INFLATER_SERVICE); View view =
	 * inflater.inflate(R.layout.logout_alert, null, false);
	 * dialog.setCanceledOnTouchOutside(true); dialog.setContentView(view);
	 * dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0)); //
	 * ImageView image=(ImageView)findViewById(R.id.img_dialog_icon); //
	 * image.setVisibility(false);
	 * 
	 * // Retrieve views from the inflated dialog layout and update their //
	 * values TextView txtTitle = (TextView) dialog
	 * .findViewById(R.id.txt_alert_message); //
	 * txtTitle.setText("Do you want to add visit?");
	 * txtTitle.setText(context.getResources().getString(R.string.Fac_visit));
	 * // TextView txtMessage = (TextView) //
	 * dialog.findViewById(R.id.txt_dialog_message); //
	 * txtMessage.setText("Do you want to Leave the page ?");
	 * 
	 * Button btnyes = (Button) dialog.findViewById(R.id.btn_yes);
	 * btnyes.setOnClickListener(new android.view.View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // Dismiss the dialog
	 * global.setsGlobalANCVisitGUID(""); global.setiGlobalTTFlag(1);
	 * global.setiGlobalFacilityVisit(0); Intent i = new Intent(context,
	 * AncVisitTabActivity.class); ((Activity) context).finish();
	 * context.startActivity(i); dialog.dismiss();
	 * 
	 * } });
	 * 
	 * Button btnno = (Button) dialog.findViewById(R.id.btn_no);
	 * btnno.setOnClickListener(new android.view.View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // Dismiss the dialog
	 * dialog.dismiss(); } });
	 * 
	 * // Display the dialog dialog.show();
	 * 
	 * }
	 */

	/*
	 * public void CustomAlertDelete(final String sANCGUID) {
	 * 
	 * // Create custom dialog object final Dialog dialog = new Dialog(context);
	 * // hide to default title for Dialog
	 * dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	 * 
	 * // inflate the layout dialog_layout.xml and set it as contentView
	 * LayoutInflater inflater = (LayoutInflater) context
	 * .getSystemService(Context.LAYOUT_INFLATER_SERVICE); View view =
	 * inflater.inflate(R.layout.logout_alert, null, false);
	 * dialog.setCanceledOnTouchOutside(true); dialog.setContentView(view);
	 * dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0)); //
	 * ImageView image=(ImageView)findViewById(R.id.img_dialog_icon); //
	 * image.setVisibility(false);
	 * 
	 * // Retrieve views from the inflated dialog layout and update their //
	 * values TextView txtTitle = (TextView) dialog
	 * .findViewById(R.id.txt_alert_message); //
	 * txtTitle.setText("Are you sure you want to delete this record?");
	 * txtTitle.setText(context.getResources()
	 * .getString(R.string.deleterecord)); // TextView txtMessage = (TextView)
	 * // dialog.findViewById(R.id.txt_dialog_message); //
	 * txtMessage.setText("Do you want to Leave the page ?");
	 * 
	 * Button btnyes = (Button) dialog.findViewById(R.id.btn_yes);
	 * btnyes.setOnClickListener(new android.view.View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // Dismiss the dialog
	 * 
	 * DeleteData(sANCGUID); dialog.dismiss(); } });
	 * 
	 * Button btnno = (Button) dialog.findViewById(R.id.btn_no);
	 * btnno.setOnClickListener(new android.view.View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // Dismiss the dialog
	 * dialog.dismiss(); } });
	 * 
	 * // Display the dialog dialog.show();
	 * 
	 * }
	 * 
	 * public void DeleteData(String sANCGUID) { String sqldelete =
	 * "Delete from Tbl_ANC where ANC_GUID = '" + sANCGUID + "'";
	 * dataProvider.executeSql(sqldelete); String sqloutcomedelete =
	 * "Delete from Tbl_ANCOutcome where ANC_GUID = '" + sANCGUID + "'";
	 * dataProvider.executeSql(sqloutcomedelete); String sqlvisitdelete =
	 * "Delete from Tbl_ANCVisit where ANC_GUID = '" + sANCGUID + "'";
	 * dataProvider.executeSql(sqlvisitdelete);
	 * 
	 * ((AncActivity) context).fillgrid(iflag, global.getsGlobalVillageCode());
	 * }
	 */
	public void fillvisitdata(GridView gridVisits, String sPWGUID) {
		VisitANC = dataProvider.getTbl_VisitANCData(sPWGUID, "", 0);
		if (VisitANC != null && VisitANC.size() > 0) {

			android.view.ViewGroup.LayoutParams params = gridVisits
					.getLayoutParams();
			Resources r = context.getResources();
			float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
					50, r.getDisplayMetrics());
			int hi = Math.round(px);
			int gridHeight1 = 0;
			gridHeight1 = hi * VisitANC.size();
			params.height = gridHeight1;
			gridVisits.setLayoutParams(params);
			gridVisits.setAdapter(new AncVisitAdapter(context, VisitANC));

		}
	}
}