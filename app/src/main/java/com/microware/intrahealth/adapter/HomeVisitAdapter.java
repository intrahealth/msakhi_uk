package com.microware.intrahealth.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.microware.intrahealth.AncQuestionActivity;
import com.microware.intrahealth.Global;
import com.microware.intrahealth.R;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.TblANCVisit;
import com.microware.intrahealth.object.tbl_pregnantwomen;

@SuppressLint({"SimpleDateFormat", "InflateParams"})
public class HomeVisitAdapter extends BaseAdapter {

    ArrayList<TblANCVisit> VisitANC;
    ArrayList<tbl_pregnantwomen> Pregnant;
    Context context;
    Global global;
    DataProvider dataProvider;


    public HomeVisitAdapter(Context context, ArrayList<TblANCVisit> VisitANC) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.VisitANC = VisitANC;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return VisitANC.size();
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

    @SuppressWarnings("unused")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // TODO Auto-generated method stub
        View gridview = null;
        try {

            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                gridview = new View(context);
                gridview = layoutInflater.inflate(
                        R.layout.homevisit_gridadapter, null);
            } else {
                gridview = convertView;
            }

            dataProvider = new DataProvider(context);
            global = (Global) context.getApplicationContext();
            TextView tvsno = (TextView) gridview.findViewById(R.id.tvsno);
            TextView tvlastvisit = (TextView) gridview
                    .findViewById(R.id.tvlastvisit);
            ImageView imageedit = (ImageView) gridview
                    .findViewById(R.id.imageedit);
            int ashaid = 0;
            if (global.getsGlobalAshaCode() != null
                    && global.getsGlobalAshaCode().length() > 0) {
                ashaid = Integer.valueOf(global.getsGlobalAshaCode());
            }

            Pregnant = dataProvider.getPregnantWomendata(VisitANC.get(position)
                    .getPWGUID(), 1, ashaid);
            String tvvisitdate = Pregnant.get(0).getLMPDate();
            int diffInDays = 0;
            SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date d = null;
            Date d1 = null;
            Calendar cal = Calendar.getInstance();
            if (tvvisitdate != null && tvvisitdate.length() > 0) {
                String[] Cdt = tvvisitdate.split("-");
                String DD = Cdt[0];
                String MM = Cdt[1];
                String YYYY = Cdt[2];
                if (MM.length() == 1) {
                    MM = "0" + Cdt[1];
                }
                tvvisitdate = DD + "-" + MM + "-" + YYYY;

            }
            if (tvvisitdate != null && tvvisitdate.length() > 0) {

                String tt1 = Validate.getcurrentdate();
                String lmp = tvvisitdate;

                try {
                    d = dfDate.parse(lmp);
                    d1 = dfDate.parse(tt1);
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }

                diffInDays = (int) ((d1.getTime() - d.getTime()) / (1000 * 60 * 60 * 24));
                System.out.println(diffInDays);
                if (position == 0) {
                    if (diffInDays >= 0 && diffInDays <= 105) {

                        imageedit.setEnabled(true);
                        if (VisitANC.get(position).getHomeVisitDate().length() > 0) {
                            tvsno.setBackgroundColor(Color.GREEN);
                            tvlastvisit.setBackgroundColor(Color.GREEN);
                        } else {
                            tvsno.setBackgroundColor(Color.BLUE);
                            tvlastvisit.setBackgroundColor(Color.BLUE);
                        }
                    } else {
                        if (VisitANC.get(position).getHomeVisitDate().length() > 0) {
                            tvsno.setBackgroundColor(Color.GREEN);
                            tvlastvisit.setBackgroundColor(Color.GREEN);
                        } else if(diffInDays > 105) {
                            tvsno.setBackgroundColor(context.getResources()
                                    .getColor(R.color.Orange));
                            tvlastvisit.setBackgroundColor(context
                                    .getResources().getColor(R.color.Orange));
                        }
                        imageedit.setEnabled(false);
                    }
                } else if (position == 1) {
                    if (diffInDays >= 91 && diffInDays <= 203) {

                        imageedit.setEnabled(true);
                        if (VisitANC.get(position).getHomeVisitDate().length() > 0) {
                            tvsno.setBackgroundColor(Color.GREEN);
                            tvlastvisit.setBackgroundColor(Color.GREEN);
                        } else {
                            tvsno.setBackgroundColor(Color.BLUE);
                            tvlastvisit.setBackgroundColor(Color.BLUE);
                        }
                    } else if(diffInDays > 203) {
                        imageedit.setEnabled(false);
                        if (VisitANC.get(position).getHomeVisitDate().length() > 0) {
                            tvsno.setBackgroundColor(Color.GREEN);
                            tvlastvisit.setBackgroundColor(Color.GREEN);
                        } else {
                            tvsno.setBackgroundColor(context.getResources()
                                    .getColor(R.color.Orange));
                            tvlastvisit.setBackgroundColor(context
                                    .getResources().getColor(R.color.Orange));
                        }
                    }

                } else if (position == 2) {
                    if (diffInDays >= 189 && diffInDays <= 259) {

                        imageedit.setEnabled(true);
                        if (VisitANC.get(position).getHomeVisitDate().length() > 0) {
                            tvsno.setBackgroundColor(Color.GREEN);
                            tvlastvisit.setBackgroundColor(Color.GREEN);
                        } else {
                            tvsno.setBackgroundColor(Color.BLUE);
                            tvlastvisit.setBackgroundColor(Color.BLUE);
                        }
                    } else {
                        imageedit.setEnabled(false);
                        if (VisitANC.get(position).getHomeVisitDate().length() > 0) {
                            tvsno.setBackgroundColor(Color.GREEN);
                            tvlastvisit.setBackgroundColor(Color.GREEN);
                        } else if(diffInDays > 259){
                            tvsno.setBackgroundColor(context.getResources()
                                    .getColor(R.color.Orange));
                            tvlastvisit.setBackgroundColor(context
                                    .getResources().getColor(R.color.Orange));
                        }
                    }

                } else if (position == 3) {
                    if (diffInDays >= 252) {

                        imageedit.setEnabled(true);
                        if (VisitANC.get(position).getHomeVisitDate().length() > 0) {
                            tvsno.setBackgroundColor(Color.GREEN);
                            tvlastvisit.setBackgroundColor(Color.GREEN);
                        } else {
                            tvsno.setBackgroundColor(Color.BLUE);
                            tvlastvisit.setBackgroundColor(Color.BLUE);
                        }
                    } else if(diffInDays > 252){
                        imageedit.setEnabled(false);
                        if (VisitANC.get(position).getHomeVisitDate().length() > 0) {
                            tvsno.setBackgroundColor(Color.GREEN);
                            tvlastvisit.setBackgroundColor(Color.GREEN);
                        } else {
                            tvsno.setBackgroundColor(context.getResources()
                                    .getColor(R.color.Orange));
                            tvlastvisit.setBackgroundColor(context
                                    .getResources().getColor(R.color.Orange));
                        }
                    }
                }

            }

            // ImageView imagedelete = (ImageView) gridview
            // .findViewById(R.id.imagedelete);
            if ((VisitANC.get(position).getVisit_No()) > 0) {

                // imagedelete.setEnabled(false);
            }

            tvsno.setText(context.getResources().getString(R.string.visit)
                    + "-"
                    + String.valueOf(VisitANC.get(position).getVisit_No()));
            tvlastvisit.setText(Validate.changeDateFormat(VisitANC.get(position).getHomeVisitDate()));
            if (global.getiGlobalRoleID() == 2) {
                imageedit.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        // global.setiCurrentActiveTab(0);
                        global.setVisitno(VisitANC.get(position).getVisit_No());
                        global.setsGlobalANCVisitGUID(VisitANC.get(position)
                                .getVisitGUID());
                        global.setsGlobalPWGUID(VisitANC.get(position)
                                .getPWGUID());
                        if (VisitANC.get(position).getVisit_No() == 1) {
                            Intent in = new Intent(context,
                                    AncQuestionActivity.class);

                            context.startActivity(in);
                        } else if (VisitANC.get(position).getVisit_No() == 2) {
                            Intent in = new Intent(context,
                                    AncQuestionActivity.class);
                            // ((AncActivity) context).finish();
                            context.startActivity(in);
                        } else if (VisitANC.get(position).getVisit_No() == 3) {
                            Intent in = new Intent(context,
                                    AncQuestionActivity.class);
                            // ((AncActivity) context).finish();
                            context.startActivity(in);
                        } else if (VisitANC.get(position).getVisit_No() == 4) {
                            Intent in = new Intent(context,
                                    AncQuestionActivity.class);
                            // ((AncActivity) context).finish();
                            context.startActivity(in);
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridview;

    }

}
