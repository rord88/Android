package com.ktds.cain.myapplication.pingTest;

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

import com.ktds.cain.myapplication.R;
import com.ktds.cain.myapplication.utility.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    private EditText edIPAddress;
    private TextView tvPingtest;
    private Button btnStart;
    private Button btnStop;
    private boolean isValidCheck;

    private Network network;

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
        network = new Network();




//        TextView info = (TextView) findViewById(R.id.info);
//
//        EditText edit = (EditText) findViewById(R.id.edit);
//        Editable host = edit.getText();
//
//        InetAddress in;
//        in = null;
//        // Definimos la ip de la cual haremos el ping
//        try {
//            in = InetAddress.getByName(host.toString());
//        } catch (UnknownHostException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        // Definimos un tiempo en el cual ha de responder
//        try {
//            if (in.isReachable(5000)) {
//                info.setText("Responde OK");
//            } else {
//                info.setText("No responde: Time out");
//            }
//        } catch (IOException ioe) {
//            // TODO Auto-generated catch block
//            info.setText(ioe.toString());
//        }












        edIPAddress = (EditText) view.findViewById(R.id.edIPAddress);
        edIPAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                validationCheck(v, hasFocus);
            }
        });

        btnStart = (Button) view.findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                run();
            }
        });
        btnStop = (Button) view.findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ipAddress.destroy();
            }
        });

        tvPingtest = (TextView) view.findViewById(R.id.tvPingtest);

        return view;
    }


    private void run() {
        Runtime runTime = Runtime.getRuntime();

        String host = "192.168.0.13";
        String cmd = "ping -c 1 -W 10 "+host; //-c 1은 반복 횟수를 1번만 날린다는 뜻
        Process proc = null;

        try {
            proc = runTime.exec(cmd);
        } catch(IOException ie){
            Log.d("runtime.exec()", ie.getMessage());
        }

        try {
            proc.waitFor();
            tvPingtest.setText(proc.waitFor());
        } catch(InterruptedException ie){
            Log.d("proc.waitFor", ie.getMessage());
        }
//여기서 반환되는 ping 테스트의 결과 값은 0, 1, 2 중 하나이다.
// 0 : 성공, 1 : fail, 2 : error이다.
        int result = proc.exitValue();

        if (result == 0) {
            Log.d("ping test 결과", "네트워크 연결 상태 양호");
        } else {
            Log.d("ping test 결과", "연결되어 있지 않습니다.");
        }
    }


    //    executeCmd("ping -c 1 -w 1 google.com", false);
    private String executeCmd(String cmd, boolean sudo){
        try {

            Process p;
            if(!sudo)
                p= Runtime.getRuntime().exec(cmd);
            else{
                p= Runtime.getRuntime().exec(new String[]{"su", "-c", cmd});
            }
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String s;
            String res = "";
            while ((s = stdInput.readLine()) != null) {
                res += s + "\n";
            }
            p.destroy();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    private boolean executeCommand(){
        runtime = Runtime.getRuntime();

        try
        {
            ipAddress = runtime.exec("/system/bin/ping -c " + edIPAddress.getText().toString());
            value = ipAddress.waitFor();

            edIPAddress.setText(value+"");

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

            Pattern p = Pattern.compile("^([1-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])){3}$\"");
            Matcher m = p.matcher(edIPAddress.getText().toString());
            isValidCheck = m.matches();
            if(!isValidCheck) {
                Snackbar.make(v, getHost().getClass().toString()+"의 IP 형식이 맞지 않습니다. 다시 입력해주세요.",Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                edIPAddress.requestFocus();
            }
        } else {
            if(isValidCheck) {
                return;
            }
        }
    }

}
