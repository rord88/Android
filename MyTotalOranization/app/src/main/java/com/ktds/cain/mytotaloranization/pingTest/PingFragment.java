package com.ktds.cain.mytotaloranization.pingTest;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ktds.cain.mytotaloranization.R;
import com.ktds.cain.mytotaloranization.calculator.ResultFragment;
import com.ktds.cain.mytotaloranization.utility.FragmentReplaceable;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText etHost;
    private TextView ttvPingTest;
    private Button btnStart;
    private Button btnStop;


    private Process ipAddress;
    private Runtime runtime;
    private int value;

    public PingFragment() {
        // Required empty public constructor
    }

    public static PingFragment newInstance(String param1, String param2) {
        PingFragment fragment = new PingFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_ping, container, false);
        etHost = (EditText) view.findViewById(R.id.etHost);
        etHost.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        btnStart = (Button) view.findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeCommand();
            }
        });
        btnStop = (Button) view.findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ipAddress.destroy();
            }
        });

        ttvPingTest = (TextView) view.findViewById(R.id.ttvPingtest);

        return view;
    }



    private boolean executeCommand(){
        runtime = Runtime.getRuntime();

        try
        {
            ipAddress = runtime.exec("/system/bin/ping -c " + etHost.getText().toString());
            value = ipAddress.waitFor();

            ttvPingTest.setText(value + "");

            if(value == 0){
                return true;
            }else{
                return false;
            }
        }
        catch (InterruptedException ie)
        {
            ie.getMessage();
            Log.d("executeCommandException", ie.getMessage());
        }
        catch (IOException ioe)
        {
            ioe.getMessage();
            Log.d("executeCommandException", ioe.getMessage());
        }
        return false;
    }


    private void validationCheck(View v, boolean hasFocus) {
        if(!hasFocus) {


            String validIp = "^([1-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])){3}$";

            if (!Pattern.matches(validIp, ipAddr )) {
                return "IP주소 " + ipAddr + " 가 올바르지 않습니다.";
            }


            Pattern p = Pattern.compile("^[0-9.]*$");
            Matcher m = p.matcher(etHost.getText().toString());
            isValidCheck = m.matches();
            if(!isValidCheck) {
                Snackbar.make(v, "숫자만 입력해주세요.",Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }
        } else {
            if(isValidCheck) {
                etHost.requestFocus();
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

