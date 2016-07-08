package com.ktds.cain.myapplication.calculator;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ktds.cain.myapplication.R;
import com.ktds.cain.myapplication.utility.FragmentReplaceable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String FIRST = "param1";
    private static final String SECOND = "param2";
    private static final String RESULT = "param3";
    private EditText etFirst;
    private EditText etSecond;
    private EditText etResult;
    private boolean isValidCheck1;
    private boolean isValidCheck2;
    private Button btnPlus;
    private Button btnMinus;
    private Button btnMulti;
    private Button btnDiv;

    private String result;
    private String operator;
    private String first;
    private String second;
    public CalculatorFragment() {
        // Required empty public constructor
    }

    public static CalculatorFragment newInstance(String first, String second, String result) {
        CalculatorFragment fragment = new CalculatorFragment();
        Bundle args = new Bundle();
        args.putString(FIRST, first);
        args.putString(SECOND, second);
        args.putString(RESULT, result);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            first = getArguments().getString(FIRST);
            second = getArguments().getString(SECOND);
            result = getArguments().getString(RESULT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calculator, container, false);

        etFirst = (EditText) view.findViewById(R.id.etFirst);
        etFirst.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                validationCheck(v, hasFocus);
            }
        });

        etSecond = (EditText) view.findViewById(R.id.etSecond);
        etSecond.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                validationCheck(v, hasFocus);
            }
        });
        btnPlus = (Button) view.findViewById(R.id.btnPlus);
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnContents(v);
            }
        });
        btnMinus = (Button) view.findViewById(R.id.btnMinus);
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnContents(v);
            }
        });

        btnMulti = (Button) view.findViewById(R.id.btnMulti);
        btnMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnContents(v);
            }
        });
        btnDiv = (Button) view.findViewById(R.id.btnDiv);
        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnContents(v);
            }
        });

        etResult = (EditText) view.findViewById(R.id.etResult);
        //etFirst.setText(FIRST);
        //etSecond.setText(SECOND);
        etResult.setText(result);
        return view;
    }

    private void validationCheck(View v, boolean hasFocus) {
        if(!hasFocus) {
            Pattern p = Pattern.compile("^[0-9.]*$");
            Matcher m1 = p.matcher(etFirst.getText().toString());
            Matcher m2 = p.matcher(etSecond.getText().toString());
            isValidCheck1 = m1.matches();
            isValidCheck2 = m2.matches();
            if(!isValidCheck1) {
                Snackbar.make(v, "숫자만 입력해주세요.",Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }
            if(!isValidCheck2) {
                Snackbar.make(v, "숫자만 입력해주세요.",Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }
        } else {
            if(isValidCheck1) {
                etSecond.requestFocus();
            } else if (isValidCheck2) {
                return;
            }
        }
    }

    private void returnContents(View v) {
        operator = (String) ((Button) v).getText();
        if(etFirst.getText().toString().length() == 0) {
            Snackbar.make(v, "첫번째 숫자를 작성하세요.",Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            etFirst.requestFocus();
            return;
        }
        if(etSecond.getText().toString().length() == 0) {
            Snackbar.make(v, "두번째 숫자를 작성하세요.",Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            etSecond.requestFocus();
            return;
        }
        if(etSecond.getText().toString().equals("0") && operator.equals("/")) {
            Snackbar.make(v, "0 나눌 수 없습니다.",Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            etSecond.requestFocus();
            return;
        }

        else {
            ((FragmentReplaceable) getActivity()).replaceFragment(
                    ResultFragment.newInstance(
                            etFirst.getText().toString(),
                            etSecond.getText().toString(),
                            operator = ((Button) v).getText().toString()
                    )
            );
        }
    }
}
