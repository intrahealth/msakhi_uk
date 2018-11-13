package com.microware.intrahealth.IncentiveFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import com.microware.intrahealth.Global;
import com.microware.intrahealth.R;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.dataprovider.DataProvider;

import java.util.ArrayList;
import java.util.HashMap;

public class Incentive_BB extends Fragment {


    DataProvider dataProvider;
    ArrayAdapter<String> adapter;
    Global global;
    Spinner spin_year, spin_month;
    EditText et_Ans1, et_Ans2, et_Ans3, et_Ans4, et_Ans5, et_Ans6, et_Ans7, et_Ans8, et_Ans9, et_Ans10, et_Ans11, et_Ans12, et_Ans13, et_Ans14, et_Ans15, et_Ans16, et_Ans17, et_Ans18, et_Ans19, et_Ans20, et_Ans21, et_Ans22, et_Ans23, et_Ans24, et_Ans25, et_Ans26, et_Ans27, et_Ans28, et_Ans29, et_Ans30, et_Ans31, et_Ans32, et_Ans33, et_Ans34, et_Ans35, et_Ans36, et_Ans37, et_Ans38, et_Ans39, et_Ans40, et_Ans41, et_Ans42, et_Ans43, et_Ans44, et_Ans45, et_Ans46, et_Ans47, et_Ans48, et_Ans49, et_Ans50, et_Ans51, et_Ans52, et_Ans53, et_Ans54, et_Ans55, et_Ans56, et_Ans57, et_Ans58, et_Ans59, et_Ans60, et_Ans61, et_Ans62, et_Ans63, et_Ans64, et_Ans65, et_Ans66, et_Ans67, et_Ans68, et_Ans69, et_Ans70, et_Ans71, et_Ans72, et_Ans73;
    Button btnSave;
    TextView tv_Anstot1, tv_Anstot2, tv_Anstot3, tv_Anstot4, tv_Anstot5, tv_Anstot6, tv_Anstot7,
            tv_ANsscore1, tv_ANsscore2, tv_ANsscore3, tv_ANsscore4, tv_ANsscore5, tv_ANsscore6, tv_ANsscore7, tv_ANsscore8,
            tv_ANsscore9, tv_ANsscore10, tv_ANsscore11, tv_ANsscore12, tv_ANsscore13, tv_ANsscore14, tv_ANsscore15, tv_ANsscore16, tv_ANsscore17, tv_ANsscore18, tv_ANsscore19, tv_ANsscore20, tv_ANsscore21, tv_ANsscore22, tv_ANsscore23, tv_ANsscore24, tv_ANsscore25, tv_ANsscore26, tv_ANsscore27, tv_ANsscore28, tv_ANsscore29, tv_ANsscore30, tv_ANsscore31, tv_ANsscore32, tv_ANsscore33, tv_ANsscore34, tv_ANsscore35, tv_ANsscore36, tv_ANsscore37, tv_ANsscore38, tv_ANsscore39, tv_ANsscore40, tv_ANsscore41, tv_ANsscore42, tv_ANsscore43, tv_ANsscore44, tv_ANsscore45, tv_ANsscore46, tv_ANsscore47, tv_ANsscore48, tv_ANsscore49, tv_ANsscore50, tv_ANsscore51, tv_ANsscore52, tv_ANsscore53, tv_ANsscore54, tv_ANsscore55, tv_ANsscore56, tv_ANsscore57, tv_ANsscore58, tv_ANsscore59, tv_ANsscore60, tv_ANsscore61, tv_ANsscore62, tv_ANsscore63, tv_ANsscore64, tv_ANsscore65, tv_ANsscore66, tv_ANsscore67, tv_ANsscore68, tv_ANsscore69, tv_ANsscore70, tv_ANsscore71, tv_ANsscore72, tv_ANsscore73, tv_Anstot8, tv_Anstot9, tv_Anstot10, tv_Anstot11, tv_Anstot12, tv_Anstot13, tv_Anstot14, tv_Anstot15, tv_Anstot16, tv_Anstot17, tv_Anstot18, tv_Anstot19, tv_Anstot20, tv_Anstot21, tv_Anstot22, tv_Anstot23, tv_Anstot24, tv_Anstot25, tv_Anstot26, tv_Anstot27, tv_Anstot28, tv_Anstot29, tv_Anstot30, tv_Anstot31, tv_Anstot32, tv_Anstot33, tv_Anstot34, tv_Anstot35, tv_Anstot36, tv_Anstot37, tv_Anstot38, tv_Anstot39, tv_Anstot40, tv_Anstot41, tv_Anstot42, tv_Anstot43, tv_Anstot44, tv_Anstot45, tv_Anstot46, tv_Anstot47, tv_Anstot48, tv_Anstot49, tv_Anstot50, tv_Anstot51, tv_Anstot52, tv_Anstot53, tv_Anstot54, tv_Anstot55, tv_Anstot56, tv_Anstot57, tv_Anstot58, tv_Anstot59, tv_Anstot60, tv_Anstot61, tv_Anstot62, tv_Anstot63, tv_Anstot64, tv_Anstot65, tv_Anstot66, tv_Anstot67, tv_Anstot68, tv_Anstot69, tv_Anstot70, tv_Anstot71, tv_Anstot72, tv_Anstot73;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View rootView = inflater.inflate(R.layout.incentive, container, false);
        global = (Global) getActivity().getApplication();
        dataProvider = new DataProvider(getActivity());
        spin_year = (Spinner) rootView.findViewById(R.id.spin_year);
        spin_month = (Spinner) rootView.findViewById(R.id.spin_month);
        fillspinner(spin_month, getResources().getStringArray(R.array.monthincentive));
        fillspinner(spin_year, getResources().getStringArray(R.array.globalyear));
        idfind(rootView);

        et_Ans1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    addtotal(et_Ans1, tv_Anstot1, tv_ANsscore1);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String search = s.toString();
                if (search.length() == 0) {
                    tv_ANsscore1.setText("");
                }
            }
        });

        et_Ans2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    addtotal(et_Ans2, tv_Anstot2, tv_ANsscore2);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String search = s.toString();
                if (search.length() == 0) {
                    tv_ANsscore2.setText("");
                }
            }
        });

        et_Ans3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    addtotal(et_Ans3, tv_Anstot3, tv_ANsscore3);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String search = s.toString();
                if (search.length() == 0) {
                    tv_ANsscore3.setText("");
                }
            }
        });

        et_Ans4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    addtotal(et_Ans4, tv_Anstot4, tv_ANsscore4);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String search = s.toString();
                if (search.length() == 0) {
                    tv_ANsscore4.setText("");
                }
            }
        });

        et_Ans5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    addtotal(et_Ans5, tv_Anstot5, tv_ANsscore5);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String search = s.toString();
                if (search.length() == 0) {
                    tv_ANsscore5.setText("");
                }
            }
        });

        et_Ans6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    addtotal(et_Ans6, tv_Anstot6, tv_ANsscore6);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String search = s.toString();
                if (search.length() == 0) {
                    tv_ANsscore6.setText("");
                }
            }
        });

        et_Ans7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    addtotal(et_Ans7, tv_Anstot7, tv_ANsscore7);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String search = s.toString();
                if (search.length() == 0) {
                    tv_ANsscore7.setText("");
                }
            }
        });
        et_Ans8.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    addtotal(et_Ans8, tv_Anstot8, tv_ANsscore8);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String search = s.toString();
                if (search.length() == 0) {
                    tv_ANsscore8.setText("");
                }
            }
        });
        et_Ans9.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    addtotal(et_Ans9, tv_Anstot9, tv_ANsscore9);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String search = s.toString();
                if (search.length() == 0) {
                    tv_ANsscore9.setText("");
                }
            }
        });
        et_Ans10.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    addtotal(et_Ans10, tv_Anstot10, tv_ANsscore10);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String search = s.toString();
                if (search.length() == 0) {
                    tv_ANsscore10.setText("");
                }
            }
        });
        edittextlistner(et_Ans11, tv_Anstot11, tv_ANsscore11);
        edittextlistner(et_Ans12, tv_Anstot12, tv_ANsscore12);
        edittextlistner(et_Ans13, tv_Anstot13, tv_ANsscore13);
        edittextlistner(et_Ans14, tv_Anstot14, tv_ANsscore14);
        edittextlistner(et_Ans15, tv_Anstot15, tv_ANsscore15);
        edittextlistner(et_Ans16, tv_Anstot16, tv_ANsscore16);
        edittextlistner(et_Ans17, tv_Anstot17, tv_ANsscore17);
        edittextlistner(et_Ans18, tv_Anstot18, tv_ANsscore18);
        edittextlistner(et_Ans19, tv_Anstot19, tv_ANsscore19);
        edittextlistner(et_Ans20, tv_Anstot20, tv_ANsscore20);
        edittextlistner(et_Ans21, tv_Anstot21, tv_ANsscore21);
        edittextlistner(et_Ans22, tv_Anstot22, tv_ANsscore22);
        edittextlistner(et_Ans23, tv_Anstot23, tv_ANsscore23);
        edittextlistner(et_Ans24, tv_Anstot24, tv_ANsscore24);
        edittextlistner(et_Ans25, tv_Anstot25, tv_ANsscore25);
        edittextlistner(et_Ans26, tv_Anstot26, tv_ANsscore26);
        edittextlistner(et_Ans27, tv_Anstot27, tv_ANsscore27);
        edittextlistner(et_Ans28, tv_Anstot28, tv_ANsscore28);
        edittextlistner(et_Ans29, tv_Anstot29, tv_ANsscore29);
        edittextlistner(et_Ans30, tv_Anstot30, tv_ANsscore30);
        edittextlistner(et_Ans31, tv_Anstot31, tv_ANsscore31);
        edittextlistner(et_Ans32, tv_Anstot32, tv_ANsscore32);
        edittextlistner(et_Ans33, tv_Anstot33, tv_ANsscore33);
        edittextlistner(et_Ans34, tv_Anstot34, tv_ANsscore34);
        edittextlistner(et_Ans35, tv_Anstot35, tv_ANsscore35);
        edittextlistner(et_Ans36, tv_Anstot36, tv_ANsscore36);
        edittextlistner(et_Ans37, tv_Anstot37, tv_ANsscore37);
        edittextlistner(et_Ans38, tv_Anstot38, tv_ANsscore38);
        edittextlistner(et_Ans39, tv_Anstot39, tv_ANsscore39);
        edittextlistner(et_Ans40, tv_Anstot40, tv_ANsscore40);
        edittextlistner(et_Ans41, tv_Anstot41, tv_ANsscore41);
        edittextlistner(et_Ans42, tv_Anstot42, tv_ANsscore42);
        edittextlistner(et_Ans43, tv_Anstot43, tv_ANsscore43);
        edittextlistner(et_Ans44, tv_Anstot44, tv_ANsscore44);
        edittextlistner(et_Ans45, tv_Anstot45, tv_ANsscore45);
        edittextlistner(et_Ans46, tv_Anstot46, tv_ANsscore46);
        edittextlistner(et_Ans47, tv_Anstot47, tv_ANsscore47);
        edittextlistner(et_Ans48, tv_Anstot48, tv_ANsscore48);
        edittextlistner(et_Ans49, tv_Anstot49, tv_ANsscore49);
        edittextlistner(et_Ans50, tv_Anstot50, tv_ANsscore50);
        edittextlistner(et_Ans51, tv_Anstot51, tv_ANsscore51);
        edittextlistner(et_Ans52, tv_Anstot52, tv_ANsscore52);
        edittextlistner(et_Ans53, tv_Anstot53, tv_ANsscore53);
        edittextlistner(et_Ans54, tv_Anstot54, tv_ANsscore54);
        edittextlistner(et_Ans55, tv_Anstot55, tv_ANsscore55);
        edittextlistner(et_Ans56, tv_Anstot56, tv_ANsscore56);
        edittextlistner(et_Ans57, tv_Anstot57, tv_ANsscore57);
        edittextlistner(et_Ans58, tv_Anstot58, tv_ANsscore58);
        edittextlistner(et_Ans59, tv_Anstot59, tv_ANsscore59);
        edittextlistner(et_Ans60, tv_Anstot60, tv_ANsscore60);
        edittextlistner(et_Ans61, tv_Anstot61, tv_ANsscore61);
        edittextlistner(et_Ans62, tv_Anstot62, tv_ANsscore62);
        edittextlistner(et_Ans63, tv_Anstot63, tv_ANsscore63);
        edittextlistner(et_Ans64, tv_Anstot64, tv_ANsscore64);
        edittextlistner(et_Ans65, tv_Anstot65, tv_ANsscore65);
        edittextlistner(et_Ans66, tv_Anstot66, tv_ANsscore66);
        edittextlistner(et_Ans67, tv_Anstot67, tv_ANsscore67);
        edittextlistner(et_Ans68, tv_Anstot68, tv_ANsscore68);
        edittextlistner(et_Ans69, tv_Anstot69, tv_ANsscore69);
        edittextlistner(et_Ans70, tv_Anstot70, tv_ANsscore70);
        edittextlistner(et_Ans71, tv_Anstot71, tv_ANsscore71);
        edittextlistner(et_Ans72, tv_Anstot72, tv_ANsscore72);
        edittextlistner(et_Ans73, tv_Anstot73, tv_ANsscore73);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int icheck = 0;

                icheck = sCheckValidation();
                if (icheck == 1) {

                    saveIncentive(rootView);

                } else {
                    showAlert(icheck);
                }
            }
        });
        setyaer(getResources().getStringArray(R.array.globalyear));

        showQues(rootView);
        return rootView;
    }

    public void edittextlistner(final EditText edit, final TextView txt1, final TextView txt2) {
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    addtotal(edit, txt1, txt2);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String search = s.toString();
                if (search.length() == 0) {
                    txt2.setText("");
                }
            }
        });
    }

    public void idfind(View rootView) {
        tv_Anstot1 = (TextView) rootView.findViewById(R.id.tv_Anstot1);
        tv_Anstot2 = (TextView) rootView.findViewById(R.id.tv_Anstot2);
        tv_Anstot3 = (TextView) rootView.findViewById(R.id.tv_Anstot3);
        tv_Anstot4 = (TextView) rootView.findViewById(R.id.tv_Anstot4);
        tv_Anstot5 = (TextView) rootView.findViewById(R.id.tv_Anstot5);
        tv_Anstot6 = (TextView) rootView.findViewById(R.id.tv_Anstot6);
        tv_Anstot7 = (TextView) rootView.findViewById(R.id.tv_Anstot7);
        tv_Anstot8 = (TextView) rootView.findViewById(R.id.tv_Anstot8);
        tv_Anstot9 = (TextView) rootView.findViewById(R.id.tv_Anstot9);
        tv_Anstot10 = (TextView) rootView.findViewById(R.id.tv_Anstot10);
        tv_Anstot11 = (TextView) rootView.findViewById(R.id.tv_Anstot11);
        tv_Anstot12 = (TextView) rootView.findViewById(R.id.tv_Anstot12);
        tv_Anstot13 = (TextView) rootView.findViewById(R.id.tv_Anstot13);
        tv_Anstot14 = (TextView) rootView.findViewById(R.id.tv_Anstot14);
        tv_Anstot15 = (TextView) rootView.findViewById(R.id.tv_Anstot15);
        tv_Anstot16 = (TextView) rootView.findViewById(R.id.tv_Anstot16);
        tv_Anstot17 = (TextView) rootView.findViewById(R.id.tv_Anstot17);
        tv_Anstot18 = (TextView) rootView.findViewById(R.id.tv_Anstot18);
        tv_Anstot19 = (TextView) rootView.findViewById(R.id.tv_Anstot19);
        tv_Anstot20 = (TextView) rootView.findViewById(R.id.tv_Anstot20);
        tv_Anstot21 = (TextView) rootView.findViewById(R.id.tv_Anstot21);
        tv_Anstot22 = (TextView) rootView.findViewById(R.id.tv_Anstot22);
        tv_Anstot23 = (TextView) rootView.findViewById(R.id.tv_Anstot23);
        tv_Anstot24 = (TextView) rootView.findViewById(R.id.tv_Anstot24);
        tv_Anstot25 = (TextView) rootView.findViewById(R.id.tv_Anstot25);
        tv_Anstot26 = (TextView) rootView.findViewById(R.id.tv_Anstot26);
        tv_Anstot27 = (TextView) rootView.findViewById(R.id.tv_Anstot27);
        tv_Anstot28 = (TextView) rootView.findViewById(R.id.tv_Anstot28);
        tv_Anstot29 = (TextView) rootView.findViewById(R.id.tv_Anstot29);
        tv_Anstot30 = (TextView) rootView.findViewById(R.id.tv_Anstot30);
        tv_Anstot31 = (TextView) rootView.findViewById(R.id.tv_Anstot31);
        tv_Anstot32 = (TextView) rootView.findViewById(R.id.tv_Anstot32);
        tv_Anstot33 = (TextView) rootView.findViewById(R.id.tv_Anstot33);
        tv_Anstot34 = (TextView) rootView.findViewById(R.id.tv_Anstot34);
        tv_Anstot35 = (TextView) rootView.findViewById(R.id.tv_Anstot35);
        tv_Anstot36 = (TextView) rootView.findViewById(R.id.tv_Anstot36);
        tv_Anstot37 = (TextView) rootView.findViewById(R.id.tv_Anstot37);
        tv_Anstot38 = (TextView) rootView.findViewById(R.id.tv_Anstot38);
        tv_Anstot39 = (TextView) rootView.findViewById(R.id.tv_Anstot39);
        tv_Anstot40 = (TextView) rootView.findViewById(R.id.tv_Anstot40);
        tv_Anstot41 = (TextView) rootView.findViewById(R.id.tv_Anstot41);
        tv_Anstot42 = (TextView) rootView.findViewById(R.id.tv_Anstot42);
        tv_Anstot43 = (TextView) rootView.findViewById(R.id.tv_Anstot43);
        tv_Anstot44 = (TextView) rootView.findViewById(R.id.tv_Anstot44);
        tv_Anstot45 = (TextView) rootView.findViewById(R.id.tv_Anstot45);
        tv_Anstot46 = (TextView) rootView.findViewById(R.id.tv_Anstot46);
        tv_Anstot47 = (TextView) rootView.findViewById(R.id.tv_Anstot47);
        tv_Anstot48 = (TextView) rootView.findViewById(R.id.tv_Anstot48);
        tv_Anstot49 = (TextView) rootView.findViewById(R.id.tv_Anstot49);
        tv_Anstot50 = (TextView) rootView.findViewById(R.id.tv_Anstot50);
        tv_Anstot51 = (TextView) rootView.findViewById(R.id.tv_Anstot51);
        tv_Anstot52 = (TextView) rootView.findViewById(R.id.tv_Anstot52);
        tv_Anstot53 = (TextView) rootView.findViewById(R.id.tv_Anstot53);
        tv_Anstot54 = (TextView) rootView.findViewById(R.id.tv_Anstot54);
        tv_Anstot55 = (TextView) rootView.findViewById(R.id.tv_Anstot55);
        tv_Anstot56 = (TextView) rootView.findViewById(R.id.tv_Anstot56);
        tv_Anstot57 = (TextView) rootView.findViewById(R.id.tv_Anstot57);
        tv_Anstot58 = (TextView) rootView.findViewById(R.id.tv_Anstot58);
        tv_Anstot59 = (TextView) rootView.findViewById(R.id.tv_Anstot59);
        tv_Anstot60 = (TextView) rootView.findViewById(R.id.tv_Anstot60);

        tv_Anstot61 = (TextView) rootView.findViewById(R.id.tv_Anstot61);
        tv_Anstot62 = (TextView) rootView.findViewById(R.id.tv_Anstot62);
        tv_Anstot63 = (TextView) rootView.findViewById(R.id.tv_Anstot63);
        tv_Anstot64 = (TextView) rootView.findViewById(R.id.tv_Anstot64);
        tv_Anstot65 = (TextView) rootView.findViewById(R.id.tv_Anstot65);
        tv_Anstot66 = (TextView) rootView.findViewById(R.id.tv_Anstot66);
        tv_Anstot67 = (TextView) rootView.findViewById(R.id.tv_Anstot67);
        tv_Anstot68 = (TextView) rootView.findViewById(R.id.tv_Anstot68);
        tv_Anstot69 = (TextView) rootView.findViewById(R.id.tv_Anstot69);
        tv_Anstot70 = (TextView) rootView.findViewById(R.id.tv_Anstot70);

        tv_Anstot71 = (TextView) rootView.findViewById(R.id.tv_Anstot71);
        tv_Anstot72 = (TextView) rootView.findViewById(R.id.tv_Anstot72);
        tv_Anstot73 = (TextView) rootView.findViewById(R.id.tv_Anstot73);
        tv_ANsscore1 = (TextView) rootView.findViewById(R.id.tv_ANsscore1);
        tv_ANsscore2 = (TextView) rootView.findViewById(R.id.tv_ANsscore2);
        tv_ANsscore3 = (TextView) rootView.findViewById(R.id.tv_ANsscore3);
        tv_ANsscore4 = (TextView) rootView.findViewById(R.id.tv_ANsscore4);
        tv_ANsscore5 = (TextView) rootView.findViewById(R.id.tv_ANsscore5);
        tv_ANsscore6 = (TextView) rootView.findViewById(R.id.tv_ANsscore6);
        tv_ANsscore7 = (TextView) rootView.findViewById(R.id.tv_ANsscore7);
        tv_ANsscore8 = (TextView) rootView.findViewById(R.id.tv_ANsscore8);
        tv_ANsscore9 = (TextView) rootView.findViewById(R.id.tv_ANsscore9);
        tv_ANsscore10 = (TextView) rootView.findViewById(R.id.tv_ANsscore10);
        tv_ANsscore11 = (TextView) rootView.findViewById(R.id.tv_ANsscore11);
        tv_ANsscore12 = (TextView) rootView.findViewById(R.id.tv_ANsscore12);
        tv_ANsscore13 = (TextView) rootView.findViewById(R.id.tv_ANsscore13);
        tv_ANsscore14 = (TextView) rootView.findViewById(R.id.tv_ANsscore14);
        tv_ANsscore15 = (TextView) rootView.findViewById(R.id.tv_ANsscore15);
        tv_ANsscore16 = (TextView) rootView.findViewById(R.id.tv_ANsscore16);
        tv_ANsscore17 = (TextView) rootView.findViewById(R.id.tv_ANsscore17);
        tv_ANsscore18 = (TextView) rootView.findViewById(R.id.tv_ANsscore18);
        tv_ANsscore19 = (TextView) rootView.findViewById(R.id.tv_ANsscore19);
        tv_ANsscore20 = (TextView) rootView.findViewById(R.id.tv_ANsscore20);
        tv_ANsscore21 = (TextView) rootView.findViewById(R.id.tv_ANsscore21);
        tv_ANsscore22 = (TextView) rootView.findViewById(R.id.tv_ANsscore22);
        tv_ANsscore23 = (TextView) rootView.findViewById(R.id.tv_ANsscore23);
        tv_ANsscore24 = (TextView) rootView.findViewById(R.id.tv_ANsscore24);
        tv_ANsscore25 = (TextView) rootView.findViewById(R.id.tv_ANsscore25);
        tv_ANsscore26 = (TextView) rootView.findViewById(R.id.tv_ANsscore26);
        tv_ANsscore27 = (TextView) rootView.findViewById(R.id.tv_ANsscore27);
        tv_ANsscore28 = (TextView) rootView.findViewById(R.id.tv_ANsscore28);
        tv_ANsscore29 = (TextView) rootView.findViewById(R.id.tv_ANsscore29);
        tv_ANsscore30 = (TextView) rootView.findViewById(R.id.tv_ANsscore30);
        tv_ANsscore31 = (TextView) rootView.findViewById(R.id.tv_ANsscore31);
        tv_ANsscore32 = (TextView) rootView.findViewById(R.id.tv_ANsscore32);
        tv_ANsscore33 = (TextView) rootView.findViewById(R.id.tv_ANsscore33);
        tv_ANsscore34 = (TextView) rootView.findViewById(R.id.tv_ANsscore34);
        tv_ANsscore35 = (TextView) rootView.findViewById(R.id.tv_ANsscore35);
        tv_ANsscore36 = (TextView) rootView.findViewById(R.id.tv_ANsscore36);
        tv_ANsscore37 = (TextView) rootView.findViewById(R.id.tv_ANsscore37);
        tv_ANsscore38 = (TextView) rootView.findViewById(R.id.tv_ANsscore38);
        tv_ANsscore39 = (TextView) rootView.findViewById(R.id.tv_ANsscore39);
        tv_ANsscore40 = (TextView) rootView.findViewById(R.id.tv_ANsscore40);
        tv_ANsscore41 = (TextView) rootView.findViewById(R.id.tv_ANsscore41);
        tv_ANsscore42 = (TextView) rootView.findViewById(R.id.tv_ANsscore42);
        tv_ANsscore43 = (TextView) rootView.findViewById(R.id.tv_ANsscore43);
        tv_ANsscore44 = (TextView) rootView.findViewById(R.id.tv_ANsscore44);
        tv_ANsscore45 = (TextView) rootView.findViewById(R.id.tv_ANsscore45);
        tv_ANsscore46 = (TextView) rootView.findViewById(R.id.tv_ANsscore46);
        tv_ANsscore47 = (TextView) rootView.findViewById(R.id.tv_ANsscore47);
        tv_ANsscore48 = (TextView) rootView.findViewById(R.id.tv_ANsscore48);
        tv_ANsscore49 = (TextView) rootView.findViewById(R.id.tv_ANsscore49);
        tv_ANsscore50 = (TextView) rootView.findViewById(R.id.tv_ANsscore50);
        tv_ANsscore51 = (TextView) rootView.findViewById(R.id.tv_ANsscore51);
        tv_ANsscore52 = (TextView) rootView.findViewById(R.id.tv_ANsscore52);
        tv_ANsscore53 = (TextView) rootView.findViewById(R.id.tv_ANsscore53);
        tv_ANsscore54 = (TextView) rootView.findViewById(R.id.tv_ANsscore54);
        tv_ANsscore55 = (TextView) rootView.findViewById(R.id.tv_ANsscore55);
        tv_ANsscore56 = (TextView) rootView.findViewById(R.id.tv_ANsscore56);
        tv_ANsscore57 = (TextView) rootView.findViewById(R.id.tv_ANsscore57);
        tv_ANsscore58 = (TextView) rootView.findViewById(R.id.tv_ANsscore58);
        tv_ANsscore59 = (TextView) rootView.findViewById(R.id.tv_ANsscore59);
        tv_ANsscore60 = (TextView) rootView.findViewById(R.id.tv_ANsscore60);
        tv_ANsscore61 = (TextView) rootView.findViewById(R.id.tv_ANsscore61);
        tv_ANsscore62 = (TextView) rootView.findViewById(R.id.tv_ANsscore62);
        tv_ANsscore63 = (TextView) rootView.findViewById(R.id.tv_ANsscore63);
        tv_ANsscore64 = (TextView) rootView.findViewById(R.id.tv_ANsscore64);
        tv_ANsscore65 = (TextView) rootView.findViewById(R.id.tv_ANsscore65);
        tv_ANsscore66 = (TextView) rootView.findViewById(R.id.tv_ANsscore66);
        tv_ANsscore67 = (TextView) rootView.findViewById(R.id.tv_ANsscore67);
        tv_ANsscore68 = (TextView) rootView.findViewById(R.id.tv_ANsscore68);
        tv_ANsscore69 = (TextView) rootView.findViewById(R.id.tv_ANsscore69);

        tv_ANsscore70 = (TextView) rootView.findViewById(R.id.tv_ANsscore70);
        tv_ANsscore71 = (TextView) rootView.findViewById(R.id.tv_ANsscore71);
        tv_ANsscore72 = (TextView) rootView.findViewById(R.id.tv_ANsscore72);
        tv_ANsscore73 = (TextView) rootView.findViewById(R.id.tv_ANsscore73);
        et_Ans1 = (EditText) rootView.findViewById(R.id.et_Ans1);
        et_Ans2 = (EditText) rootView.findViewById(R.id.et_Ans2);
        et_Ans3 = (EditText) rootView.findViewById(R.id.et_Ans3);
        et_Ans4 = (EditText) rootView.findViewById(R.id.et_Ans4);
        et_Ans5 = (EditText) rootView.findViewById(R.id.et_Ans5);
        et_Ans6 = (EditText) rootView.findViewById(R.id.et_Ans6);
        et_Ans7 = (EditText) rootView.findViewById(R.id.et_Ans7);
        et_Ans8 = (EditText) rootView.findViewById(R.id.et_Ans8);
        et_Ans9 = (EditText) rootView.findViewById(R.id.et_Ans9);
        et_Ans10 = (EditText) rootView.findViewById(R.id.et_Ans10);
        et_Ans11 = (EditText) rootView.findViewById(R.id.et_Ans11);
        et_Ans12 = (EditText) rootView.findViewById(R.id.et_Ans12);
        et_Ans13 = (EditText) rootView.findViewById(R.id.et_Ans13);
        et_Ans14 = (EditText) rootView.findViewById(R.id.et_Ans14);
        et_Ans15 = (EditText) rootView.findViewById(R.id.et_Ans15);
        et_Ans16 = (EditText) rootView.findViewById(R.id.et_Ans16);
        et_Ans17 = (EditText) rootView.findViewById(R.id.et_Ans17);
        et_Ans18 = (EditText) rootView.findViewById(R.id.et_Ans18);
        et_Ans19 = (EditText) rootView.findViewById(R.id.et_Ans19);
        et_Ans20 = (EditText) rootView.findViewById(R.id.et_Ans20);
        et_Ans21 = (EditText) rootView.findViewById(R.id.et_Ans21);
        et_Ans22 = (EditText) rootView.findViewById(R.id.et_Ans22);
        et_Ans23 = (EditText) rootView.findViewById(R.id.et_Ans23);
        et_Ans24 = (EditText) rootView.findViewById(R.id.et_Ans24);
        et_Ans25 = (EditText) rootView.findViewById(R.id.et_Ans25);
        et_Ans26 = (EditText) rootView.findViewById(R.id.et_Ans26);
        et_Ans27 = (EditText) rootView.findViewById(R.id.et_Ans27);
        et_Ans28 = (EditText) rootView.findViewById(R.id.et_Ans28);
        et_Ans29 = (EditText) rootView.findViewById(R.id.et_Ans29);
        et_Ans30 = (EditText) rootView.findViewById(R.id.et_Ans30);
        et_Ans31 = (EditText) rootView.findViewById(R.id.et_Ans31);
        et_Ans32 = (EditText) rootView.findViewById(R.id.et_Ans32);
        et_Ans33 = (EditText) rootView.findViewById(R.id.et_Ans33);
        et_Ans34 = (EditText) rootView.findViewById(R.id.et_Ans34);
        et_Ans35 = (EditText) rootView.findViewById(R.id.et_Ans35);
        et_Ans36 = (EditText) rootView.findViewById(R.id.et_Ans36);
        et_Ans37 = (EditText) rootView.findViewById(R.id.et_Ans37);
        et_Ans38 = (EditText) rootView.findViewById(R.id.et_Ans38);
        et_Ans39 = (EditText) rootView.findViewById(R.id.et_Ans39);
        et_Ans40 = (EditText) rootView.findViewById(R.id.et_Ans40);
        et_Ans41 = (EditText) rootView.findViewById(R.id.et_Ans41);
        et_Ans42 = (EditText) rootView.findViewById(R.id.et_Ans42);
        et_Ans43 = (EditText) rootView.findViewById(R.id.et_Ans43);
        et_Ans44 = (EditText) rootView.findViewById(R.id.et_Ans44);
        et_Ans45 = (EditText) rootView.findViewById(R.id.et_Ans45);
        et_Ans46 = (EditText) rootView.findViewById(R.id.et_Ans46);
        et_Ans47 = (EditText) rootView.findViewById(R.id.et_Ans47);
        et_Ans48 = (EditText) rootView.findViewById(R.id.et_Ans48);
        et_Ans49 = (EditText) rootView.findViewById(R.id.et_Ans49);
        et_Ans50 = (EditText) rootView.findViewById(R.id.et_Ans50);
        et_Ans51 = (EditText) rootView.findViewById(R.id.et_Ans51);
        et_Ans52 = (EditText) rootView.findViewById(R.id.et_Ans52);
        et_Ans53 = (EditText) rootView.findViewById(R.id.et_Ans53);
        et_Ans54 = (EditText) rootView.findViewById(R.id.et_Ans54);
        et_Ans55 = (EditText) rootView.findViewById(R.id.et_Ans55);
        et_Ans56 = (EditText) rootView.findViewById(R.id.et_Ans56);
        et_Ans57 = (EditText) rootView.findViewById(R.id.et_Ans57);
        et_Ans58 = (EditText) rootView.findViewById(R.id.et_Ans58);
        et_Ans59 = (EditText) rootView.findViewById(R.id.et_Ans59);
        et_Ans60 = (EditText) rootView.findViewById(R.id.et_Ans60);
        et_Ans61 = (EditText) rootView.findViewById(R.id.et_Ans61);
        et_Ans62 = (EditText) rootView.findViewById(R.id.et_Ans62);
        et_Ans63 = (EditText) rootView.findViewById(R.id.et_Ans63);
        et_Ans64 = (EditText) rootView.findViewById(R.id.et_Ans64);
        et_Ans65 = (EditText) rootView.findViewById(R.id.et_Ans65);
        et_Ans66 = (EditText) rootView.findViewById(R.id.et_Ans66);
        et_Ans67 = (EditText) rootView.findViewById(R.id.et_Ans67);
        et_Ans68 = (EditText) rootView.findViewById(R.id.et_Ans68);
        et_Ans69 = (EditText) rootView.findViewById(R.id.et_Ans69);
        et_Ans70 = (EditText) rootView.findViewById(R.id.et_Ans70);
        et_Ans71 = (EditText) rootView.findViewById(R.id.et_Ans71);
        et_Ans72 = (EditText) rootView.findViewById(R.id.et_Ans72);
        et_Ans73 = (EditText) rootView.findViewById(R.id.et_Ans73);

        btnSave = (Button) rootView.findViewById(R.id.btnSave);
    }

    public void showAlert(int iCheck) {
        if (iCheck == 2) {
            CustomAlertSpinner(spin_year, getResources().getString(R.string.selectyear));
        } else if (iCheck == 3) {
            CustomAlertSpinner(spin_month, getResources().getString(R.string.selectmonth));
        }
    }

    public void addtotal(EditText edit, TextView txt3, TextView txt4) {
        int edittotal = 0, txttv1 = 0, tv1 = 0, tv2 = 0, tv3 = 0, tv4 = 0, tv5 = 0, tv6 = 0, tv7 = 0, total = 0, tvtotal = 0;
        if (!edit.getText().toString().equalsIgnoreCase("null") && edit.getText().toString().length() > 0) {
            edittotal = Integer.valueOf(edit.getText().toString());
            txttv1 = Integer.valueOf(txt3.getText().toString());
            total = edittotal * txttv1;
            txt4.setText("" + total);
        }
    }

    public void setyaer(String[] ss) {
        int yaear = 0, month = 0;
        String date = Validate.getcurrentdate();
        String[] datearr = date.split("-");
        if (datearr.length > 0) {
            for (int i = 0; i < ss.length; i++) {
                if (datearr[0].equals(ss[i])) {
                    yaear = i;
                }

            }
            month = Integer.valueOf(datearr[1]);
        }
        spin_year.setSelection(yaear);
        spin_month.setSelection(month);

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

    public void setspinner(String[] ss, String msg, Spinner spin) {
        int yaear = 0;
        for (int i = 0; i < ss.length; i++) {
            if (msg.equals(ss[i])) {
                yaear = i;
            }

        }
        spin.setSelection(yaear);
    }

    public int returnpos(String[] ss, String msg) {
        int value = 0;
        for (int i = 0; i < ss.length; i++) {
            if (msg.equals(ss[i])) {
                value = i;
            }

        }
        return value;
    }

    public int sCheckValidation() {
        try {
            if (spin_year.getSelectedItemPosition() == 0) {
                return 2;

            }
            if (spin_month.getSelectedItemPosition() == 0) {
                return 3;

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;

    }

    public void showQues(View rootView) {
        try {
            ArrayList<HashMap<String, String>> data = null;
            String sql = "select * from mstashaactivity where  LangaugeID=1 and AreaType='R' ";
            data = dataProvider.getDynamicVal(sql);
            //ashaactivity = dataProvider.getashaincetiveques(1);
            if (data != null && data.size() > 0) {

                for (int i = 0; i < data.size(); i++) {
                    String resourceName = "tv_ques" + (i + 1);
                    int resourceID = getResources().getIdentifier(resourceName, "id",
                            getActivity().getPackageName());
                    if (resourceID != 0) {
                        TextView tv = (TextView) rootView.findViewById(resourceID);
                        if (tv != null) {
                            // Take action on TextView tv here...
                            tv.setText(data.get(i).get("Activity"));
                            if (Integer.valueOf(data.get(i).get("Qtype")) == 2) {
                                tv.setBackgroundColor(getResources().getColor(R.color.LightSeaGreen));
                                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            }
                        }
                    }
                    String resourceName1 = "tv_Anstot" + (i + 1);
                    int resourceID1 = getResources().getIdentifier(resourceName1, "id",
                            getActivity().getPackageName());
                    if (resourceID1 != 0) {
                        TextView tv = (TextView) rootView.findViewById(resourceID1);
                        if (tv != null) {
                            // Take action on TextView tv here...
                            tv.setText("" + data.get(i).get("Amount"));
                        }
                    }
                    if (Integer.valueOf(data.get(i).get("Qtype")) == 2) {
                        String Qtbl = "tbl_Ans" + (i + 1);
                        String Qtbl1 = "tbl_Qsrno" + (i + 1);
                        int resourceIDQsrno = getResources().getIdentifier(Qtbl, "id", getActivity().getPackageName());
                        int resourceIDQsrno1 = getResources().getIdentifier(Qtbl1, "id",
                                getActivity().getPackageName());
                        if (resourceIDQsrno != 0) {
                            TableRow tbl = (TableRow) rootView.findViewById(resourceIDQsrno);
                            if (tbl != null) {
                                // Take action on TextView tv here...
                                tbl.setVisibility(View.GONE);
                                if (resourceIDQsrno1 != 0) {
                                    TableRow tbl1 = (TableRow) rootView.findViewById(resourceIDQsrno1);
                                    if (tbl1 != null)
                                        tbl1.setBackgroundColor(getResources().getColor(R.color.LightSeaGreen));
                                }
                            }
                        }
                    }
                    String Qsrno = "tv_Qsrno" + (i + 1);
                    int resourceIDQsrno = getResources().getIdentifier(Qsrno, "id",
                            getActivity().getPackageName());
                    if (resourceIDQsrno != 0) {
                        TextView tv = (TextView) rootView.findViewById(resourceIDQsrno);
                        if (tv != null) {
                            // Take action on TextView tv here...
                            tv.setText(data.get(i).get("Qsrno"));
                            if (Integer.valueOf(data.get(i).get("Qtype")) == 2) {
                                tv.setText("");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void saveIncentive(View rootView) {
        try {
            ArrayList<HashMap<String, String>> data = null;
            String sql = "select * from mstashaactivity where  LangaugeID=1 and AreaType='R' ";
            data = dataProvider.getDynamicVal(sql);
            int ActivityAmount = 0, ActivityTotal = 0, ActivityNo = 0;
            int savecheck = 0;
            String srno = "";
            String IncentiveSurveyGUID = "";
            int Month = 0;
            int Year = 0;
            int AshaStatus = 0;
            int AFStatus = 0;
            int AMStatus = 0;
            int BCPMStatus = 0;
            int AmtRecieved = 0;
            int Claim = 0;
            int NotApproved = 0;
            int IsEdited = 0;
            int CreatedBy = 0;

            int AshaID = 0;
            int ANMID = 0;
            int AFID = 0;
            String flag = "";
            if (global.getIncentiveSurveyGUID() != null&&global.getIncentiveSurveyGUID() .length()>0) {
                IncentiveSurveyGUID = global.getIncentiveSurveyGUID();
                flag = "U";
            } else {
                IncentiveSurveyGUID = Validate.random();
                flag = "I";
            }
            if (spin_month.getSelectedItemPosition() > 0) {
                Month = returnpos(getResources().getStringArray(R.array.monthincentive), spin_month.getSelectedItem().toString());
            }
            if (spin_year.getSelectedItemPosition() > 0) {
                Year = Integer.valueOf(spin_year.getSelectedItem().toString());
            }
            CreatedBy = global.getUserID();
            AshaID = Integer.valueOf(global.getsGlobalAshaCode());
            ANMID = Integer.valueOf(global.getsGlobalANMCODE());
            String sqlcount = "select count(*) from tblincentivesurvey where year=" + Year + " and month=" + Month + " and createdBy=" + CreatedBy + "";
            int recordcount = dataProvider.getMaxRecord(sqlcount);
            if (recordcount == 0 || (recordcount > 0 && flag.equalsIgnoreCase("U"))) {
                if (data != null && data.size() > 0) {

                    for (int i = 0; i < data.size(); i++) {
                        if (Integer.valueOf(data.get(i).get("Qtype")) == 1) {
                            String resourceName = "et_Ans" + (i + 1);
                            int resourceID = getResources().getIdentifier(resourceName, "id",
                                    getActivity().getPackageName());
                            if (resourceID != 0) {
                                EditText edit = (EditText) rootView.findViewById(resourceID);
                                if (edit != null) {
                                    // Take action on TextView tv here...
                                    if (edit.getText().toString().length() > 0) {
                                        ActivityNo = Integer.valueOf(edit.getText().toString());
                                    } else {
                                        ActivityNo = 0;
                                    }


                                }
                            }
                            String resourceName1 = "tv_Anstot" + (i + 1);
                            int resourceID1 = getResources().getIdentifier(resourceName1, "id",
                                    getActivity().getPackageName());
                            if (resourceID1 != 0) {
                                TextView tv = (TextView) rootView.findViewById(resourceID1);
                                if (tv != null) {
                                    // Take action on TextView tv here...
                                    if (tv.getText().toString().length() > 0) {
                                        ActivityAmount = Integer.valueOf(tv.getText().toString());
                                    } else {
                                        ActivityAmount = 0;
                                    }

                                }
                            }
                            String resourceName2 = "tv_ANsscore" + (i + 1);
                            int resourceID2 = getResources().getIdentifier(resourceName2, "id",
                                    getActivity().getPackageName());
                            if (resourceID2 != 0) {
                                TextView tv = (TextView) rootView.findViewById(resourceID2);
                                if (tv != null) {
                                    // Take action on TextView tv here...
                                    if (tv.getText().toString().length() > 0) {
                                        ActivityTotal = Integer.valueOf(tv.getText().toString());
                                    } else {
                                        ActivityTotal = 0;
                                    }
                                    Claim = Claim + ActivityTotal;
                                }
                            }
                            String Qsrno = "tv_Qsrno" + (i + 1);
                            int resourceIDQsrno = getResources().getIdentifier(Qsrno, "id",
                                    getActivity().getPackageName());
                            if (resourceIDQsrno != 0) {
                                TextView tv = (TextView) rootView.findViewById(resourceIDQsrno);
                                if (tv != null) {
                                    // Take action on TextView tv here...
                                    srno = tv.getText().toString();

                                }
                            }
                            if (ActivityNo > 0) {
                                savecheck = 1;
                                String IncentiveGUID = Validate.random();
                                dataProvider.insertIncetiveDetail(IncentiveSurveyGUID, Month, Year, ActivityAmount, ActivityTotal, ActivityNo, srno, CreatedBy, AshaID, ANMID, AFID, flag, AmtRecieved, IncentiveGUID);

                            }
                        }
                    }
                }
                if (savecheck > 0) {
                    int count = dataProvider.insertIncetive(IncentiveSurveyGUID, Month, Year, AshaStatus, AFStatus, AMStatus, BCPMStatus, Claim, NotApproved, IsEdited, CreatedBy, AshaID, ANMID, AFID, flag, AmtRecieved);
                    if (count > 0) {
                        CustomAlert(getString(R.string.SaveSuccessfully), 1);
                    }
                } else {
                    CustomAlert(getString(R.string.selectatleast), 0);
                }
            } else {
                CustomAlert(getString(R.string.thisrecordexist), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fillspinner(Spinner spin, String[] ss) {

        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, ss);
        spin.setAdapter(adapter);
    }

    public void CustomAlert(String msg, final int flag) {
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
        btnok.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View v) {
                if (flag == 1) {
                    Intent in = getActivity().getIntent();
                    getActivity().finish();
                    startActivity(in);
                }
                dialog.dismiss();
            }
        });

        // Display the dialog
        dialog.show();

    }

}
