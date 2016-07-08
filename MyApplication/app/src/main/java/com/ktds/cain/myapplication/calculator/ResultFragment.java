package com.ktds.cain.myapplication.calculator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ktds.cain.myapplication.R;
import com.ktds.cain.myapplication.utility.FragmentReplaceable;

public class ResultFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String NUMBER1 = "param1";
    private static final String NUMBER2 = "param2";
    private static final String OPERATOR = "param3";

    // TODO: Rename and change types of parameters
    private Button btnClose;
    private EditText etResult;
    private String numberOne;
    private String numberTwo;
    private int one;
    private double two;
    private double result;
    private String operator;

    public ResultFragment() {
        // Required empty public constructor
    }

    public static ResultFragment newInstance(String numberOne, String numberTwo, String operator) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putString(NUMBER1, numberOne);
        args.putString(NUMBER2, numberTwo);
        args.putString(OPERATOR, operator);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            numberOne = getArguments().getString(NUMBER1);
            numberTwo = getArguments().getString(NUMBER2);
            operator = getArguments().getString(OPERATOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        one = Integer.parseInt(numberOne);
        two = Integer.parseInt(numberTwo);
        result = calculate(one, two, operator);
        etResult = (EditText) view.findViewById(R.id.etResult);
        etResult.setText(numberOne + " " + operator + " " + numberTwo + " = " + result);

        btnClose = (Button) view.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentReplaceable) getActivity()).replaceFragment(
                        CalculatorFragment.newInstance(
                                numberOne,
                                numberTwo,
                                etResult.getText().toString()
                        )
                );
            }
        });
        return view;
    }

    private double calculate(int one, double two, String operator) {
        if (operator.equals("+")) {
            return (int) (one + two);
        } else if (operator.equals("-")) {
            return (int) (one - two);
        } else if (operator.equals("*")) {
            return (int) (one * two);
        } else if (operator.equals("/")) {
            return one / two;
        } else {
            return 0;
        }
    }
}