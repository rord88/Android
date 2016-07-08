package com.ktds.cain.mytotaloranization;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ktds.cain.mytotaloranization.calculator.CalculatorFragment;
import com.ktds.cain.mytotaloranization.pingTest.PingFragment;
import com.ktds.cain.mytotaloranization.utility.FragmentReplaceable;


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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
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
    private ImageView ivUtil;

    private Fragment calculatorFragment;
    private Fragment pingTestFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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


        ivUtil = (ImageView) view.findViewById(R.id.ivUtil);
//        ivUtil.setImageResource(R.mipmap.utility_house);
        Resources res = getResources();
        ivUtil.setImageDrawable(res.getDrawable(R.mipmap.utility_house));
        return  view;
    }
}
