package com.ktds.cain.mymultifragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Random;

public class MainActivity extends AppCompatActivity  implements FragmentReplaceable{

    private Fragment firstFragment;
    private Fragment secondFragment;
    private Fragment thirdFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 첫 번째 프래그먼트를 생성과 동시에 데이터를 전달한다.
        firstFragment = FirstFragment.newInstance(
                new Random().nextInt(45) + 1,
                new Random().nextInt(45) + 1,
                new Random().nextInt(45) + 1,
                new Random().nextInt(45) + 1,
                new Random().nextInt(45) + 1,
                new Random().nextInt(45) + 1
        );

//        잘 사용되지 않는 방식
//        firstFragment = new FirstFragment();
//        secondFragment = new SecondFragment();
//        thirdFragment = new ThirdFragment();
        setDefaultFragment();
    }
    /**
     * MainActivity가 처음 실행될 때
     * 최초로 보여질 Fragment를 셋팅한다.
     */
    public void setDefaultFragment() {
        // 화면에 보여지는 프래그먼트를 추가하거나 바꿀 수 있는 객체를 만든다.
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // 첫 번째로 보여지는 프래그먼트는 container에 FirstFragment로 설정한다.
        transaction.add(R.id.container, firstFragment);
        //프래그먼트의 변경사항을 반영시킨다.
        transaction.commit();

    }

    /**
     * @see FragmentReplaceable
     * @param fragmentId
     */

    public void replaceFragment(int fragmentId) {
        // 화면에 보여지는 프래그먼트를 추가하거나 바꿀 수 있는 객체를 만든다.
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // 다음으로 보여지는 프래그먼트는 fragmentId로 설정한다.
        if( fragmentId == 1 ) {
            transaction.replace(R.id.container, firstFragment);
        } else if ( fragmentId == 2) {
            transaction.replace(R.id.container, secondFragment);
        } else if ( fragmentId == 3) {
            transaction.replace(R.id.container, thirdFragment);
        }

        // Back 버튼 클릭시, 이전 프래그먼트로 이동시키도록 한다.
        transaction.addToBackStack(null);

        //프래그먼트의 변경사항을 반영시킨다.
        transaction.commit();
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        //뒤로가기 버튼을 클릭 했을 때, 경유를 하지 않고 validation 을 하지 않는다면 에러가 발생하기 때문에 해주어야 한다.
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if( fragment instanceof FirstFragment ) {
            firstFragment = fragment;
            transaction.replace(R.id.container, firstFragment);
        } else if ( fragment instanceof  SecondFragment) {
            secondFragment = fragment;
            transaction.replace(R.id.container, secondFragment);
        } else if ( fragment instanceof ThirdFragment) {
            thirdFragment = fragment;
            transaction.replace(R.id.container, thirdFragment);
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
