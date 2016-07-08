package com.ktds.cain.myfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MainFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getActivity().setTitle("Fragment Changed.");
        // Action바를 보이고 싶지 않을때ㅣ
        ((MainActivity)getActivity()).getSupportActionBar().hide();

        // 이 프래그먼트가 보여줄 레이아웃을 정의.
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_main, container, false);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).getSupportActionBar().show();
            }
        });

        return layout;
    }
}
