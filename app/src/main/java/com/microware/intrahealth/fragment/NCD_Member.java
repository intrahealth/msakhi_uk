package com.microware.intrahealth.fragment;

import java.util.ArrayList;

import com.microware.intrahealth.Global;
import com.microware.intrahealth.R;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.adapter.FP_ExistAdapter;
import com.microware.intrahealth.adapter.NCD_ExistAdapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstVillage;
import com.microware.intrahealth.object.Tbl_HHFamilyMember;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

public class NCD_Member extends Fragment {

    Button btn_search;
    EditText et_Search;
    GridView HHSurveyGrid;
    DataProvider dataProvider;
    Global global;
    ArrayList<Tbl_HHFamilyMember> member = new ArrayList<Tbl_HHFamilyMember>();
    TextView totalcount, tv_stat;
    Spinner spinVillageName;
    ArrayList<MstVillage> Village = new ArrayList<MstVillage>();
    int VillageID = 0;
    ArrayAdapter<String> adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.ncd_existmember, container,
                false);

        dataProvider = new DataProvider(getActivity());
        global = (Global) getActivity().getApplication();

        btn_search = (Button) rootView.findViewById(R.id.btn_search);
        // btnadd = (ImageView) rootView.findViewById(R.id.btnadd);
        et_Search = (EditText) rootView.findViewById(R.id.et_Search);
        HHSurveyGrid = (GridView) rootView.findViewById(R.id.HHSurveyGrid);
        totalcount = (TextView) rootView.findViewById(R.id.totalcount);
        tv_stat = (TextView) rootView.findViewById(R.id.tv_stat);
        spinVillageName = (Spinner) rootView.findViewById(R.id.spinVillageName);
        if (global.getiGlobalRoleID() == 2) {
            tv_stat.setText(getResources().getString(R.string.ncdcbac));
        }
        et_Search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
                if (s.length() > 0
                        && !Validate
                        .isTextValid(et_Search.getText().toString())) {
                    fillgrid(1);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (s.length() == 0) {
                    fillgrid(2);
                }
            }
        });
        btn_search.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (et_Search.getText().toString().length() > 0) {
                    fillgrid(2);
                }
            }
        });
        fillVillageName(global.getLanguage());
        spinVillageName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (global.getVHND_flag() == 1) {
                } else {
                    if (pos > 0) {
                        VillageID = Village.get(
                                spinVillageName.getSelectedItemPosition() - 1)
                                .getVillageID();
                        fillgrid(0);

                    } else {
                        VillageID = 0;
                        fillgrid(2);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fillgrid(2);

        return rootView;
    }

    private void fillVillageName(int Language) {
        Village = dataProvider.getMstVillageName(global.getsGlobalAshaCode(), Language, 0);

        String sValue[] = new String[Village.size() + 1];
        sValue[0] = getResources().getString(R.string.select);

        for (int i = 0; i < Village.size(); i++) {
            sValue[i + 1] = Village.get(i).getVillageName();
        }
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, sValue);
        spinVillageName.setAdapter(adapter);
    }

    public void fillgrid(int flag) {
        int ashaid = 0;
        if (global.getsGlobalAshaCode() != null
                && global.getsGlobalAshaCode().length() > 0) {
            ashaid = Integer.valueOf(global.getsGlobalAshaCode());
        }
        if (flag == 0) {

            member = dataProvider.getAllMember("", 1, ashaid, VillageID);
        } else if (flag == 1) {
            member = dataProvider.getAllMember(et_Search.getText().toString(),
                    2, ashaid, VillageID);
        }else if (flag == 2) {
            member = dataProvider.getAllMember("", 3, ashaid, 0);
        }

        if (member != null) {
            totalcount.setText(getResources().getString(
                    R.string.totalcountfamilymember)
                    + " -" + String.valueOf(member.size()));

            // android.view.ViewGroup.LayoutParams params = HHSurveyGrid
            // .getLayoutParams();
            // Resources r = getResources();
            // float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            // 300, r.getDisplayMetrics());
            // int hi = Math.round(px);
            // int gridHeight1 = 0;
            // gridHeight1 = hi * member.size();
            // params.height = gridHeight1;
            // HHSurveyGrid.setLayoutParams(params);
            HHSurveyGrid.setAdapter(new NCD_ExistAdapter(getActivity(), member,
                    1));
        }
    }

}
