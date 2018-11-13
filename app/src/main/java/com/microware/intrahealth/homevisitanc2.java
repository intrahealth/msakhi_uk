package com.microware.intrahealth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.Editable;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.microware.intrahealth.Global;
import com.microware.intrahealth.HomeVisit;
import com.microware.intrahealth.R;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.TblANCVisit;

@SuppressLint("ClickableViewAccessibility")
public class homevisitanc2 extends Activity {
	// ExtendedViewPager viewPager;
	PagerAdapter adapter;
	int[] flag;
	String[] rank;
	static TextView pagenumber, tvans;
	ImageView imgleft, imgright;
	ViewPager mViewPager;
	DataProvider dataProvider;
	Button imgNext, imgEnd;
	Global global;
	static TableRow tbltvans;
	static TableRow tbletans;
	static int pos = 0;
	static int ans = 0;
	static int dateans = 0;
	static int data = 0;
	static float dataet = 0;
	static String datatv = "";
	static String HomeVisitDate = "";
	private String[] images = {
			" पंजीकरण  ",
			"तुरंत पंजीकरण का महत्व  ",
			"तुरंत पंजीकरण का फायदा ",
			"4 प्रसव पूर्व जांचें अवश्य कराएं ",
			"एमसीपी कार्ड को सावधानी से रखें ",
			"गर्भ का पता चलते ही पहली प्रसवपूर्व जांच ",
			"प्रसवपूर्व जांच के लिए एमसीपी कार्ड हमेशा लायें ",
			" एमसीपी कार्ड  ",
			"टीटी1",
			"टीटी2",
			"टीटी बूस्टर",
			"वजन",

			"ब्लड प्रेशर",

			"एचबी टेस्ट",

			"पेशाब की जांच (शुगर/शक्कर)",

			"पेशाब की जांच (एल्ब्यूमिन)",

			"आयरन की गोलियां",

			"प्रसवपूर्व जांच 2",

			" टीटी के इंजेक्शन का महत्व  ",
			"आयरन की गोलियों का महत्व ",
			"गर्भावस्था में आयरन की आवश्यकता बढ़ती है  ",
			"आयरन की गोलियों की संख्या",
			"एनीमिया या खून की कमी",
			"एनीमिया से खतरा ",
			"एनीमिया से माँ को खतरा है ",
			"आयरन और फोलिक एसिड की गोलियां निरंतर लें  ",
			"चाय के साथ आयरन की गोलियां न लें ",
			"काली लैट्रिन और कब्ज सामान्य है ",
			"आयरन युक्त भोजन खाएं  ",
			"कैल्शियम की गोलियां  ",
			"कैल्शियम एव आयरन की गोली कभी भी एक साथ न ले। ",
			"धात्री माताओं भी खाएं ",
			"गर्भावस्था के दौरान पेट के कीड़े मारने की दवाई एल्बेंडाजोल पहले तीन माह के बाद एक खुराक लेनी चाहियेें।एल्बेंडाजोल गर्भावस्था के पहले तीन महीनों में न लें ",
			"पेट के कीड़े मारने की दवाई एल्बेंडाजोल",
			"गर्भावस्था में घर पर देखभाल ", "पोषक भोजन खाएं  ",
			"ज्यादा मात्रा में खाना लें  ", "दिन में दो घंटे आराम करें ",
			"भारी शारीरिक काम न करें  ", "प्रसव की तैयारी ",
			"प्रसव हेतु अस्पताल की पहचान करें ",
			"प्रसव हेतु ले जाने के लिए साधन की व्यवस्था करें ",
			"एमरजेंसी के लिए पैसे बचाएं ",
			"प्रसव के बाद 2 दिनों तक अस्पताल में रुकें ",
			"घर में प्रसव की योजना ",
			"प्रसव केवल एएनएम या प्रशिक्षित नर्स द्वारा ",
			"प्रसव के स्थान को साफ़ और गर्म रखें ", "हाथ अच्छी तरह धोएं ",
			"नाल पर कुछ न लगाएं ",
			"नाल को काटने के लिए नए ब्लेड का इस्तेमाल करें ",
			"गर्भावस्था में खतरे के लक्षण ",
			"इन खतरे के लक्षणों में डॉक्टर या एएनएम से संपर्क करें ",
			"रक्तस्राव ", "तेज बुखार", "पेट में तेज दर्द ",
			"सिर में दर्द धुंधला दिखना ", "दौरे आना या फिट्स पड़ना ",
			"चेहरे, हाथों, पांवों में सूजन ",
			"योनि से गंदे तथा बदबूदार सफेद पानी का निकलना",
			"	प्रसव पश्चात परिवार नियोजन ", "परिवार नियोजन का महत्व 	 ",
			"गर्भावस्था में अनुचित अंतर ", "प्रसव पश्चात परिवार नियोजन ",
			"गर्भ निरोधन के स्थाई साधन ", "महिला नसबंदी ",
			" डॉक्टर से नसबंदी ", "7 दिनों या घंटों में या 6 सप्ताह बाद ",
			"प्रभावी और सुरक्षित साधन ", "किसी भी मौसम में की जा सकती है ",
			"ऑपरेशन के बाद कोई कमजोरी नहीं ",
			"सरकारी अस्पताल में बिलकुल मुफ्त में उपलब्ध",
			"एएनएम या डॉक्टर से अधिक जानकारी लें ", "पुरुष नसबंदी ",
			"बिना चीरा और टांका ", "किसी तरह की कोई कमजोरी नहीं ",
			"प्रभावी और सुरक्षित साधन ",
			"सरकारी अस्पताल में बिलकुल मुफ्त में उपलब्ध",
			"एएनएम या डॉक्टर से अधिक जानकारी लें ",
			"2 बच्चों के बीच अंतर माँ व बच्चे दोनों के लिए अच्छा है ",
			"यौन संबंधों में देरी रखना ", "लैम ",
			"बच्चे को पहले 6 सप्ताह तक सिर्फ स्तनपान कराना ", "पहली स्थिति ",
			"दूसरी स्थिति  ", "तीसरी स्थिति ",
			"परिवार नियोजन के किसी एक साधन का प्रयोग करने की योजना  ",
			"कॉपर टी",
			"डॉक्टर या नर्स के द्वारा अस्पताल में लगवाई जा सकती है ",
			"48 घंटों के अन्दर या 6 सप्ताह बाद ", "10 साल तक प्रभावी ",
			"स्तनपान पर प्रभाव नहीं डालती ", "बिलकुल मुफ्त में उपलब्ध ",
			"कुछ महीनों बाद अतिरिक्त प्रभाव कम होते हैं ",
			"एएनएम या डॉक्टर से अधिक जानकारी हेतु पूछें  ", "कॉन्डोम ",
			"सरल और प्रभावी ", "गर्भावस्था और यौन जनित बीमारियों से बचाता है ",
			"कंडोम फटना", "आशा से कंडोम के लिए कहें ",
			"कंडोम आसानी से बाजार में उपलब्ध " };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.pregnantwomenvisit);
		// getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
		// R.layout.titlebar);
		// setTaskBarColored(this);
		dataProvider = new DataProvider(this);
		global = (Global) getApplication();
		pagenumber = (TextView) findViewById(R.id.pagenumber);

		mViewPager = (ViewPager) findViewById(R.id.treatmentviewpager);

		// imgleft = (ImageView) findViewById(R.id.imgleft);
		// imgright = (ImageView) findViewById(R.id.imgright);
		imgNext = (Button) findViewById(R.id.imgNext);
		imgEnd = (Button) findViewById(R.id.imgEnd);

		// imgleft.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// previousPage();
		// }
		// });
		//
		// imgright.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// nextPage();
		//
		// }
		// });
		imgNext.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				mynextPage();

			}
		});
		imgEnd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// nextPage();
				finish();
			}
		});
		// Pass results to ViewPagerAdapter Class
		// String[] data2 = {"page1", "page2", "page3", "page4", "page5",
		// "page6"};
		adapter = new TouchImageAdapter(homevisitanc2.this);
		// Binds the Adapter to the ViewPager
		mViewPager.setAdapter(adapter);
		mViewPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return true;
			}
		});
		// mViewPager.addOnPageChangeListener(new OnPageChangeListener() {
		//
		// @Override
		// public void onPageSelected(int arg0) {
		// // TODO Auto-generated method stub
		//
		// pagenumber.setText(String.valueOf(arg0 + 1) + "/7");
		// if((arg0+1)==7)
		// {
		// imgright.setVisibility(View.INVISIBLE);
		// }
		// else{
		// imgright.setVisibility(View.VISIBLE);
		// }
		//
		// }
		//
		// @Override
		// public void onPageScrolled(int arg0, float arg1, int arg2) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void onPageScrollStateChanged(int arg0) {
		// // TODO Auto-generated method stub
		//
		// }
		// });

	}

	// @SuppressLint("NewApi")
	// public static void setTaskBarColored(Activity context) {
	// if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
	// Window window = context.getWindow();
	//
	// // clear FLAG_TRANSLUCENT_STATUS flag:
	// window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
	//
	// // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
	// window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
	//
	// // finally change the color
	// window.setStatusBarColor(context.getResources().getColor(
	// R.color.statusbarcolor));
	// }
	// }

	class TouchImageAdapter extends PagerAdapter {

		Context context;

		LayoutInflater inflater;
		Spannable spans;
		ClickableSpan clickSpan;
		DataProvider dataProvider;
		Global global;
		Dialog datepic;
		ArrayList<TblANCVisit> AncVisit = new ArrayList<TblANCVisit>();
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();

		public TouchImageAdapter(Context context) {
			this.context = context;
			dataProvider = new DataProvider(context);
			global = (Global) context.getApplicationContext();
			if (global.getsGlobalANCVisitGUID() != null
					&& global.getsGlobalANCVisitGUID().length() > 0) {
				showdata();
			}
		}

		// private static int[] rank = new int[] { 1, 2, 3, 4, 5, 6, 7 };

		@Override
		public int getCount() {
			return images.length;

		}

		@SuppressWarnings("unused")
		@Override
		public View instantiateItem(ViewGroup container, int position) {
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View itemView = inflater.inflate(R.layout.viewpager_item,
					container, false);
			final int newpos = position;
			TextView tvQuestions = (TextView) itemView
					.findViewById(R.id.tvQuestions);
			final TextView tvdateans = (TextView) itemView
					.findViewById(R.id.tvdateans);
			final EditText etAns = (EditText) itemView.findViewById(R.id.etAns);
			TableRow tblrdgrp5 = (TableRow) itemView
					.findViewById(R.id.tblrdgrp5);
			TableRow tblrdgrp4 = (TableRow) itemView
					.findViewById(R.id.tblrdgrp4);
			TableRow tblrdgrp3 = (TableRow) itemView
					.findViewById(R.id.tblrdgrp3);
			TableRow tblrdgrp2 = (TableRow) itemView
					.findViewById(R.id.tblrdgrp2);
			TableRow tblrdgrp1 = (TableRow) itemView
					.findViewById(R.id.tblrdgrp1);
			final TableRow tbltvans = (TableRow) itemView
					.findViewById(R.id.tbltvans);
			final TableRow tbletans = (TableRow) itemView
					.findViewById(R.id.tbletans);
			final TextView tvdata = (TextView) itemView
					.findViewById(R.id.tvdata);
			final TextView tvdatatv = (TextView) itemView
					.findViewById(R.id.tvdatatv);
			final TextView tvans = (TextView) itemView.findViewById(R.id.tvans);
			TableRow tblrdgrp = (TableRow) itemView.findViewById(R.id.tblrdgrp);
			RadioGroup rdgrp = (RadioGroup) itemView.findViewById(R.id.rdgrp);
			RadioGroup rdgrp1 = (RadioGroup) itemView.findViewById(R.id.rdgrp1);
			RadioGroup rdgrp2 = (RadioGroup) itemView.findViewById(R.id.rdgrp2);
			RadioGroup rdgrp3 = (RadioGroup) itemView.findViewById(R.id.rdgrp3);
			RadioGroup rdgrp4 = (RadioGroup) itemView.findViewById(R.id.rdgrp4);
			RadioGroup rdgrp5 = (RadioGroup) itemView.findViewById(R.id.rdgrp5);
			RadioButton radioyes = (RadioButton) itemView
					.findViewById(R.id.radioyes);

			RadioButton radiono = (RadioButton) itemView
					.findViewById(R.id.radiono);
			RadioButton radioyesavl = (RadioButton) itemView
					.findViewById(R.id.radioyesavl);
			RadioButton radionotavl = (RadioButton) itemView
					.findViewById(R.id.radionotavl);
			RadioButton radionotissued = (RadioButton) itemView
					.findViewById(R.id.radionotissued);
			RadioButton radiono1 = (RadioButton) itemView
					.findViewById(R.id.radiono1);
			RadioButton radioyes1 = (RadioButton) itemView
					.findViewById(R.id.radioyes1);
			RadioButton radionotm = (RadioButton) itemView
					.findViewById(R.id.radionotm);
			RadioButton radionotkids = (RadioButton) itemView
					.findViewById(R.id.radionotkids);
			RadioButton radioaftersometime = (RadioButton) itemView
					.findViewById(R.id.radioaftersometime);
			RadioButton radiodontknow = (RadioButton) itemView
					.findViewById(R.id.radiodontknow);
			RadioButton radionotgiven = (RadioButton) itemView
					.findViewById(R.id.radionotgiven);
			RadioButton radiogiven = (RadioButton) itemView
					.findViewById(R.id.radiogiven);
			RadioButton radionotdone = (RadioButton) itemView
					.findViewById(R.id.radionotdone);
			RadioButton radiodone = (RadioButton) itemView
					.findViewById(R.id.radiodone);
			TextView tvdatafield = (TextView) itemView
					.findViewById(R.id.tvdatafield);
			// TextView tvdateans = (TextView) itemView
			// .findViewById(R.id.tvdateans);
			tvdateans.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub
					ShowDatePicker(tvdateans, tvdatatv);
				}
			});
			tvdateans.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					// TODO Auto-generated method stub
					if (s.length() > 0) {

					}
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub

				}

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub

					// datatv=Validate.changeDateFormat(tvans.getText().toString());

				}
			});
			etAns.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					// TODO Auto-generated method stub

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub

				}

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					if (s.length() > 0) {
						tvdatatv.setText(etAns.getText().toString());
					} else {
						tvdatatv.setText("");
					}
				}
			});

			rdgrp.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub
					// || newpos == 23
					// || newpos == 24 || newpos == 25 || newpos == 26
					// || newpos == 27 || newpos == 28 || newpos == 29
					// || newpos == 30
					if (newpos == 8 || newpos == 9) {
						if (checkedId == R.id.radioyes) {
							tvdata.setText("1");
							showhide(tbltvans, 1);
						} else if (checkedId == R.id.radiono) {
							tvdata.setText("0");
							showhide(tbltvans, 0);
							tvans.setText("");
							tvdateans.setText("");
							etAns.setText("");

						}
					} else if (newpos == 11 || newpos == 12) {
						if (checkedId == R.id.radioyes) {
							tvdata.setText("1");
							showhide(tbletans, 1);
							if (newpos == 11) {
								tvans.setText("Weight(in kg)");
							} else if (newpos == 12) {
								tvans.setText("BP(in mmHg)");
							}
						} else if (checkedId == R.id.radiono) {
							showhide(tbletans, 0);
							tvans.setText("");
							tvdateans.setText("");
							etAns.setText("");

						}
					}
					if (checkedId == R.id.radioyes) {
						tvdata.setText("1");
						// ans=1;
					} else if (checkedId == R.id.radiono) {
						tvdata.setText("0");
						// ans=0;
						showhide(tbltvans, 0);
						tvans.setText("");
						tvdateans.setText("");
						etAns.setText("");

					}
				}

			});
			rdgrp1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub
					if (newpos == 17) {
						if (checkedId == R.id.radiodone) {
							tvdata.setText("1");
							showhide(tbltvans, 1);

						} else if (checkedId == R.id.radionotdone) {
							tvdata.setText("0");
							showhide(tbltvans, 0);
						}
					}
					if (newpos == 13) {
						if (checkedId == R.id.radiodone) {
							tvdata.setText("1");
							showhide(tbletans, 1);
							tvans.setText("हिमोग्लोबिन (ग्राम%)");

						} else if (checkedId == R.id.radionotdone) {
							tvdata.setText("0");
							showhide(tbltvans, 0);
							tvans.setText("");
							tvdateans.setText("");
							etAns.setText("");

						}

					}
					if (checkedId == R.id.radiodone) {
						tvdata.setText("1");

					} else if (checkedId == R.id.radionotdone) {
						tvdata.setText("0");

					}
				}
			});

			rdgrp2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub
					if (newpos == 16) {
						if (checkedId == R.id.radiogiven) {
							tvdata.setText("1");
							showhide(tbletans, 1);
							tvans.setText("कितनी प्राप्त हुई (संख्या)");
						} else if (checkedId == R.id.radionotgiven) {
							tvdata.setText("0");
							showhide(tbletans, 0);
							tvans.setText("");
							tvdateans.setText("");
							etAns.setText("");

						}
					}
					if (checkedId == R.id.radiogiven) {
						tvdata.setText("1");

					} else if (checkedId == R.id.radionotgiven) {
						tvdata.setText("0");

					}
				}

			});

			rdgrp3.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub

					if (checkedId == R.id.radionotkids) {
						tvdata.setText("0");

					} else if (checkedId == R.id.radioaftersometime) {
						tvdata.setText("1");

					} else if (checkedId == R.id.radiodontknow) {
						tvdata.setText("2");
					}
				}

			});
			rdgrp4.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub

					if (checkedId == R.id.radioyes1) {
						tvdata.setText("1");
						showhide(tbltvans, 1);
					} else if (checkedId == R.id.radiono1) {
						tvdata.setText("0");
						showhide(tbltvans, 0);
					} else if (checkedId == R.id.radionotm) {
						tvdata.setText("2");
						showhide(tbltvans, 0);
					}
				}

			});
			rdgrp5.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub

					if (checkedId == R.id.radioyesavl) {
						tvdata.setText("1");

					} else if (checkedId == R.id.radionotissued) {
						tvdata.setText("0");

					} else if (checkedId == R.id.radionotavl) {
						tvdata.setText("2");
					}
				}

			});

			LinearLayout txtcontentHeading = (LinearLayout) itemView
					.findViewById(R.id.txtcontentHeading);

			int newp = position + 1;
			tvQuestions.setText(images[position]);

			tvQuestions.setVisibility(View.VISIBLE);
			txtcontentHeading.setVisibility(View.VISIBLE);

			if (position == 0 || position == 8 || position == 9
					|| position == 11 || position == 12 || position == 14
					|| position == 15 || position == 39) {

				tblrdgrp.setVisibility(View.VISIBLE);

			} else {
				tblrdgrp.setVisibility(View.GONE);
				tvans.setText("");
				tvdateans.setText("");
				etAns.setText("");

			}
			if (position == 10) {

				tblrdgrp4.setVisibility(View.VISIBLE);
			} else {
				tblrdgrp4.setVisibility(View.GONE);
				tvans.setText("");
				tvdateans.setText("");
				etAns.setText("");

			}
			if (position == 59) {
				tblrdgrp3.setVisibility(View.VISIBLE);
			} else {
				tblrdgrp3.setVisibility(View.GONE);
				tvans.setText("");
				tvdateans.setText("");
				etAns.setText("");
			}
			if (position == 13 || position == 17) {

				tblrdgrp1.setVisibility(View.VISIBLE);
			} else {
				tblrdgrp1.setVisibility(View.GONE);
				tvans.setText("");
				tvdateans.setText("");
				etAns.setText("");

			}
			if (position == 16) {

				tblrdgrp2.setVisibility(View.VISIBLE);
			} else {
				tblrdgrp2.setVisibility(View.GONE);
				tvans.setText("");
				tvdateans.setText("");
				etAns.setText("");

			}

			if (position == 7) {

				tblrdgrp5.setVisibility(View.VISIBLE);
			} else {
				tvdatafield.setText("McpCard");
				tblrdgrp5.setVisibility(View.GONE);
				tvans.setText("");
				tvdateans.setText("");
				etAns.setText("");

			}
			// if()
			if (position == 0) {
				try {
					String name = ja.getJSONObject(0).getString("PregWomenReg");
					System.out.print("name" + name);
					int value = Validate.returnIntegerValue(name);
					tvdata.setText(String.valueOf(value));
					if (value == 1) {
						radioyes.setChecked(true);
					} else if (value == 0) {
						radiono.setChecked(true);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (position == 7) {
				try {
					String name = ja.getJSONObject(0).getString("McpCard");
					System.out.print("name" + name);

					int value = Validate.returnIntegerValue(name);
					tvdata.setText(String.valueOf(value));
					if (value == 1) {

						radioyesavl.setChecked(true);
					} else if (value == 0) {
						radionotissued.setChecked(true);
					} else if (value == 2) {
						radionotavl.setChecked(true);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (position == 8) {
				try {
					String name = ja.getJSONObject(0).getString("TT1");

					String name2 = ja.getJSONObject(0).getString("TT1date");
					System.out.print("name" + name2);
					int value = Validate.returnIntegerValue(name);
					tvdata.setText(String.valueOf(value));

					if (value == 1) {
						radioyes.setChecked(true);
						showhide(tbltvans, 1);
						if (name2 != null && name2.length() > 0) {
							tvdateans.setText(Validate.changeDateFormat(name2));
							tvdatatv.setText(name2);
						} else {
							tvdateans.setText("");
							tvdatatv.setText("");
						}
					} else if (value == 0) {
						radiono.setChecked(true);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (position == 9) {
				try {
					String name = ja.getJSONObject(0).getString("TT2");
					String name2 = ja.getJSONObject(0).getString("TT2date");
					System.out.print("name" + name);

					int value = Validate.returnIntegerValue(name);
					tvdata.setText(String.valueOf(value));
					// tvdatatv.setText(name2);

					if (value == 1) {
						radioyes.setChecked(true);
						showhide(tbltvans, 1);
						if (name2 != null && name2.length() > 0) {
							tvdateans.setText(Validate.changeDateFormat(name2));
							tvdatatv.setText(name2);
						} else {
							tvdateans.setText("");
							tvdatatv.setText("");
						}
					} else if (value == 0) {
						radiono.setChecked(true);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (position == 10) {
				try {
					String name = ja.getJSONObject(0).getString("TTbooster");
					String name2 = ja.getJSONObject(0).getString(
							"TTboosterDate1");
					System.out.print("name" + name);
					int value = Validate.returnIntegerValue(name);
					tvdata.setText(String.valueOf(value));

					if (value == 1) {
						radioyes1.setChecked(true);
						showhide(tbltvans, 1);
						if (name2 != null && name2.length() > 0) {
							tvdateans.setText(Validate.changeDateFormat(name2));
							tvdatatv.setText(name2);
						} else {
							tvdateans.setText("");
						}
					} else if (value == 0) {
						radiono1.setChecked(true);
					} else if (value == 2) {
						radionotm.setChecked(true);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (position == 11) {
				try {
					String name = ja.getJSONObject(0).getString("Weight1YN");
					String name2 = ja.getJSONObject(0).getString("Weight1");
					System.out.print("name" + name);
					tvdatatv.setText(name2);
					int value = Validate.returnIntegerValue(name);
					tvdata.setText(String.valueOf(value));
					if (value == 1) {
						radioyes.setChecked(true);
						showhide(tbletans, 1);
						etAns.setText(name2);
					} else if (value == 0) {
						radiono.setChecked(true);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// if(position==12){
			// try {
			// String name = ja.getJSONObject(0).getString("Weight2YN");
			// String name2 = ja.getJSONObject(0).getString("Weight2");
			// dataet=Float.valueOf(name2);
			// System.out.print("name"+name);
			// int value=Validate.returnIntegerValue(name);
			// data=value;
			// if(value==1){
			// radioyes.setChecked(true);
			// showhide(tbletans, 1);
			// etAns.setText(name2);
			// }else if(value==0){
			// radiono.setChecked(true);
			// }
			// } catch (JSONException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
			// if(position==13){
			// try {
			// String name = ja.getJSONObject(0).getString("Weight3YN");
			// String name2 = ja.getJSONObject(0).getString("Weight3");
			// dataet=Float.valueOf(name2);
			// System.out.print("name"+name);
			// int value=Validate.returnIntegerValue(name);
			// data=value;
			// if(value==1){
			// radioyes.setChecked(true);
			// showhide(tbletans, 1);
			// etAns.setText(name2);
			// }else if(value==0){
			// radiono.setChecked(true);
			// }
			// } catch (JSONException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
			// if(position==14){
			// try {
			// String name = ja.getJSONObject(0).getString("Weight4YN");
			// String name2 = ja.getJSONObject(0).getString("Weight4");
			// dataet=Float.valueOf(name2);
			// System.out.print("name"+name);
			// int value=Validate.returnIntegerValue(name);
			// data=value;
			// if(value==1){
			// radioyes.setChecked(true);
			// showhide(tbletans, 1);
			// etAns.setText(name2);
			// }else if(value==0){
			// radiono.setChecked(true);
			// }
			// } catch (JSONException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
			if (position == 12) {
				try {
					String name = ja.getJSONObject(0).getString("BP1YN");
					String name2 = ja.getJSONObject(0).getString("BP1");
					tvdatatv.setText(name2);
					System.out.print("name" + name);
					int value = Validate.returnIntegerValue(name);
					tvdata.setText(String.valueOf(value));
					if (value == 1) {
						radioyes.setChecked(true);
						showhide(tbletans, 1);
						etAns.setText(name2);
					} else if (value == 0) {
						radiono.setChecked(true);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// if(position==16){
			// try {
			// String name = ja.getJSONObject(0).getString("BP2YN");
			// String name2 = ja.getJSONObject(0).getString("BP2");
			// dataet=Float.valueOf(name2);
			// System.out.print("name"+name);
			// int value=Validate.returnIntegerValue(name);
			// data=value;
			// if(value==1){
			// radioyes.setChecked(true);
			// showhide(tbletans, 1);
			// etAns.setText(name2);
			// }else if(value==0){
			// radiono.setChecked(true);
			// }
			// } catch (JSONException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
			// if(position==17){
			// try {
			// String name = ja.getJSONObject(0).getString("BP3YN");
			// String name2 = ja.getJSONObject(0).getString("BP3");
			// dataet=Float.valueOf(name2);
			// System.out.print("name"+name);
			// int value=Validate.returnIntegerValue(name);
			// data=value;
			// if(value==1){
			// radioyes.setChecked(true);
			// showhide(tbletans, 1);
			// etAns.setText(name2);
			// }else if(value==0){
			// radiono.setChecked(true);
			// }
			// } catch (JSONException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
			//
			// if(position==18){
			// try {
			// String name = ja.getJSONObject(0).getString("BP4YN");
			// String name2 = ja.getJSONObject(0).getString("BP4");
			// dataet=Float.valueOf(name2);
			// System.out.print("name"+name);
			// int value=Validate.returnIntegerValue(name);
			// data=value;
			// if(value==1){
			// radioyes.setChecked(true);
			// showhide(tbletans, 1);
			// etAns.setText(name2);
			// }else if(value==0){
			// radiono.setChecked(true);
			// }
			// } catch (JSONException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
			//
			if (position == 13) {
				try {
					String name = ja.getJSONObject(0).getString("HB1YN");
					String name2 = ja.getJSONObject(0).getString("HB1");
					tvdatatv.setText(name2);
					System.out.print("name" + name);
					int value = Validate.returnIntegerValue(name);
					tvdata.setText(String.valueOf(value));
					if (value == 1) {
						radiodone.setChecked(true);
						showhide(tbletans, 1);
						etAns.setText(name2);
					} else if (value == 0) {
						radionotdone.setChecked(true);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// if(position==20){
			// try {
			// String name = ja.getJSONObject(0).getString("HB2YN");
			// String name2 = ja.getJSONObject(0).getString("HB2");
			// dataet=Float.valueOf(name2);
			// System.out.print("name"+name);
			// int value=Validate.returnIntegerValue(name);
			// data=value;
			// if(value==1){
			// radiodone.setChecked(true);
			// showhide(tbletans, 1);
			// etAns.setText(name2);
			// }else if(value==0){
			// radionotdone.setChecked(true);
			// }
			// } catch (JSONException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
			//
			// if(position==21){
			// try {
			// String name = ja.getJSONObject(0).getString("HB3YN");
			// String name2 = ja.getJSONObject(0).getString("HB3");
			// dataet=Float.valueOf(name2);
			// System.out.print("name"+name);
			// int value=Validate.returnIntegerValue(name);
			// data=value;
			// if(value==1){
			// radiodone.setChecked(true);
			// showhide(tbletans, 1);
			// etAns.setText(name2);
			// }else if(value==0){
			// radionotdone.setChecked(true);
			// }
			// } catch (JSONException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
			//
			// if(position==22){
			// try {
			// String name = ja.getJSONObject(0).getString("HB4YN");
			// String name2 = ja.getJSONObject(0).getString("HB4");
			// dataet=Float.valueOf(name2);
			// System.out.print("name"+name);
			// int value=Validate.returnIntegerValue(name);
			// data=value;
			// if(value==1){
			// radiodone.setChecked(true);
			// showhide(tbletans, 1);
			// etAns.setText(name2);
			// }else if(value==0){
			// radionotdone.setChecked(true);
			// }
			// } catch (JSONException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
			if (position == 14) {
				try {
					String name = ja.getJSONObject(0).getString(
							"UrineTestsugar1YN");
					String name2 = ja.getJSONObject(0).getString(
							"UrineTestsugar1");
					tvdatatv.setText(name2);
					System.out.print("name" + name);
					int value = Validate.returnIntegerValue(name);
					tvdata.setText(String.valueOf(value));
					if (value == 1) {
						radioyes.setChecked(true);
						// showhide(tbletans, 1);
						// etAns.setText(name2);
					} else if (value == 0) {
						radiono.setChecked(true);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// if(position==24){
			// try {
			// String name = ja.getJSONObject(0).getString("UrineTestsugar2YN");
			// String name2 = ja.getJSONObject(0).getString("UrineTestsugar2");
			// dataet=Float.valueOf(name2);
			// System.out.print("name"+name);
			// int value=Validate.returnIntegerValue(name);
			// data=value;
			// if(value==1){
			// radioyes.setChecked(true);
			// // showhide(tbletans, 1);
			// // etAns.setText(name2);
			// }else if(value==0){
			// radiono.setChecked(true);
			// }
			// } catch (JSONException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
			// if(position==25){
			// try {
			// String name = ja.getJSONObject(0).getString("UrineTestsugar3YN");
			// String name2 = ja.getJSONObject(0).getString("UrineTestsugar3");
			// dataet=Float.valueOf(name2);
			// System.out.print("name"+name);
			// int value=Validate.returnIntegerValue(name);
			// data=value;
			// if(value==1){
			// radioyes.setChecked(true);
			// // showhide(tbletans, 1);
			// // etAns.setText(name2);
			// }else if(value==0){
			// radiono.setChecked(true);
			// }
			// } catch (JSONException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
			// if(position==26){
			// try {
			// String name = ja.getJSONObject(0).getString("UrineTestsugar4YN");
			// String name2 = ja.getJSONObject(0).getString("UrineTestsugar4");
			// dataet=Float.valueOf(name2);
			// System.out.print("name"+name);
			// int value=Validate.returnIntegerValue(name);
			// data=value;
			// if(value==1){
			// radioyes.setChecked(true);
			// // showhide(tbletans, 1);
			// // etAns.setText(name2);
			// }else if(value==0){
			// radiono.setChecked(true);
			// }
			// } catch (JSONException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
			if (position == 15) {
				try {
					String name = ja.getJSONObject(0).getString(
							"UrineTestAl1YN");
					String name2 = ja.getJSONObject(0)
							.getString("UrineTestAl1");
					tvdatatv.setText(name2);
					System.out.print("name" + name);
					int value = Validate.returnIntegerValue(name);
					tvdata.setText(String.valueOf(value));
					if (value == 1) {
						radioyes.setChecked(true);
						// showhide(tbletans, 1);
						// etAns.setText(name2);
					} else if (value == 0) {
						radiono.setChecked(true);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// if(position==28){
			// try {
			// String name = ja.getJSONObject(0).getString("UrineTestAl2YN");
			// String name2 = ja.getJSONObject(0).getString("UrineTestAl2");
			// dataet=Float.valueOf(name2);
			// System.out.print("name"+name);
			// int value=Validate.returnIntegerValue(name);
			// data=value;
			// if(value==1){
			// radioyes.setChecked(true);
			// // showhide(tbletans, 1);
			// // etAns.setText(name2);
			// }else if(value==0){
			// radiono.setChecked(true);
			// }
			// } catch (JSONException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
			// if(position==29){
			// try {
			// String name = ja.getJSONObject(0).getString("UrineTestAl3YN");
			// String name2 = ja.getJSONObject(0).getString("UrineTestAl3");
			// dataet=Float.valueOf(name2);
			// System.out.print("name"+name);
			// int value=Validate.returnIntegerValue(name);
			// data=value;
			// if(value==1){
			// radioyes.setChecked(true);
			// // showhide(tbletans, 1);
			// // etAns.setText(name2);
			// }else if(value==0){
			// radiono.setChecked(true);
			// }
			// } catch (JSONException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
			// if(position==30){
			// try {
			// String name = ja.getJSONObject(0).getString("UrineTestAl4YN");
			// String name2 = ja.getJSONObject(0).getString("UrineTestAl4");
			// dataet=Float.valueOf(name2);
			// System.out.print("name"+name);
			// int value=Validate.returnIntegerValue(name);
			// data=value;
			// if(value==1){
			// radioyes.setChecked(true);
			// // showhide(tbletans, 1);
			// // etAns.setText(name2);
			// }else if(value==0){
			// radiono.setChecked(true);
			// }
			// } catch (JSONException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
			if (position == 16) {
				try {
					String name = ja.getJSONObject(0)
							.getString("IronTablet1YN");
					String name2 = ja.getJSONObject(0).getString("IronTablet1");
					tvdatatv.setText(name2);
					System.out.print("name" + name);
					int value = Validate.returnIntegerValue(name);
					tvdata.setText(String.valueOf(value));
					if (value == 1) {
						radiogiven.setChecked(true);
						showhide(tbletans, 1);
						etAns.setText(name2);
					} else if (value == 0) {
						radionotgiven.setChecked(true);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// if(position==32){
			// try {
			// String name = ja.getJSONObject(0).getString("IronTablet2YN");
			// String name2 = ja.getJSONObject(0).getString("IronTablet2");
			// dataet=Float.valueOf(name2);
			// System.out.print("name"+name);
			// int value=Validate.returnIntegerValue(name);
			// data=value;
			// if(value==1){
			// radiogiven.setChecked(true);
			// showhide(tbletans, 1);
			// etAns.setText(name2);
			// }else if(value==0){
			// radionotgiven.setChecked(true);
			// }
			// } catch (JSONException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
			// if(position==33){
			// try {
			// String name = ja.getJSONObject(0).getString("IronTablet3YN");
			// String name2 = ja.getJSONObject(0).getString("IronTablet3");
			// dataet=Float.valueOf(name2);
			// System.out.print("name"+name);
			// int value=Validate.returnIntegerValue(name);
			// data=value;
			// if(value==1){
			// radiogiven.setChecked(true);
			// showhide(tbletans, 1);
			// etAns.setText(name2);
			// }else if(value==0){
			// radionotgiven.setChecked(true);
			// }
			// } catch (JSONException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
			// if(position==34){
			// try {
			// String name = ja.getJSONObject(0).getString("IronTablet4YN");
			// String name2 = ja.getJSONObject(0).getString("IronTablet4");
			// dataet=Float.valueOf(name2);
			// System.out.print("name"+name);
			// int value=Validate.returnIntegerValue(name);
			// data=value;
			// if(value==1){
			// radiogiven.setChecked(true);
			// // showhide(tbletans, 1);
			// // etAns.setText(name2);
			// }else if(value==0){
			// radionotgiven.setChecked(true);
			// }
			// } catch (JSONException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
			if (position == 17) {
				try {
					String name = ja.getJSONObject(0)
							.getString("AncCheckup1YN");
					String name2 = ja.getJSONObject(0).getString("AncCheckup1");

					tvdatatv.setText(name2);
					// if(name2.length()>0){
					// tvdateans.setText(Validate.changeDateFormat(name2));
					// }
					System.out.print("name" + name);
					int value = Validate.returnIntegerValue(name);
					tvdata.setText(String.valueOf(value));
					if (value == 1) {
						radiodone.setChecked(true);
						showhide(tbltvans, 1);
						if (name2 != null && name2.length() > 0) {
							tvdateans.setText(Validate.changeDateFormat(name2));
							tvdatatv.setText(name2);
						} else {
							tvdateans.setText("");
						}
					} else if (value == 0) {
						radionotdone.setChecked(true);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (position == 39) {
				try {
					String name = ja.getJSONObject(0).getString(
							"DeliveryONhospYN");
					// String name2 =
					// ja.getJSONObject(0).getString("AncCheckup4");
					System.out.print("name" + name);
					int value = Validate.returnIntegerValue(name);
					tvdata.setText(String.valueOf(value));
					if (value == 1) {
						radioyes.setChecked(true);

					} else if (value == 0) {
						radiono.setChecked(true);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (position == 59) {
				try {
					String name = ja.getJSONObject(0).getString(
							"FamilyPlanning");
					// String name2 =
					// ja.getJSONObject(0).getString("AncCheckup4");
					System.out.print("name" + name);
					int value = Validate.returnIntegerValue(name);
					tvdata.setText(String.valueOf(value));
					if (value == 1) {
						radioaftersometime.setChecked(true);

					} else if (value == 0) {
						radionotkids.setChecked(true);

					} else if (value == 2) {
						radiodontknow.setChecked(true);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// Add viewpager_item.xml to ViewPager
			((ViewPager) container).addView(itemView);

			return itemView;

			// container.addView(img, LinearLayout.LayoutParams.MATCH_PARENT,
			// LinearLayout.LayoutParams.MATCH_PARENT);
			// return img;
		}

		public void ShowDatePicker(final TextView txt, final TextView datatv2) {
			datepic = new Dialog(context);
			Window window = datepic.getWindow();
			window.requestFeature(Window.FEATURE_NO_TITLE);
			datepic.setContentView(R.layout.datetimepicker);
			datepic.getWindow().setLayout(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);

			Button btnset = (Button) datepic.findViewById(R.id.set);
			Button btnCancle = (Button) datepic.findViewById(R.id.cancle);
			btnCancle.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated

					datepic.dismiss();

				}
			});
			final DatePicker datetext = (DatePicker) datepic
					.findViewById(R.id.idfordate);
			if (txt != null && txt.getText() != ""
					&& txt.getText().toString().length() > 0) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy",
						Locale.US);
				Date date;
				Calendar c = Calendar.getInstance(Locale.US);
				try {
					date = sdf.parse(txt.getText().toString());
					c.setTime(date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH);
				int day = c.get(Calendar.DAY_OF_MONTH);

				// set current date into datepicker
				datetext.init(year, month, day, null);
			}
			btnset.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					datetext.clearFocus();
					int Mdate = datetext.getDayOfMonth();

					int month = datetext.getMonth()+1;
					int year = datetext.getYear();
					String date = "" + Mdate;
					String mnth = "" + month;
					if (Mdate < 10) {
						date = "0" + Mdate;
					}
					if (month < 10) {
						mnth = "0" + month;
					}

					String sSellectedDate = date + "-" + mnth + "-"
							+ String.valueOf(year);
					txt.setText(sSellectedDate);
					datatv = Validate.changeDateFormat(sSellectedDate);
					datatv2.setText(sSellectedDate);

					// TODO Auto-generated

					datepic.dismiss();

				}
			});
			datepic.show();

		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		public void showhide(TableRow tbl, int iFlag) {

			if (iFlag == 1) {

				tbl.setVisibility(View.VISIBLE);
			} else {

				tbl.setVisibility(View.GONE);
			}
		}

		public void showdata() {
			AncVisit = dataProvider.getTbl_VisitANCData(
					global.getsGlobalPWGUID(), global.getsGlobalANCVisitGUID(),
					1);
			if (AncVisit != null && AncVisit.size() > 0) {

				if (AncVisit.get(0).getPregWomenReg() >= 0) {

					try {
						jo.put("PregWomenReg", AncVisit.get(0)
								.getPregWomenReg());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (AncVisit.get(0).getHomeVisitDate() != null
						&& AncVisit.get(0).getHomeVisitDate().length() > 0) {
					HomeVisitDate = AncVisit.get(0).getHomeVisitDate();
				} else {
					HomeVisitDate = Validate.getcurrentdate();
				}
				if (AncVisit.get(0).getMcpCard() >= 0) {
					try {
						jo.put("McpCard", AncVisit.get(0).getMcpCard());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (AncVisit.get(0).getTT1() >= 0) {
					try {
						jo.put("TT1", AncVisit.get(0).getTT1());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (AncVisit.get(0).getTT2() >= 0) {
					try {
						jo.put("TT2", AncVisit.get(0).getTT2());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (AncVisit.get(0).getTTbooster() >= 0) {
					try {
						jo.put("TTbooster", AncVisit.get(0).getTTbooster());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (AncVisit.get(0).getWeight1YN() >= 0) {
					try {
						jo.put("Weight1YN", AncVisit.get(0).getWeight1YN());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if (AncVisit.get(0).getBP1YN() >= 0) {
					try {
						jo.put("BP1YN", AncVisit.get(0).getBP1YN());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if (AncVisit.get(0).getHB1YN() >= 0) {
					try {
						jo.put("HB1YN", AncVisit.get(0).getHB1YN());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if (AncVisit.get(0).getUrineTestsugar1YN() >= 0) {
					try {
						jo.put("UrineTestsugar1YN", AncVisit.get(0)
								.getUrineTestsugar1YN());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if (AncVisit.get(0).getUrineTestAl1YN() >= 0) {
					try {
						jo.put("UrineTestAl1YN", AncVisit.get(0)
								.getUrineTestAl1YN());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if (AncVisit.get(0).getIronTablet1YN() >= 0) {
					try {
						jo.put("IronTablet1YN", AncVisit.get(0)
								.getIronTablet1YN());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if (AncVisit.get(0).getAncCheckup1YN() >= 0) {
					try {
						jo.put("AncCheckup1YN", AncVisit.get(0)
								.getAncCheckup1YN());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if (AncVisit.get(0).getWeight1() > 0) {
					try {
						jo.put("Weight1", AncVisit.get(0).getWeight1());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						jo.put("Weight1", "0");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if (AncVisit.get(0).getBP1() > 0) {
					try {
						jo.put("BP1", AncVisit.get(0).getBP1());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						jo.put("BP1", "0");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if (AncVisit.get(0).getHB1() > 0) {
					try {
						jo.put("HB1", AncVisit.get(0).getHB1());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						jo.put("HB1", "0");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if (AncVisit.get(0).getUrineTestsugar1() > 0) {
					try {
						jo.put("UrineTestsugar1", AncVisit.get(0)
								.getUrineTestsugar1());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						jo.put("UrineTestsugar1", "0");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if (AncVisit.get(0).getUrineTestAl1() > 0) {
					try {
						jo.put("UrineTestAl1", AncVisit.get(0)
								.getUrineTestAl1());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						jo.put("UrineTestAl1", "0");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if (AncVisit.get(0).getIronTablet1() > 0) {
					try {
						jo.put("IronTablet1", AncVisit.get(0).getIronTablet1());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						jo.put("IronTablet1", "0");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if (AncVisit.get(0).getTT1date() != null
						&& AncVisit.get(0).getTT1date().length() > 0) {
					try {
						jo.put("TT1date", AncVisit.get(0).getTT1date());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						jo.put("TT1date", "");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (AncVisit.get(0).getTT2date() != null
						&& AncVisit.get(0).getTT2date().length() > 0) {
					try {
						jo.put("TT2date", AncVisit.get(0).getTT2date());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						jo.put("TT2date", "");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (AncVisit.get(0).getTTboosterDate1() != null
						&& AncVisit.get(0).getTTboosterDate1().length() > 0) {
					try {
						jo.put("TTboosterDate1", AncVisit.get(0)
								.getTTboosterDate1());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						jo.put("TTboosterDate1", "");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (AncVisit.get(0).getAncCheckup1() != null
						&& AncVisit.get(0).getAncCheckup1().length() > 0) {
					try {
						jo.put("AncCheckup1", AncVisit.get(0).getAncCheckup1());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						jo.put("AncCheckup1", "");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if (AncVisit.get(0).getFamilyPlanning() > 0) {
					try {
						jo.put("FamilyPlanning", AncVisit.get(0)
								.getFamilyPlanning());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						jo.put("FamilyPlanning", "0");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (AncVisit.get(0).getDeliveryONhospYN() > 0) {
					try {
						jo.put("DeliveryONhospYN", AncVisit.get(0)
								.getDeliveryONhospYN());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						jo.put("DeliveryONhospYN", "0");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				ja.put(jo);
			}
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		Intent in = new Intent(homevisitanc2.this, HomeVisit.class);
		finish();
		startActivity(in);
	}

	public void mynextPage() {
		int isze = mViewPager.getChildCount();
		int currentPage = mViewPager.getCurrentItem();
		int totalPages = mViewPager.getAdapter().getCount();
		int nextPage = currentPage + 1;
		if (nextPage >= totalPages) {
			// We can't go forward anymore.
			// Loop to the first page. If you don't want looping just
			// return here.
			nextPage = totalPages;

		}
		TextView tvdata = null, tvdatatv = null;
		if (isze == 2) {
			ViewGroup view = (ViewGroup) mViewPager.getChildAt(0);
			tvdata = (TextView) view.findViewById(R.id.tvdata);
			tvdatatv = (TextView) view.findViewById(R.id.tvdatatv);
			Toast.makeText(this, tvdata.getText() + "," + tvdatatv.getText(),
					Toast.LENGTH_LONG).show();
		} else if (isze == 3) {
			ViewGroup view = (ViewGroup) mViewPager.getChildAt(1);

			tvdata = (TextView) view.findViewById(R.id.tvdata);
			tvdatatv = (TextView) view.findViewById(R.id.tvdatatv);
			Toast.makeText(this, tvdata.getText() + "," + tvdatatv.getText(),
					Toast.LENGTH_LONG).show();
		}
		savedata(currentPage, tvdata.getText().toString(), tvdatatv.getText()
				.toString());
		int tempdata = 0;
		if (tvdata.getText() != null
				&& tvdata.getText().toString().length() > 0) {
			tempdata = Validate.returnIntegerValue(tvdata.getText().toString());
		}
		if (currentPage == 0 && tempdata == 1) {
			mViewPager.setCurrentItem(3, true);
		} else if (currentPage == 59 && tempdata == 1) {
			mViewPager.setCurrentItem(78, true);
		} else {
			mViewPager.setCurrentItem(nextPage, true);
		}
	}

	@SuppressWarnings("unused")
	private void nextPage() {
		int currentPage = mViewPager.getCurrentItem();
		int totalPages = mViewPager.getAdapter().getCount();
		// savedata(currentPage);
		int nextPage = currentPage + 1;
		if (nextPage >= totalPages) {
			// We can't go forward anymore.
			// Loop to the first page. If you don't want looping just
			// return here.
			nextPage = totalPages;

		}
		if (ans == 1 && currentPage == 0) {
			mViewPager.setCurrentItem(3, true);
		} else if (ans == 1 && currentPage == 7) {
			mViewPager.setCurrentItem(30, true);
		} else if (ans == 1 && currentPage == 110) {
			mViewPager.setCurrentItem(128, true);
		} else {
			mViewPager.setCurrentItem(nextPage, true);
		}
	}

	public void savedata(int pos, String data, String datatv) {
		String sql = "";
		int newdata = 0;
		// =Validate.getcurrentdate();
		if (data.length() > 0) {
			newdata = Validate.returnIntegerValue(data);
		}
		float dataet = 0;
		if (pos == 0) {
			sql = "update tblANCVisit set PregWomenReg=" + newdata
					+ ",HomeVisitDate='" + HomeVisitDate
					+ "' where VisitGUID='" + global.getsGlobalANCVisitGUID()
					+ "' and PWGUID='" + global.getsGlobalPWGUID() + "'";
			System.out.println(sql);
			dataProvider.executeSql(sql);
		} else if (pos == 7) {
			sql = "update tblANCVisit set McpCard=" + newdata
					+ " where VisitGUID='" + global.getsGlobalANCVisitGUID()
					+ "'and PWGUID='" + global.getsGlobalPWGUID() + "'";
			dataProvider.executeSql(sql);
			System.out.println(sql);
		} else if (pos == 8) {

			sql = "update tblANCVisit set TT1=" + newdata + ",TT1date='"
					+ datatv + "' where VisitGUID='"
					+ global.getsGlobalANCVisitGUID() + "'and PWGUID='"
					+ global.getsGlobalPWGUID() + "'";
			System.out.println(sql);
			dataProvider.executeSql(sql);

		} else if (pos == 9) {
			sql = "update tblANCVisit set TT2=" + newdata + ",TT2date='"
					+ datatv + "' where VisitGUID='"
					+ global.getsGlobalANCVisitGUID() + "'and PWGUID='"
					+ global.getsGlobalPWGUID() + "'";
			System.out.println(sql);
			dataProvider.executeSql(sql);
		} else if (pos == 10) {
			sql = "update tblANCVisit set TTbooster=" + newdata
					+ ",TTboosterDate1='" + datatv + "' where VisitGUID='"
					+ global.getsGlobalANCVisitGUID() + "'and PWGUID='"
					+ global.getsGlobalPWGUID() + "'";
			dataProvider.executeSql(sql);
		} else if (pos == 11) {
			if (datatv.length() > 0) {
				dataet = Float.valueOf(datatv);
			}

			sql = "update tblANCVisit set Weight1YN=" + newdata + ",Weight1='"
					+ dataet + "' where VisitGUID='"
					+ global.getsGlobalANCVisitGUID() + "'and PWGUID='"
					+ global.getsGlobalPWGUID() + "'";
			System.out.println(sql);
			dataProvider.executeSql(sql);
		}
		// else if (pos == 12) {
		// sql = "update tblANCVisit set Weight2YN=" + data
		// + ",Weight2='"+dataet+"' where VisitGUID='" +
		// global.getsGlobalANCVisitGUID()
		// + "'and PWGUID='"+global.getsGlobalPWGUID()+"'";
		// dataProvider.executeSql(sql);
		// } else if (pos == 13) {
		// sql = "update tblANCVisit set Weight3YN=" + data
		// + ",Weight3='"+dataet+"' where VisitGUID='" +
		// global.getsGlobalANCVisitGUID()
		// + "'and PWGUID='"+global.getsGlobalPWGUID()+"'";
		// System.out.println(sql);
		// dataProvider.executeSql(sql);
		// } else if (pos == 14) {
		// sql = "update tblANCVisit set Weight4YN=" + data
		// + ",Weight4='"+dataet+"' where VisitGUID='" +
		// global.getsGlobalANCVisitGUID()
		// + "'and PWGUID='"+global.getsGlobalPWGUID()+"'";
		// System.out.println(sql);
		// dataProvider.executeSql(sql);
		// }
		else if (pos == 12) {
			if (datatv.length() > 0) {
				dataet = Float.valueOf(datatv);
			}
			sql = "update tblANCVisit set BP1YN=" + newdata + ",BP1='" + dataet
					+ "' where VisitGUID='" + global.getsGlobalANCVisitGUID()
					+ "'and PWGUID='" + global.getsGlobalPWGUID() + "'";
			dataProvider.executeSql(sql);
			// } else if (pos == 16) {
			// sql = "update tblANCVisit set BP2YN=" + data
			// + ",BP2='"+dataet+"' where VisitGUID='" +
			// global.getsGlobalANCVisitGUID()
			// + "'and PWGUID='"+global.getsGlobalPWGUID()+"'";
			// System.out.println(sql);
			// dataProvider.executeSql(sql);
			// } else if (pos == 17) {
			// sql = "update tblANCVisit set BP3YN=" + data
			// + ",BP3='"+dataet+"' where VisitGUID='" +
			// global.getsGlobalANCVisitGUID()
			// + "'and PWGUID='"+global.getsGlobalPWGUID()+"'";
			// System.out.println(sql);
			// dataProvider.executeSql(sql);
			// } else if (pos == 18) {
			// sql = "update tblANCVisit set BP4YN=" + data
			// + ",BP4='"+dataet+"' where VisitGUID='" +
			// global.getsGlobalANCVisitGUID()
			// + "'and PWGUID='"+global.getsGlobalPWGUID()+"'";
			// dataProvider.executeSql(sql);
		} else if (pos == 13) {
			if (datatv.length() > 0) {
				dataet = Float.valueOf(datatv);
			}
			sql = "update tblANCVisit set HB1YN=" + newdata + ",HB1='" + dataet
					+ "' where VisitGUID='" + global.getsGlobalANCVisitGUID()
					+ "'and PWGUID='" + global.getsGlobalPWGUID() + "'";
			System.out.println(sql);
			dataProvider.executeSql(sql);
			// } else if (pos == 20) {
			// sql = "update tblANCVisit set HB2YN=" + data
			// + ",HB2='"+dataet+"' where VisitGUID='" +
			// global.getsGlobalANCVisitGUID()
			// + "'and PWGUID='"+global.getsGlobalPWGUID()+"'";
			// dataProvider.executeSql(sql);
			// } else if (pos == 21) {
			// sql = "update tblANCVisit set HB3YN=" + data
			// + ",HB3='"+dataet+"' where VisitGUID='" +
			// global.getsGlobalANCVisitGUID()
			// + "'and PWGUID='"+global.getsGlobalPWGUID()+"'";
			// System.out.println(sql);
			// dataProvider.executeSql(sql);
			// } else if (pos == 22) {
			// sql = "update tblANCVisit set HB4YN=" + data
			// + ",HB4='"+dataet+"' where VisitGUID='" +
			// global.getsGlobalANCVisitGUID()
			// + "'and PWGUID='"+global.getsGlobalPWGUID()+"'";
			// dataProvider.executeSql(sql);
		} else if (pos == 14) {
			if (datatv.length() > 0) {
				dataet = Float.valueOf(datatv);
			}
			sql = "update tblANCVisit set UrineTestsugar1YN=" + newdata
					+ ", UrineTestsugar1='" + dataet + "' where VisitGUID='"
					+ global.getsGlobalANCVisitGUID() + "'and PWGUID='"
					+ global.getsGlobalPWGUID() + "'";
			System.out.println(sql);
			dataProvider.executeSql(sql);
			// } else if (pos == 24) {
			// sql = "update tblANCVisit set UrineTestsugar2YN=" + data
			// + ", UrineTestsugar2='"+dataet+"' where VisitGUID='" +
			// global.getsGlobalANCVisitGUID()
			// + "'and PWGUID='"+global.getsGlobalPWGUID()+"'";
			// System.out.println(sql);
			// dataProvider.executeSql(sql);
			// } else if (pos == 25) {
			// sql = "update tblANCVisit set UrineTestsugar3YN=" + data
			// + ", UrineTestsugar3='"+dataet+"' where VisitGUID='" +
			// global.getsGlobalANCVisitGUID()
			// + "'and PWGUID='"+global.getsGlobalPWGUID()+"'";
			// dataProvider.executeSql(sql);
			// } else if (pos == 26) {
			// sql = "update tblANCVisit set UrineTestsugar4YN=" + data
			// + " , UrineTestsugar4='"+dataet+"'where VisitGUID='" +
			// global.getsGlobalANCVisitGUID()
			// + "'and PWGUID='"+global.getsGlobalPWGUID()+"'";
			// System.out.println(sql);
			// dataProvider.executeSql(sql);
		} else if (pos == 15) {
			if (datatv.length() > 0) {
				dataet = Float.valueOf(datatv);
			}
			sql = "update tblANCVisit set UrineTestAl1YN=" + newdata
					+ ", UrineTestAl1='" + dataet + "' where VisitGUID='"
					+ global.getsGlobalANCVisitGUID() + "'and PWGUID='"
					+ global.getsGlobalPWGUID() + "'";
			System.out.println(sql);
			dataProvider.executeSql(sql);
			// } else if (pos == 28) {
			// sql = "update tblANCVisit set UrineTestAl2YN=" + data
			// + ", UrineTestAl2='"+dataet+"' where VisitGUID='" +
			// global.getsGlobalANCVisitGUID()
			// + "'and PWGUID='"+global.getsGlobalPWGUID()+"'";
			// System.out.println(sql);
			// dataProvider.executeSql(sql);
			// } else if (pos == 29) {
			// sql = "update tblANCVisit set UrineTestAl3YN=" + data
			// + ", UrineTestAl3='"+dataet+"' where VisitGUID='" +
			// global.getsGlobalANCVisitGUID()
			// + "'and PWGUID='"+global.getsGlobalPWGUID()+"'";
			// System.out.println(sql);
			// dataProvider.executeSql(sql);
			// }else if(pos==30){
			// sql = "update tblANCVisit set UrineTestAl4YN=" + data
			// + ", UrineTestAl4='"+dataet+"'where VisitGUID='" +
			// global.getsGlobalANCVisitGUID()
			// + "'and PWGUID='"+global.getsGlobalPWGUID()+"'";
			// dataProvider.executeSql(sql);
		} else if (pos == 16) {
			if (datatv.length() > 0) {
				dataet = Float.valueOf(datatv);
			}
			sql = "update tblANCVisit set IronTablet1YN=" + newdata
					+ ",IronTablet1='" + dataet + "' where VisitGUID='"
					+ global.getsGlobalANCVisitGUID() + "'and PWGUID='"
					+ global.getsGlobalPWGUID() + "'";
			System.out.println(sql);
			dataProvider.executeSql(sql);
			// }else if(pos==32){
			// sql = "update tblANCVisit set IronTablet2YN=" + data
			// + ",IronTablet2='"+dataet+"' where VisitGUID='" +
			// global.getsGlobalANCVisitGUID()
			// + "'and PWGUID='"+global.getsGlobalPWGUID()+"'";
			// System.out.println(sql);
			// dataProvider.executeSql(sql);
			// }else if(pos==33){
			// sql = "update tblANCVisit set IronTablet3YN=" + data
			// + ",IronTablet3='"+dataet+"' where VisitGUID='" +
			// global.getsGlobalANCVisitGUID()
			// + "'and PWGUID='"+global.getsGlobalPWGUID()+"'";
			// System.out.println(sql);
			// dataProvider.executeSql(sql);
			// }else if(pos==34){
			// sql = "update tblANCVisit set IronTablet4YN=" + data
			// + ",IronTablet4='"+dataet+"' where VisitGUID='" +
			// global.getsGlobalANCVisitGUID()
			// + "'and PWGUID='"+global.getsGlobalPWGUID()+"'";
			// dataProvider.executeSql(sql);
		} else if (pos == 17) {

			sql = "update tblANCVisit set AncCheckup1YN=" + newdata
					+ ",AncCheckup1='" + datatv + "' where VisitGUID='"
					+ global.getsGlobalANCVisitGUID() + "'and PWGUID='"
					+ global.getsGlobalPWGUID() + "'";
			System.out.println(sql);
			dataProvider.executeSql(sql);
		} else if (pos == 39) {
			sql = "update tblANCVisit set DeliveryONhospYN=" + newdata
					+ " where VisitGUID='" + global.getsGlobalANCVisitGUID()
					+ "'and PWGUID='" + global.getsGlobalPWGUID() + "'";
			System.out.println(sql);
			dataProvider.executeSql(sql);
		} else if (pos == 59) {
			sql = "update tblANCVisit set FamilyPlanning=" + newdata
					+ " where VisitGUID='" + global.getsGlobalANCVisitGUID()
					+ "'and PWGUID='" + global.getsGlobalPWGUID() + "'";
			System.out.println(sql);
			dataProvider.executeSql(sql);
		}

	}

	@SuppressWarnings("unused")
	private void previousPage() {
		int currentPage = mViewPager.getCurrentItem();
		int totalPages = mViewPager.getAdapter().getCount();

		int previousPage = currentPage - 1;
		if (previousPage < 0) {
			// We can't go back anymore.
			// Loop to the last page. If you don't want looping just
			// return here.
			previousPage = 0;
		}

		mViewPager.setCurrentItem(previousPage, true);
	}
}