package com.ktds.cain.mydrawablelayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.ktds.cain.mydrawablelayout.gallery.GalleryFragmentFive;
import com.ktds.cain.mydrawablelayout.gallery.GalleryFragmentFour;
import com.ktds.cain.mydrawablelayout.gallery.GalleryFragmentOne;
import com.ktds.cain.mydrawablelayout.gallery.GalleryFragmentSix;
import com.ktds.cain.mydrawablelayout.gallery.GalleryFragmentThree;
import com.ktds.cain.mydrawablelayout.gallery.GalleryFragmentTwo;

public class GallaryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public GallaryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GallaryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GallaryFragment newInstance(String param1, String param2) {
        GallaryFragment fragment = new GallaryFragment();
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

    // 'com.astuetz:pagerslidingtabstrip:1.0.1' 을 사용하게 되면서 버튼은 필요없게 되었다.
//    private Button btnFirstGallery;
//    private Button btnSecondGallery;
//    private Button btnThirdGallery;
    private ViewPager pager;
    private PagerSlidingTabStrip tabs;

    private Fragment galleryFragmentOne;
    private Fragment galleryFragmentTwo;
    private Fragment galleryFragmentThree;
    private Fragment galleryFragmentFour;
    private Fragment galleryFragmentFive;
    private Fragment galleryFragmentSix;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallary, container, false);
        galleryFragmentOne = new GalleryFragmentOne();
        galleryFragmentTwo = new GalleryFragmentTwo();
        galleryFragmentThree = new GalleryFragmentThree();
        galleryFragmentFour = new GalleryFragmentFour();
        galleryFragmentFive = new GalleryFragmentFive();
        galleryFragmentSix = new GalleryFragmentSix();


//        btnFirstGallery = (Button) view.findViewById(R.id.btnFirstGallery);
//        btnFirstGallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pager.setCurrentItem(0);
//            }
//        });
//        btnSecondGallery = (Button) view.findViewById(R.id.btnSecondGallery);
//        btnSecondGallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pager.setCurrentItem(1);
//            }
//        });
//        btnThirdGallery = (Button) view.findViewById(R.id.btnThirdGallery);
//        btnThirdGallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pager.setCurrentItem(2);
//            }
//        });

        pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setAdapter(new PagerAdapter( getChildFragmentManager()));
        pager.setOffscreenPageLimit(3);
        pager.setCurrentItem(0);

        tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        tabs.setViewPager(pager);

        getActivity().setTitle("Gallery Fragment");

        return view;
    }

    private String[] pageTitle = {"Page_1", "Page_2", "Page_3", "Page_4", "Page_5", "Page_6"};

    private class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return pageTitle[position];
        }

        /**
         * View Pager의 Fragment들은 각각 Index를 가진다.
         * Android OS로 부터 요청된 Pager의 Index를 보내주면,
         * 해당되는 Fragment를 리턴시킨다.
         * @param position
         * @return
         */
        @Override
        public Fragment getItem(int position) {
            if ( position == 0 ) {
                return galleryFragmentOne;
            } else if ( position == 1 ) {
                return galleryFragmentTwo;
            } else if ( position == 2 ) {
                return galleryFragmentThree;
            } else if ( position == 3 ) {
                return galleryFragmentFour;
            } else if ( position == 4 ) {
                return galleryFragmentFive;
            } else {
                return galleryFragmentSix;
            }
        }

        /**
         * View Pager에 몇개의 Fragment가 들어가는지 설정한다.
         * @return
         */
        @Override
        public int getCount() {
            return 6;
        }
    }

}
