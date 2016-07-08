
package com.ktds.cain.mybeaconapplication;

import android.Manifest;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.ktds.cain.mybeaconapplication.permissionRequester.PermissionRequester;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CouponActivity extends AppCompatActivity {

    private TextView tvLatestLottoCount;
    private ImageView ivNumber1;
    private ImageView ivNumber2;
    private ImageView ivNumber3;
    private ImageView ivNumber4;
    private ImageView ivNumber5;
    private ImageView ivNumber6;
    private ImageView ivNumber7;

    private TextView tvWinCount;
    private TextView tvWinMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);

        PermissionRequester.Builder request = new PermissionRequester.Builder(this);
        request.create().request(
                Manifest.permission.INTERNET,
                10000,
                new PermissionRequester.OnClickDenyButtonListener() {
                    @Override
                    public void onClick(Activity activity) {
                        activity.finish();
                    }
                });

        tvLatestLottoCount = (TextView) findViewById(R.id.tvLatestLottoCount);
        tvWinCount = (TextView) findViewById(R.id.tvWinCount);
        tvWinMoney = (TextView) findViewById(R.id.tvWinMoney);

        ivNumber1 = (ImageView) findViewById(R.id.ivNumber1);
        ivNumber2 = (ImageView) findViewById(R.id.ivNumber2);
        ivNumber3 = (ImageView) findViewById(R.id.ivNumber3);
        ivNumber4 = (ImageView) findViewById(R.id.ivNumber4);
        ivNumber5 = (ImageView) findViewById(R.id.ivNumber5);
        ivNumber6 = (ImageView) findViewById(R.id.ivNumber6);
        ivNumber7 = (ImageView) findViewById(R.id.ivNumber7);

    }

    @Override
    protected void onResume() {
        super.onResume();
        GetLottoNumberTask task = new GetLottoNumberTask();
        task.execute();
    }

    private class GetLottoNumberTask extends AsyncTask<Void, Void, Map<String, String>> {

        @Override
        protected Map<String, String> doInBackground(Void... params) {
            Map<String, String> result = new HashMap<String, String>();
            try {
                Document document = Jsoup
                        .connect("http://www.nlotto.co.kr/common.do?method=main")
                        .get();
                Elements elements = document.select(".lotto_area #lottoDrwNo");
                result.put("latestLottoCount", elements.text());

                for(int i = 1; i<7; i++ ) {
                    elements = document.select(".lotto_area #numView #drwtNo" + i);
                    result.put("number" + i, elements.attr("src"));
                }

                elements = document.select(".lotto_area #numView #bnusNo");
                result.put("number7", elements.attr("src"));

                elements = document.select(".lotto_area .winner_num #lottoNo1Su");
                result.put("winGameCount", elements.text());

                elements = document.select(".lotto_area .winner_money #lottoNo1SuAmount");
                result.put("winGameMoney", elements.text());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Map<String, String> map) {

            tvLatestLottoCount.setText(map.get("latestLottoCount") + "회 당첨번호" );
            tvWinCount.setText("총" + map.get("winGameCount") + "게임 당첨");
            tvWinMoney.setText("1등" + map.get("winGameMoney") + "원");

            for(int i = 1; i < 8; i++) {
                GetImageTask task = new GetImageTask();
                task.execute(map.get("number"+ i), "number" + i);
            }

        }
    }

    private class GetImageTask extends AsyncTask<String, Void, Bitmap> {

        private String numberType;

        @Override
        protected Bitmap doInBackground(String... params) {
            Log.d("Result", params[0]);
            numberType = params[1];
            Bitmap bitmap = null;

            try {
                URL url = new URL("http://www.nlotto.co.kr" + params[0]);
                Log.d("Result", params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);

                connection.connect();

                bitmap = BitmapFactory.decodeStream(connection.getInputStream());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(numberType.equals("number1")) {
                ivNumber1.setImageBitmap(bitmap);
            }
            if(numberType.equals("number2")) {
                ivNumber2.setImageBitmap(bitmap);
            }
            if(numberType.equals("number3")) {
                ivNumber3.setImageBitmap(bitmap);
            }
            if(numberType.equals("number4")) {
                ivNumber4.setImageBitmap(bitmap);
            }
            if(numberType.equals("number5")) {
                ivNumber5.setImageBitmap(bitmap);
            }
            if(numberType.equals("number6")) {
                ivNumber6.setImageBitmap(bitmap);
            }
            if(numberType.equals("number7")) {
                ivNumber7.setImageBitmap(bitmap);
            }

        }
    }
}
