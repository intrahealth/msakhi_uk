package com.microware.intrahealth.adapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.R.color;
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;


import com.microware.intrahealth.Deathform;
import com.microware.intrahealth.FamilyDetails;
import com.microware.intrahealth.Global;
import com.microware.intrahealth.MigrationForm;
import com.microware.intrahealth.MigrationSelf;
import com.microware.intrahealth.R;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.fragment.Family_Fragment;
import com.microware.intrahealth.object.Tbl_HHFamilyMember;

@SuppressLint({ "InflateParams", "SimpleDateFormat" })
public class FamilyDetails_Adapter extends BaseAdapter {
	Context context;
	ArrayList<Tbl_HHFamilyMember> HHFamilyMember;
	Global global;
	DataProvider dataProvider;
	Dialog PicVideo_PreviewPopUp;
	String sFatherGUID = "";
	String SmotherGUID = "";
	String sSpouseGUID = "";
	Family_Fragment familyfragmnt;

	ArrayAdapter<String> adapter;
	ArrayList<Tbl_HHFamilyMember> HHFamilyMemberspin = new ArrayList<Tbl_HHFamilyMember>();
	ArrayList<Tbl_HHFamilyMember> HHFamilyMemberspinmother = new ArrayList<Tbl_HHFamilyMember>();

	public FamilyDetails_Adapter(Context context,
			ArrayList<Tbl_HHFamilyMember> HHFamilyMember,
			Family_Fragment familyfragmnt) {
		this.HHFamilyMember = HHFamilyMember;
		this.context = context;
		this.familyfragmnt = familyfragmnt;
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

	@SuppressWarnings({ "static-access" })
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View gridview = null;
		if (convertView == null) {
			LayoutInflater layoutInflater = (LayoutInflater) context
					.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			gridview = new View(context);
			gridview = layoutInflater.inflate(R.layout.familydetails_adapter,
					null);
		} else {
			gridview = convertView;
		}
		dataProvider = new DataProvider(context);
		global = (Global) context.getApplicationContext();
		TextView tvFMName = (TextView) gridview.findViewById(R.id.tvFMName);
		TextView tvFMGender = (TextView) gridview.findViewById(R.id.tvFMGender);
		TextView tvFMagefamily = (TextView) gridview
				.findViewById(R.id.tvFMagefamily);
		TextView tvFMRelation = (TextView) gridview
				.findViewById(R.id.tvFMRelation);
		ImageView btnEdit = (ImageView) gridview.findViewById(R.id.btnEdit);
		// ImageView btnDelete = (ImageView)
		// gridview.findViewById(R.id.btnDelete);
		TableRow Gridimage = (TableRow) gridview.findViewById(R.id.Gridimage);
		TableRow GridRow = (TableRow) gridview.findViewById(R.id.GridRow);

		tvFMName.setText(HHFamilyMember.get(position).getFamilyMemberName()
				.trim());

		final int igenderid;
		final int irelationid;
		final int imarragestatus;
		final String sHHFamilyMemberGUID;
		if (HHFamilyMember.get(position).getDOBAvailable() == 1) {
			if (HHFamilyMember.get(position).getDateOfBirth() != null
					&& HHFamilyMember.get(position).getDateOfBirth().length() > 0) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
						Locale.US);
				// Date date = new Date();
				String datenew = Validate.getcurrentdate();
				String newdate = HHFamilyMember.get(position).getDateOfBirth();
				String dob = "";
				if (newdate != null && newdate.length() > 0) {
					String[] Cdt = newdate.split("-");
					String DD = Cdt[0];
					String MM = Cdt[1];
					String YYYY = Cdt[2];
					if (MM.length() == 1) {
						MM = "0" + Cdt[1];
					}
					dob = DD + "-" + MM + "-" + YYYY;

				}
				Date d1 = null;
				Date d2 = null;

				try {
					d1 = dateFormat.parse(dob);
					d2 = dateFormat.parse(datenew);

					// in milliseconds
					long diff = d2.getTime() - d1.getTime();
					//
					// long diffSeconds = diff / 1000 % 60;
					// long diffMinutes = diff / (60 * 1000) % 60;
					// long diffHours = diff / (60 * 60 * 1000) % 24;
					long diffDays = diff / (24 * 60 * 60 * 1000);
					long diffyear = diffDays / 365;
					tvFMagefamily.setText(String.valueOf(diffyear));
				} catch (Exception e) {

				}
			}
		} else if (HHFamilyMember.get(position).getDOBAvailable() == 2) {
			int gap = Validate.CalculateYM(HHFamilyMember.get(position)
					.getAgeAsOnYear());
			int age = HHFamilyMember.get(position).getAprilAgeYear();
			int newage = gap + age;
			tvFMagefamily.setText(String.valueOf(newage));
		} else {
			tvFMagefamily.setText("");
		}
		imarragestatus = HHFamilyMember.get(position).getMaritialStatusID();

		igenderid = HHFamilyMember.get(position).getGenderID();

		irelationid = HHFamilyMember.get(position).getRelationID();
		if (igenderid == 1) {
			tvFMGender.setText(context.getResources().getString(R.string.male));
		} else if (igenderid == 2) {
			tvFMGender.setText(context.getResources()
					.getString(R.string.female));
		} else if (igenderid == 3) {
			tvFMGender
					.setText(context.getResources().getString(R.string.other));
		}

		if (imarragestatus == 1) {
			if (HHFamilyMember.get(position).getSpouse() != null
					&& HHFamilyMember.get(position).getSpouse().length() > 0) {
				tvFMName.setTextColor(Color.BLACK);
			} else {
				tvFMName.setTextColor(Color.RED);
			}
		}
		if (HHFamilyMember.get(position).getStatusID() == 2) {
			btnEdit.setEnabled(false);
		}
		if (irelationid == 9 || irelationid == 10 || irelationid == 11
				|| irelationid == 12 || irelationid == 17 || irelationid == 18
				|| irelationid == 23 || irelationid == 24) {

			if (HHFamilyMember.get(position).getMother() != null
					&& HHFamilyMember.get(position).getMother().length() > 0
					&& (HHFamilyMember.get(position).getFather() != null && HHFamilyMember
							.get(position).getFather().length() > 0)) {
				tvFMName.setTextColor(Color.BLACK);
			} else if (HHFamilyMember.get(position).getSpouse() != null
					&& HHFamilyMember.get(position).getSpouse().length() > 0) {
				tvFMName.setTextColor(Color.BLACK);
			} else {
				tvFMName.setTextColor(context.getResources().getColor(
						R.color.OrangeMustard));
			}
		}

		System.out.println(imarragestatus);
		String sql = "Select Value from MstCommon where Flag=6 and LanguageID= "
				+ global.getLanguage()
				+ " and ID="
				+ HHFamilyMember.get(position).getRelationID() + "";
		String relation = "";
		relation = dataProvider.getRecord(sql);
		if (relation == null) {
			relation = "";
		}
		tvFMRelation.setText(relation);
		String status = "Select StatusID from Tbl_HHFamilyMember where HHFamilyMemberGUID='"
				+ HHFamilyMember.get(position).getHHFamilyMemberGUID() + "'";
		String stat = dataProvider.getRecord(status);

		sHHFamilyMemberGUID = HHFamilyMember.get(position)
				.getHHFamilyMemberGUID();
		int StatusId = Integer.valueOf(stat);
		if (StatusId == 3) {
			tvFMName.setBackgroundResource(R.drawable.yellowsheet);
			tvFMRelation.setBackgroundResource(R.drawable.yellowsheet);
			Gridimage.setBackgroundResource(R.drawable.yellowsheet);
		} else if (StatusId == 2) {
			tvFMName.setBackgroundResource(R.drawable.orangesheet);
			tvFMRelation.setBackgroundResource(R.drawable.orangesheet);

			Gridimage.setBackgroundResource(R.drawable.orangesheet);
		} else {

		}

		btnEdit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (global.getiGlobalRoleID() != 3) {
					global.setGlobalHHFamilyMemberGUID(HHFamilyMember.get(
							position).getHHFamilyMemberGUID());

					showServicesPopup(HHFamilyMember.get(position)
							.getStatusID());
				}

			}
		});

		tvFMName.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (global.getiGlobalRoleID() != 3) {
					if ((HHFamilyMember.get(position).getFather().length() > 0 && HHFamilyMember
							.get(position).getFather() != null)
							|| (HHFamilyMember.get(position).getMother()
									.length() > 0 && HHFamilyMember.get(
									position).getMother() != null)
							|| (HHFamilyMember.get(position).getSpouse()
									.length() > 0 && HHFamilyMember.get(
									position).getSpouse() != null)) {

						String sfather = "";
						String sMother = "";
						String sSpouse = "";
						sfather = HHFamilyMember.get(position).getFather();
						sMother = HHFamilyMember.get(position).getMother();
						sSpouse = HHFamilyMember.get(position).getSpouse();
						showdataspouse(sHHFamilyMemberGUID, sfather, sMother,
								sSpouse);

					} else {
						if (imarragestatus == 1) {
							showRelationPopUp(igenderid, imarragestatus,
									sHHFamilyMemberGUID,
									HHFamilyMember.get(position)
											.getHHSurveyGUID());
						} else if (irelationid == 9 || irelationid == 10
								|| irelationid == 11 || irelationid == 12
								|| irelationid == 17 || irelationid == 18
								|| irelationid == 23 || irelationid == 24) {
							showRelationChildrenPopUp(sHHFamilyMemberGUID,
									HHFamilyMember.get(position)
											.getHHSurveyGUID());
						}
					}

				}
			}
		});
		return gridview;
	}

	public void showRelationPopUp(final int igenderid,
			final int imarragestatus, final String sHHFamilyMemberGUID,
			final String hhguid) {
		PicVideo_PreviewPopUp = new Dialog(context);
		Window window = PicVideo_PreviewPopUp.getWindow();
		PicVideo_PreviewPopUp.getWindow().setLayout(600, 400);
		// PicVideo_PreviewPopUp.getWindow().setBackgroundDrawable(
		// new ColorDrawable(Color.TRANSPARENT));
		PicVideo_PreviewPopUp.getWindow().setBackgroundDrawableResource(
				color.white);

		window.requestFeature(Window.FEATURE_NO_TITLE);

		PicVideo_PreviewPopUp.setContentView(R.layout.map_relation_popup);
		PicVideo_PreviewPopUp.setCancelable(true);
		PicVideo_PreviewPopUp.setCanceledOnTouchOutside(true);
		PicVideo_PreviewPopUp.show();

		Button btnsave = (Button) PicVideo_PreviewPopUp
				.findViewById(R.id.btnSave);

		final TextView tvspousename = (TextView) PicVideo_PreviewPopUp
				.findViewById(R.id.tvspousename);

		final Spinner spinspouse = (Spinner) PicVideo_PreviewPopUp
				.findViewById(R.id.spinrelation);
		if (igenderid == 1 && imarragestatus == 1) {
			tvspousename.setText(context.getResources().getString(
					R.string.sspousename2));
			fillCommonRecord(spinspouse, 1, sHHFamilyMemberGUID);
		} else if (igenderid == 2 && imarragestatus == 1) {
			tvspousename.setText(context.getResources().getString(
					R.string.sspousename));
			fillCommonRecord(spinspouse, 2, sHHFamilyMemberGUID);
		}

		btnsave.setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (sSpouseGUID.length() > 0) {

					int ireturnvalue = dataProvider.savespousedata(
							sHHFamilyMemberGUID, sSpouseGUID);
					String tblpreg = "select tblPregnant_woman from where HHFamilyMemberGUID='"
							+ sHHFamilyMemberGUID + "' ";
					int result = dataProvider.getMaxRecord(tblpreg);
					if (result > 0) {
						String newquery = "update tblPregnant_woman set Husband_GUID='"
								+ sSpouseGUID
								+ "'where HHFamilyMemberGUID='"
								+ sHHFamilyMemberGUID + "'";
						dataProvider.executeSql(newquery);
					}
					if (ireturnvalue == 1) {
						String query = "update Tbl_HHSurvey set IsEdited=1 where HHSurveyGUID='"
								+ hhguid + "'";
						dataProvider.executeSql(query);
						AlertDialog alertDialog = new AlertDialog.Builder(
								context).create();
						alertDialog.setTitle(context.getResources().getString(
								R.string.mSakhi));
						alertDialog.setMessage(context.getResources()
								.getString(R.string.savesuccessfully));
						alertDialog.setButton(
								context.getResources().getString(R.string.ok),
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										familyfragmnt.fillGrid();
										PicVideo_PreviewPopUp.dismiss();

									}
								});

						alertDialog.show();
					}

				} else {

					AlertDialog alertDialog = new AlertDialog.Builder(context)
							.create();
					alertDialog.setTitle(context.getResources().getString(
							R.string.mSakhi));
					alertDialog.setMessage(context.getResources().getString(
							R.string.SelectSpousename));
					alertDialog.setButton(
							context.getResources().getString(R.string.ok),
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									familyfragmnt.fillGrid();
									PicVideo_PreviewPopUp.dismiss();

								}
							});

					alertDialog.show();

				}
			}
		});

	}

	public void showServicesPopup(final int StatusID) {
		PicVideo_PreviewPopUp = new Dialog(context);
		Window window = PicVideo_PreviewPopUp.getWindow();
		PicVideo_PreviewPopUp.getWindow().setLayout(600, 400);
		// PicVideo_PreviewPopUp.getWindow().setBackgroundDrawable(
		// new ColorDrawable(Color.TRANSPARENT));
		PicVideo_PreviewPopUp.getWindow().setBackgroundDrawableResource(
				color.white);

		window.requestFeature(Window.FEATURE_NO_TITLE);

		PicVideo_PreviewPopUp.setContentView(R.layout.services);
		PicVideo_PreviewPopUp.setCancelable(true);
		PicVideo_PreviewPopUp.setCanceledOnTouchOutside(true);
		PicVideo_PreviewPopUp.show();
		ImageView btneditdata = (ImageView) PicVideo_PreviewPopUp
				.findViewById(R.id.btneditdata);
		ImageView btnmigrated = (ImageView) PicVideo_PreviewPopUp
				.findViewById(R.id.btnmigrated);
		TextView username = (TextView) PicVideo_PreviewPopUp
				.findViewById(R.id.username);
		ImageView btnDead = (ImageView) PicVideo_PreviewPopUp
				.findViewById(R.id.btnDead);
		String sql = "select FamilyMemberName from Tbl_HHFamilyMember where HHFamilyMemberGUID='"
				+ global.getGlobalHHFamilyMemberGUID() + "'";
		String name = dataProvider.getRecord(sql);
		username.setText(name);
		btneditdata.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(v.getContext(), FamilyDetails.class);
				context.startActivity(i);
			}
		});
		btnmigrated.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (StatusID == 3) {
					String sql = "select MigrationGUID from tblMigration where DateOfMigrationIn='' and HHFamilyMemberGUID='"
							+ global.getGlobalHHFamilyMemberGUID() + "'";
					String guid = dataProvider.getRecord(sql);
					global.setMigrationGUID(guid);
					CustomAlertSave(
							context.getResources()
									.getString(R.string.migratein), 1, 0);
				} else {
					global.setMigrationGUID("");

					CustomAlertSave(
							context.getResources().getString(
									R.string.migrateout), 2, -1);
				}

			}
		});
		btnDead.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String sql = "select RelationID from Tbl_HHFamilyMember where HHFamilyMemberGUID='"
						+ global.getGlobalHHFamilyMemberGUID() + "'";
				String RelationID = dataProvider.getRecord(sql);
				int id = 0;
				if (!RelationID.equalsIgnoreCase("null")) {
					id = Integer.valueOf(RelationID);
				}
				CustomAlertSave(
						context.getResources().getString(R.string.Deadmsg), 3,
						id);
			}
		});
	}

	public void CustomAlertSave(String msg, final int id, final int relid) {
		// Create custom dialog object
		final Dialog dialog = new Dialog(context);
		// hide to default title for Dialog
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// inflate the layout dialog_layout.xml and set it as contentView
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.dialog_checklayout, null, false);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setContentView(view);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
		TextView txtTitle = (TextView) dialog
				.findViewById(R.id.txt_alert_message);
		txtTitle.setText(msg);

		Button btnyes = (Button) dialog.findViewById(R.id.btn_yes);
		Button btnno = (Button) dialog.findViewById(R.id.btn_no);

		btnyes.setOnClickListener(new android.view.View.OnClickListener() {

			public void onClick(View v) {

				if (id == 1) {
					Intent i = new Intent(v.getContext(), MigrationForm.class);
					context.startActivity(i);
				} else if (id == 2) {

					Intent i = new Intent(v.getContext(), MigrationForm.class);
					context.startActivity(i);

				} else if (id == 3) {
					if (relid == 1) {
						Intent i = new Intent(v.getContext(),
								MigrationSelf.class);
						context.startActivity(i);
					} else {
						Intent i = new Intent(v.getContext(), Deathform.class);
						context.startActivity(i);
					}
				}

				dialog.dismiss();

			}
		});

		btnno.setOnClickListener(new android.view.View.OnClickListener() {

			public void onClick(View v) {

				dialog.dismiss();

			}
		});

		// Display the dialog
		dialog.show();

	}

	private void fillCommonRecord(Spinner spin, int iflag,
			String sHHFamilyMemberGUID) {

		HHFamilyMemberspin = dataProvider.showSpinnerDataFamiily(iflag,
				sHHFamilyMemberGUID, global.getGlobalHHSurveyGUID());

		String sValue[] = new String[HHFamilyMemberspin.size() + 1];
		sValue[0] = context.getResources().getString(R.string.select);
		for (int i = 0; i < HHFamilyMemberspin.size(); i++) {
			sValue[i + 1] = HHFamilyMemberspin.get(i).getFamilyMemberName();
		}
		adapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_dropdown_item, sValue);
		spin.setAdapter(adapter);
		spin.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				if (pos > 0) {
					sSpouseGUID = HHFamilyMemberspin.get(pos - 1)
							.getHHFamilyMemberGUID();
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}

		});

	}

	public void showRelationChildrenPopUp(
			final String showRelationChildrenPopUp, final String hhguid) {
		PicVideo_PreviewPopUp = new Dialog(context);
		Window window = PicVideo_PreviewPopUp.getWindow();
		PicVideo_PreviewPopUp.getWindow().setLayout(600, 400);
		// PicVideo_PreviewPopUp.getWindow().setBackgroundDrawable(
		// new ColorDrawable(Color.TRANSPARENT));
		PicVideo_PreviewPopUp.getWindow().setBackgroundDrawableResource(
				color.white);

		window.requestFeature(Window.FEATURE_NO_TITLE);

		PicVideo_PreviewPopUp.setContentView(R.layout.popup_father_mother);
		PicVideo_PreviewPopUp.setCancelable(true);
		PicVideo_PreviewPopUp.setCanceledOnTouchOutside(true);
		PicVideo_PreviewPopUp.show();

		Button btnsave = (Button) PicVideo_PreviewPopUp
				.findViewById(R.id.btnSave);

		Spinner spinmother = (Spinner) PicVideo_PreviewPopUp
				.findViewById(R.id.spinMotherrelation);

		Spinner spinfather = (Spinner) PicVideo_PreviewPopUp
				.findViewById(R.id.spinFatherrelation);

		fillMotherRecord(spinmother);
		fillFatherRecord(spinfather);

		btnsave.setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (sFatherGUID.length() > 0 || SmotherGUID.length() > 0) {

					int ireturnvalue = dataProvider
							.savefathermotherdata(showRelationChildrenPopUp,
									sFatherGUID, SmotherGUID);

					if (ireturnvalue == 1) {
						String query = "update Tbl_HHSurvey set IsEdited=1 where HHSurveyGUID='"
								+ hhguid + "'";
						dataProvider.executeSql(query);
						AlertDialog alertDialog = new AlertDialog.Builder(
								context).create();
						alertDialog.setTitle(context.getResources().getString(
								R.string.mSakhi));
						alertDialog.setMessage(context.getResources()
								.getString(R.string.savesuccessfully));
						alertDialog.setButton(
								context.getResources().getString(R.string.ok),
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										familyfragmnt.fillGrid();
										PicVideo_PreviewPopUp.dismiss();
									}
								});

						alertDialog.show();

					}

				} else {

					AlertDialog alertDialog = new AlertDialog.Builder(context)
							.create();
					alertDialog.setTitle(context.getResources().getString(
							R.string.mSakhi));
					alertDialog.setMessage(context.getResources().getString(
							R.string.Selectparents));
					alertDialog.setButton(
							context.getResources().getString(R.string.ok),
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									familyfragmnt.fillGrid();
									PicVideo_PreviewPopUp.dismiss();

								}
							});

					alertDialog.show();

				}
			}
		});

	}

	private void fillFatherRecord(Spinner spin) {

		HHFamilyMemberspin = dataProvider.showSpinnerfatherDataFamiily(global
				.getGlobalHHSurveyGUID());

		String sValue[] = new String[HHFamilyMemberspin.size() + 1];
		sValue[0] = context.getResources().getString(R.string.select);
		for (int i = 0; i < HHFamilyMemberspin.size(); i++) {
			sValue[i + 1] = HHFamilyMemberspin.get(i).getFamilyMemberName();
		}
		adapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_dropdown_item, sValue);
		spin.setAdapter(adapter);

		spin.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				if (pos > 0) {
					sFatherGUID = HHFamilyMemberspin.get(pos - 1)
							.getHHFamilyMemberGUID();
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}

		});

	}

	private void fillMotherRecord(Spinner spin) {

		HHFamilyMemberspinmother = dataProvider
				.showSpinnerMotherDataFamiily(global.getGlobalHHSurveyGUID());

		String sValue[] = new String[HHFamilyMemberspinmother.size() + 1];
		sValue[0] = context.getResources().getString(R.string.select);
		for (int i = 0; i < HHFamilyMemberspinmother.size(); i++) {
			sValue[i + 1] = HHFamilyMemberspinmother.get(i)
					.getFamilyMemberName();
		}
		adapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_dropdown_item, sValue);
		spin.setAdapter(adapter);
		spin.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				if (pos > 0) {
					SmotherGUID = HHFamilyMemberspinmother.get(pos - 1)
							.getHHFamilyMemberGUID();
				}

				// ifacilitytypeid = facilitytype.get(pos).getFacilityTypeID();
				// fillfacilitynameSPimer(ifacilitytypeid,iblockid);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}

		});

	}

	@SuppressWarnings("unused")
	public void showdataspouse(final String sHHFamilyMemberGUID,
			final String father, final String mother, final String spouse) {

		PicVideo_PreviewPopUp = new Dialog(context);
		Window window = PicVideo_PreviewPopUp.getWindow();
		PicVideo_PreviewPopUp.getWindow().setLayout(600, 400);
		// PicVideo_PreviewPopUp.getWindow().setBackgroundDrawable(
		// new ColorDrawable(Color.TRANSPARENT));
		PicVideo_PreviewPopUp.getWindow().setBackgroundDrawableResource(
				color.white);

		window.requestFeature(Window.FEATURE_NO_TITLE);

		PicVideo_PreviewPopUp
				.setContentView(R.layout.showdata_spouse_father_mother);
		PicVideo_PreviewPopUp.setCancelable(true);
		PicVideo_PreviewPopUp.setCanceledOnTouchOutside(true);
		PicVideo_PreviewPopUp.show();

		final Button btnClose = (Button) PicVideo_PreviewPopUp
				.findViewById(R.id.btnClose);
		final Button btnSave = (Button) PicVideo_PreviewPopUp
				.findViewById(R.id.btnSave);
		final TextView txtspouse = (TextView) PicVideo_PreviewPopUp
				.findViewById(R.id.txtSpouse);
		final TextView spouse1 = (TextView) PicVideo_PreviewPopUp
				.findViewById(R.id.spouse);
		final TextView txtmother = (TextView) PicVideo_PreviewPopUp
				.findViewById(R.id.txtMothername);
		ImageView btnEdit = (ImageView) PicVideo_PreviewPopUp
				.findViewById(R.id.btnEdit);
		ImageView btnEdit2 = (ImageView) PicVideo_PreviewPopUp
				.findViewById(R.id.btnEdit2);
		ImageView btnEdit3 = (ImageView) PicVideo_PreviewPopUp
				.findViewById(R.id.btnEdit3);
		final TextView txtfather = (TextView) PicVideo_PreviewPopUp
				.findViewById(R.id.txtFathername);
		TableRow tblspouse = (TableRow) PicVideo_PreviewPopUp
				.findViewById(R.id.tblspouse);
		TableRow tblmother = (TableRow) PicVideo_PreviewPopUp
				.findViewById(R.id.tblmother);
		TableRow tblfather = (TableRow) PicVideo_PreviewPopUp
				.findViewById(R.id.tblfather);
		final Spinner Spinspouse = (Spinner) PicVideo_PreviewPopUp
				.findViewById(R.id.spinspouse);
		final Spinner SpinMother = (Spinner) PicVideo_PreviewPopUp
				.findViewById(R.id.spinmother);
		final Spinner spinFather = (Spinner) PicVideo_PreviewPopUp
				.findViewById(R.id.spinFather);

		if (father.length() > 0) {
			String status = "Select FamilyMemberName from Tbl_HHFamilyMember where HHFamilyMemberGUID='"
					+ father + "'";
			String stat = dataProvider.getRecord(status);
			if (stat != null && !stat.equalsIgnoreCase("")
					&& !stat.equalsIgnoreCase("null")) {

				txtfather.setText(stat.toString());
				tblfather.setVisibility(View.VISIBLE);
				tblmother.setVisibility(View.VISIBLE);
				tblspouse.setVisibility(View.GONE);
			} else {
				tblfather.setVisibility(View.VISIBLE);
				tblmother.setVisibility(View.VISIBLE);
				txtfather.setVisibility(View.GONE);
				spinFather.setVisibility(View.VISIBLE);
				fillFatherRecord(spinFather);
				btnClose.setVisibility(View.GONE);
				btnSave.setVisibility(View.VISIBLE);

			}
		}

		if (mother.length() > 0) {
			String status = "Select FamilyMemberName from Tbl_HHFamilyMember where HHFamilyMemberGUID='"
					+ mother + "'";
			String stat = dataProvider.getRecord(status);
			if (stat != null && !stat.equalsIgnoreCase("")
					&& !stat.equalsIgnoreCase("null")) {
				txtmother.setText(stat.toString());

				tblfather.setVisibility(View.VISIBLE);
				tblmother.setVisibility(View.VISIBLE);
				tblspouse.setVisibility(View.GONE);
			} else {
				tblfather.setVisibility(View.VISIBLE);
				tblmother.setVisibility(View.VISIBLE);
				txtmother.setVisibility(View.GONE);
				SpinMother.setVisibility(View.VISIBLE);
				fillMotherRecord(SpinMother);
				btnClose.setVisibility(View.GONE);
				btnSave.setVisibility(View.VISIBLE);
			}
		}

		if (spouse.length() > 0) {
			String status = "Select FamilyMemberName from Tbl_HHFamilyMember where HHFamilyMemberGUID='"
					+ spouse + "'";
			String stat = dataProvider.getRecord(status);
			txtspouse.setText(stat.toString());
			String sql = "Select GenderID from Tbl_HHFamilyMember where HHFamilyMemberGUID='"
					+ spouse + "'";
			String gender = dataProvider.getRecord(sql);
			int genderid = Integer.valueOf(gender);
			if (genderid == 1) {
				spouse1.setText(context.getResources().getString(
						R.string.sspousename));
			} else {
				spouse1.setText(context.getResources().getString(
						R.string.sspousename2));
			}
			tblfather.setVisibility(View.GONE);
			tblmother.setVisibility(View.GONE);
			tblspouse.setVisibility(View.VISIBLE);
		}
		btnEdit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (spouse1
						.getText()
						.toString()
						.equalsIgnoreCase(
								context.getResources().getString(
										R.string.sspousename))) {
					txtspouse.setVisibility(View.GONE);

					Spinspouse.setVisibility(View.VISIBLE);
					btnClose.setVisibility(View.GONE);
					btnSave.setVisibility(View.VISIBLE);
					fillCommonRecordhusband(Spinspouse, 2);
				} else if (spouse1
						.getText()
						.toString()
						.equalsIgnoreCase(
								context.getResources().getString(
										R.string.sspousename2))) {
					txtspouse.setVisibility(View.GONE);
					Spinspouse.setVisibility(View.VISIBLE);
					btnClose.setVisibility(View.GONE);
					btnSave.setVisibility(View.VISIBLE);
					fillCommonRecordhusband(Spinspouse, 1);
				}
			}
		});
		btnEdit2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				txtmother.setVisibility(View.GONE);
				SpinMother.setVisibility(View.VISIBLE);
				fillMotherRecord(SpinMother);
				btnClose.setVisibility(View.GONE);
				btnSave.setVisibility(View.VISIBLE);
			}
		});
		btnEdit3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				txtfather.setVisibility(View.GONE);
				spinFather.setVisibility(View.VISIBLE);
				fillFatherRecord(spinFather);
				btnClose.setVisibility(View.GONE);
				btnSave.setVisibility(View.VISIBLE);

			}
		});
		btnSave.setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int ireturnvalue = 0;
				if (Spinspouse.getSelectedItemPosition() > 0) {
					if (HHFamilyMemberspin
							.get(Spinspouse.getSelectedItemPosition() - 1)
							.getHHFamilyMemberGUID().length() > 0) {
						String sql = "update Tbl_HHFamilyMember set Spouse='',IsEdited=1 where HHFamilyMemberGUID='"
								+ spouse + "'";
						dataProvider.executeSql(sql);
						String sqlfm = "update Tbl_HHFamilyMember set Mother='',Father='',IsEdited=1 where Mother='"
								+ spouse + "' OR Father='" + spouse + "'";
						dataProvider.executeSql(sqlfm);
						ireturnvalue = dataProvider.savespousedata(
								sHHFamilyMemberGUID,
								HHFamilyMemberspin
										.get(Spinspouse
												.getSelectedItemPosition() - 1)
										.getHHFamilyMemberGUID());
						String GUId = HHFamilyMemberspin.get(
								Spinspouse.getSelectedItemPosition() - 1)
								.getHHFamilyMemberGUID();
						String tblpreg = "select tblPregnant_woman from where HHFamilyMemberGUID='"
								+ sHHFamilyMemberGUID + "' ";
						int result = dataProvider.getMaxRecord(tblpreg);
						String tblpreg2 = "select tblPregnant_woman from where HHFamilyMemberGUID='"
								+ GUId + "' ";
						int result2 = dataProvider.getMaxRecord(tblpreg);
						if (result > 0) {
							String newquery = "update tblPregnant_woman set Husband_GUID='"
									+ GUId
									+ "' ,IsEdited=1 where HHFamilyMemberGUID='"
									+ sHHFamilyMemberGUID + "'";
							dataProvider.executeSql(newquery);
						} else if (result2 > 0) {
							String newquery = "update tblPregnant_woman set Husband_GUID='"
									+ sHHFamilyMemberGUID
									+ "' ,IsEdited=1 where HHFamilyMemberGUID='"
									+ GUId
									+ "'";
							dataProvider.executeSql(newquery);
						}
						System.out.println("value" + ireturnvalue);
						if (ireturnvalue == 1) {
							PicVideo_PreviewPopUp.dismiss();
							familyfragmnt.fillGrid();

						}
					}
				}

				else if (father.length() > 0 || mother.length() > 0) {
					String FatherGUID = father;
					String MotherGUID = mother;
					if (txtfather.getVisibility() == View.VISIBLE) {
						FatherGUID = father;
					} else if (spinFather.getSelectedItemPosition() > 0) {
						FatherGUID = HHFamilyMemberspin.get(
								spinFather.getSelectedItemPosition() - 1)
								.getHHFamilyMemberGUID();
					}
					if (txtmother.getVisibility() == View.VISIBLE) {
						MotherGUID = mother;
					} else if (SpinMother.getSelectedItemPosition() > 0) {

						MotherGUID = HHFamilyMemberspinmother.get(
								SpinMother.getSelectedItemPosition() - 1)
								.getHHFamilyMemberGUID();
					}
					ireturnvalue = dataProvider.savefathermotherdata(
							sHHFamilyMemberGUID, FatherGUID, MotherGUID);
					if (ireturnvalue == 1) {

						AlertDialog alertDialog = new AlertDialog.Builder(
								context).create();
						alertDialog.setTitle(context.getResources().getString(
								R.string.mSakhi));
						alertDialog.setMessage(context.getResources()
								.getString(R.string.savesuccessfully));
						alertDialog.setButton(
								context.getResources().getString(R.string.ok),
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										familyfragmnt.fillGrid();
										PicVideo_PreviewPopUp.dismiss();
									}
								});

						alertDialog.show();

					}

				} else {

					AlertDialog alertDialog = new AlertDialog.Builder(context)
							.create();
					alertDialog.setTitle(context.getResources().getString(
							R.string.mSakhi));
					alertDialog.setMessage(context.getResources().getString(
							R.string.SelectOne));
					alertDialog.setButton(
							context.getResources().getString(R.string.ok),
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									familyfragmnt.fillGrid();
									PicVideo_PreviewPopUp.dismiss();

								}
							});

					alertDialog.show();

				}
			}
		});
		btnClose.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PicVideo_PreviewPopUp.dismiss();

			}
		});

	}

	private void fillCommonRecordhusband(Spinner spin, int flag) {

		HHFamilyMemberspin = dataProvider.showSpinnerDataFamiily(flag, "",
				global.getGlobalHHSurveyGUID());

		String sValue[] = new String[HHFamilyMemberspin.size() + 1];
		sValue[0] = context.getResources().getString(R.string.select);
		for (int i = 0; i < HHFamilyMemberspin.size(); i++) {
			sValue[i + 1] = HHFamilyMemberspin.get(i).getFamilyMemberName();
		}
		adapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_dropdown_item, sValue);
		spin.setAdapter(adapter);
		spin.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				// if (pos > 0) {
				// sSpouseGUID = HHFamilyMemberspin.get(pos - 1)
				// .getHHFamilyMemberGUID();
				// }

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}

		});

	}

}
