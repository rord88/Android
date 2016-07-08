package com.ktds.cain.mymultifragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;


public class FirstFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String NUMBER1 = "param1";
    private static final String NUMBER2 = "param2";
    private static final String NUMBER3 = "param3";
    private static final String NUMBER4 = "param4";
    private static final String NUMBER5 = "param5";
    private static final String NUMBER6 = "param6";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int number1;
    private int number2;
    private int number3;
    private int number4;
    private int number5;
    private int number6;

    public static FirstFragment newInstance(int number1, int number2, int number3, int number4, int number5, int number6) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putInt(NUMBER1, number1);
        args.putInt(NUMBER2, number2);
        args.putInt(NUMBER3, number3);
        args.putInt(NUMBER4, number4);
        args.putInt(NUMBER5, number5);
        args.putInt(NUMBER6, number6);
        fragment.setArguments(args);
        return fragment;
    }
    public FirstFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            number1 = getArguments().getInt(NUMBER1, 0);
            number2 = getArguments().getInt(NUMBER2, 0);
            number3 = getArguments().getInt(NUMBER3, 0);
            number4 = getArguments().getInt(NUMBER4, 0);
            number5 = getArguments().getInt(NUMBER5, 0);
            number6 = getArguments().getInt(NUMBER6, 0);

        }
    }

    private TextView tvNumber1;
    private TextView tvNumber2;
    private TextView tvNumber3;
    private TextView tvNumber4;
    private TextView tvNumber5;
    private TextView tvNumber6;

    private Button nextFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_first, container, false);
        tvNumber1 = (TextView) view.findViewById(R.id.tvNumber1);
        tvNumber2 = (TextView) view.findViewById(R.id.tvNumber2);
        tvNumber3 = (TextView) view.findViewById(R.id.tvNumber3);
        tvNumber4 = (TextView) view.findViewById(R.id.tvNumber4);
        tvNumber5 = (TextView) view.findViewById(R.id.tvNumber5);
        tvNumber6 = (TextView) view.findViewById(R.id.tvNumber6);

        tvNumber1.setText(number1 + "");
        tvNumber2.setText(number2 + "");
        tvNumber3.setText(number3 + "");
        tvNumber4.setText(number4 + "");
        tvNumber5.setText(number5 + "");
        tvNumber6.setText(number6 + "");


        nextFragment = (Button) view.findViewById(R.id.nextFragment);
        nextFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 안좋은 방법 : 클래스 캐스팅을 통한 방법
                //((MainActivity) getActivity()).replaceFragment(2);
                // 좋은 방법 : 인터페이스를 통한 방법( MainActivity에서 현재로써는 replaceFragment를 못불러오기 때문에 템플릿 콜백형식이 불가능)
                // 어디에서든 Fragment를 사용이 가능하다. 안좋은 방법에서는 MainActivity라는 클래스 하나만을 지정했기때문에 유연하지가 못하다.
                ((FragmentReplaceable) getActivity()).replaceFragment(
                        SecondFragment.newInstance(
                                new Random().nextInt(100) + 1,
                                new Random().nextInt(100) + 1,
                                new Random().nextInt(100) + 1
                        )
                );
            }
        });
        return view;
    }
}
