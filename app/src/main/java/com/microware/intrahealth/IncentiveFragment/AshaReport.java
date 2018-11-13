package com.microware.intrahealth.IncentiveFragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


import com.microware.intrahealth.Global;
import com.microware.intrahealth.IncentiveAdapter.AshaReport_Adapter;
import com.microware.intrahealth.IncentiveAdapter.Claim_Adapter;
import com.microware.intrahealth.R;
import com.microware.intrahealth.dataprovider.DataProvider;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kundanrawat on 17/11/17.
 */

public class AshaReport extends Fragment {
    DataProvider dataProvider;
    Global global;
    Spinner spin_year, spin_month;
    ArrayAdapter<String> adapter;
    RecyclerView ashareportrecycler_view, recycler_viewClaimed;
    LinearLayoutManager layoutManager;

    AshaReport_Adapter ashareport_adapter;
    Claim_Adapter claim_adapter;
    int Year = 0, Month = 0;

    public AshaReport() {
// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.ashareport, container, false);
        dataProvider = new DataProvider(getActivity());
        global = (Global) getActivity().getApplicationContext();
        spin_year = (Spinner) rootView.findViewById(R.id.spin_year);
        spin_month = (Spinner) rootView.findViewById(R.id.spin_month);
        ashareportrecycler_view = (RecyclerView) rootView.findViewById(R.id.ashareportrecycler_view);
        recycler_viewClaimed = (RecyclerView) rootView.findViewById(R.id.recycler_viewClaimed);
        fillspinner(spin_month, getResources().getStringArray(R.array.monthincentive));
        fillspinner(spin_year, getResources().getStringArray(R.array.globalyear));
       // fillRecycler(0);

        spin_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                // etSearch.setText("");
                if (pos > 0) {
                    Year = Integer.valueOf(spin_year.getSelectedItem().toString());
                    fillRecycler(1);
                    fillRecycler1(1);
                } else {
                    fillRecycler(0);
                    fillRecycler1(0);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spin_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                // etSearch.setText("");
                if (pos > 0 && spin_year.getSelectedItemPosition() > 0) {
                    Year = Integer.valueOf(spin_year.getSelectedItem().toString());
                    Month = spin_month.getSelectedItemPosition();
                    fillRecycler(2);
                    fillRecycler1(2);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return rootView;
    }



    public void fillspinner(Spinner spin, String[] ss) {

        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, ss);
        spin.setAdapter(adapter);
    }


    public void fillRecycler(int flag) {

        ArrayList<HashMap<String, String>> data = null;
        String sql = "";
        if (flag == 0) {
            sql = "select * from tblincentivesurvey where  AshaID='" + global.getsGlobalAshaCode() + "' order by Year DESC";
        } else if (flag == 1) {
            sql = "select * from tblincentivesurvey where  AshaID='" + global.getsGlobalAshaCode() + "' and Year=" + Year + " order by Year DESC";

        } else if (flag == 2) {
            sql = "select * from tblincentivesurvey where  AshaID='" + global.getsGlobalAshaCode() + "' and Year=" + Year + " and Month=" + Month + " order by Year DESC";

        }
        data = dataProvider.getDynamicVal(sql);
        if (data != null) {
            ashareport_adapter = new AshaReport_Adapter(getActivity(), data);
            ashareportrecycler_view.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getActivity());
            ashareportrecycler_view.setLayoutManager(layoutManager);
            ashareportrecycler_view.setAdapter(ashareport_adapter);
            ashareport_adapter.notifyDataSetChanged();
        }
    }

    public void fillRecycler1(int flag) {

        ArrayList<HashMap<String, String>> data = null;
        String sql = "";
        if (flag == 0) {
             sql = "select Year,sum(Claim) as Claim,sum(AmtRecieved) as AmtRecieved,sum(NotApproved) as NotApproved,ANMStatus,Year,Month,IncentiveSurveyGUID  from tblincentivesurvey where  AshaID='" + global.getsGlobalAshaCode() + "'  Group By Year order by Year DESC";
        }if (flag == 1) {
             sql = "select Year,sum(Claim) as Claim,sum(AmtRecieved) as AmtRecieved,sum(NotApproved) as NotApproved,ANMStatus,Year,Month,IncentiveSurveyGUID  from tblincentivesurvey where  AshaID='" + global.getsGlobalAshaCode() + "'  and Year=" + Year + "  Group By Year order by Year DESC";
        }if (flag == 2) {
             sql = "select Year,sum(Claim) as Claim,sum(AmtRecieved) as AmtRecieved,sum(NotApproved) as NotApproved,ANMStatus,Year,Month,IncentiveSurveyGUID from tblincentivesurvey where  AshaID='" + global.getsGlobalAshaCode() + "' and Year=" + Year + " and Month=" + Month + " Group By Year order by Year DESC";
        } data = dataProvider.getDynamicVal(sql);
        if (data != null ) {
            claim_adapter = new Claim_Adapter(getActivity(), data);
            recycler_viewClaimed.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getActivity());
            recycler_viewClaimed.setLayoutManager(layoutManager);
            recycler_viewClaimed.setAdapter(claim_adapter);
            claim_adapter.notifyDataSetChanged();
        }
    }

    public void CustomAlertSpinner(final Spinner spin, String msg) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(getActivity());
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_layout, null, false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        TextView txtTitle = (TextView) dialog
                .findViewById(R.id.txt_alert_message);
        txtTitle.setText(msg);

        Button btnok = (Button) dialog.findViewById(R.id.btn_ok);
        btnok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Dismiss the dialog
                dialog.dismiss();
                spin.performClick();
                spin.requestFocus();
            }
        });

        // Display the dialog
        dialog.show();

    }
}
