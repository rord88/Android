package com.ktds.cain.mytotaloranization.utility;

import android.support.v4.app.Fragment;

import java.io.Serializable;

/**
 * Created by 206-013 on 2016-06-17.
 */
public interface FragmentReplaceable extends Serializable {

    public void replaceFragment(Fragment fragment);

    public void openFragment(Fragment fragment);

}
