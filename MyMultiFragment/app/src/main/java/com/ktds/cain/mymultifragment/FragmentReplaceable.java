package com.ktds.cain.mymultifragment;

import android.support.v4.app.Fragment;

import java.io.Serializable;

/**
 * Created by 206-013 on 2016-06-16.
 */
public interface FragmentReplaceable extends Serializable {

    public void replaceFragment(int fragmentId);

    public void replaceFragment(Fragment fragment);

}
