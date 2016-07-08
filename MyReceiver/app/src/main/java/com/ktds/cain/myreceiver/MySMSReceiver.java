package com.ktds.cain.myreceiver;


import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MySMSReceiver extends BroadcastReceiver {
    public MySMSReceiver() {
    }

    /**
     * 안드로이드에 문자메세지가 도착할 경우, 실행.
     * OS에서 테스크를 모든 어플리케이션에 배분할 때 해당 어플리케이션도 동작한다.
     * @param context
     * @param intent
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceive(Context context, Intent intent) {



        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.HORIZONTAL);

        ImageView iv = new ImageView(context);
        Drawable d = context.getDrawable(R.mipmap.ic_launcher);
        iv.setImageDrawable(d);
        TextView tv = new TextView(context);
        tv.setText("문자메세지 도착");

        tv.setTextColor(Color.BLACK);

        ll.addView(iv);
        ll.addView(iv);


        Toast toast = Toast.makeText(context, "receiver에 의한 문자메세지 받음", Toast.LENGTH_SHORT);
        toast.setView(ll);
        toast.show();
    }
}
