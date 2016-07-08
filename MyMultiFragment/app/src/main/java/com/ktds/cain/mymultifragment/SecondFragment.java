package com.ktds.cain.mymultifragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class SecondFragment extends Fragment {
    private static final String NUMBER1 = "param1";
    private static final String NUMBER2 = "param2";
    private static final String NUMBER3 = "param3";

    // TODO: Rename and change types of parameters
    private int number1;
    private int number2;
    private int number3;

    public SecondFragment() {
        // Required empty public constructor
    }

    public static SecondFragment newInstance(int number1, int number2, int number3) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putInt(NUMBER1, number1);
        args.putInt(NUMBER2, number2);
        args.putInt(NUMBER3, number3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            number1 = getArguments().getInt(NUMBER1, 0);
            number2 = getArguments().getInt(NUMBER2, 0);
            number3 = getArguments().getInt(NUMBER3, 0);
        }
    }
    private TextView tvNumber1;
    private TextView tvNumber2;
    private TextView tvNumber3;
    private Button nextFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        tvNumber1 = (TextView) view.findViewById(R.id.tvNumber1);
        tvNumber2 = (TextView) view.findViewById(R.id.tvNumber2);
        tvNumber3 = (TextView) view.findViewById(R.id.tvNumber3);

        tvNumber1.setText(number1 + "");
        tvNumber2.setText(number2 + "");
        tvNumber3.setText(number3 + "");
        nextFragment = (Button) view.findViewById(R.id.nextFragment);
        nextFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentReplaceable) getActivity()).replaceFragment(
                        ThirdFragment.newInstance(
                                new Random().nextInt(100) + 1,
                                new Random().nextInt(100) + 1,
                                new Random().nextInt(100) + 1,
                                new Random().nextInt(100) + 1,
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
