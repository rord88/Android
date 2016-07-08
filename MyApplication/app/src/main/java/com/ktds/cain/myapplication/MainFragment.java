package com.ktds.cain.myapplication;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ktds.cain.myapplication.calculator.CalculatorFragment;
import com.ktds.cain.myapplication.memo.ListActivity;
import com.ktds.cain.myapplication.pingTest.PingFragment;
import com.ktds.cain.myapplication.utility.FragmentReplaceable;


public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private Button btnCalculator;
    private Button btnPingTest;
    private Button btnMemo;
    private ImageView ivUtil;

    private Fragment calculatorFragment;
    private Fragment pingTestFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("My Home Utility");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        calculatorFragment = new CalculatorFragment();
        pingTestFragment = new PingFragment();

        btnCalculator = (Button) view.findViewById(R.id.btnCalculator);
        btnCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentReplaceable) getActivity()).replaceFragment(calculatorFragment);
            }
        });
        btnPingTest = (Button) view.findViewById(R.id.btnPingTest);
        btnPingTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentReplaceable) getActivity()).replaceFragment(pingTestFragment);
            }
        });

        btnMemo = (Button) view.findViewById(R.id.btnMemo);
        btnMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ListActivity.class);
                startActivity(intent);
            }
        });



        ivUtil = (ImageView) view.findViewById(R.id.ivUtil);
//        ivUtil.setImageResource(R.mipmap.utility_house);
        Resources res = getResources();
        ivUtil.setImageDrawable(res.getDrawable(R.mipmap.utility_house));
        return  view;
    }
}
