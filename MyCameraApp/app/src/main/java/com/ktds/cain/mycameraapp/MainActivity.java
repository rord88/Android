package com.ktds.cain.mycameraapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ktds.cain.mycameraapp.utility.Base64Utility;
import com.ktds.cain.mycameraapp.utility.HttpClient;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // 사진을 찍었을 때, 기본설정으로 어떻게 찍느냐에 따라서 위치 저장이 다르기 때문에
    private Button btnTakePicture;
    private ImageView ivPicture;

    private Button btnSendPicture;
    // Base64로 변경할때 필요한 변수
    private String encodedImageString;

    private String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTakePicture = (Button) findViewById(R.id.btnTakePicture);
        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. 이미 카메라 앱이 있으니 호출.(우선 유무확인);
                if ( isExistsCameraApplication()) {
                    // 2. 있으면, Camera Application을 실행.
                    Intent cameraApp = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    // 3. 찍은 사진을 보관할 파일 객체를 만들어서 보낸다.
                    File picture = savePictureFile();
                    if( picture != null ) {
                        cameraApp.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picture));
                        startActivityForResult(cameraApp, 10000);
                    }
                }


            }
        });

        ivPicture = (ImageView) findViewById(R.id.ivPicture);

        btnSendPicture = (Button) findViewById(R.id.btnSendPicture);
        btnSendPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Img를 base64 타입 암호화진행 - 원래 이미지 용량과 같다.
                Base64Utility base64Utility = new Base64Utility();
                encodedImageString = base64Utility.encodeJPEG(imgPath);

                Log.d("Result", encodedImageString);
                // 인터넷 접근 권한 얻기

                PermissionRequester.Builder requester = new PermissionRequester.Builder(MainActivity.this);
                int result = requester
                        .create()
                        .request(Manifest.permission.INTERNET, 30000, new PermissionRequester.OnClickDenyButtonListener() {
                    @Override
                    public void onClick(Activity activity) {
                        Toast.makeText(MainActivity.this, "사진보내기 취소", Toast.LENGTH_SHORT).show();
                    }
                });
                if ( result == PermissionRequester.ALREADY_GRANTED || result == PermissionRequester.NOT_SUPPORT_VERSION) {
                    //사진보내기.
                    SendPicutureTask sendPicutureTask = new SendPicutureTask();
                    sendPicutureTask.execute(encodedImageString);
                }

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if( requestCode == 30000 && permissions[0].equals(Manifest.permission.INTERNET) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //사진을 보낸다.
            SendPicutureTask sendPicutureTask = new SendPicutureTask();
            sendPicutureTask.execute(encodedImageString);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ( requestCode == 10000 && resultCode == RESULT_OK) {
            // 사진을 IMG_VIEW로 보여준다.

            BitmapFactory.Options factory = new BitmapFactory.Options();
//            factory.inJustDecodeBounds = true;
//
//            BitmapFactory.decodeFile(imgPath);

            factory.inJustDecodeBounds = false;
            factory.inPurgeable = true;

            Bitmap bitmap = BitmapFactory.decodeFile(imgPath, factory);
            ivPicture.setImageBitmap(bitmap);




        }
    }


    /**
     * 안드로이드 내 카메라 어플리케이션 유무 확인.
     * @return
     */
    private boolean isExistsCameraApplication() {

        /**
         * Android 모든 Application 가져온다.
         */
        PackageManager packageManager = getPackageManager();
        //Camera Application.
        Intent cameraApp = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // MediaStore.ACTION_IMAGE_CAPTURE Intent를 처리 할 수 있는 어플리케이션 정보를 가져온다.
        List<ResolveInfo> cameraApps = packageManager.queryIntentActivities(cameraApp, PackageManager.MATCH_DEFAULT_ONLY);

        return cameraApps.size() > 0;
    }

    /**
     * 카메라에서 찍은 사진을 외부 저장소에 저장한다.
     * @return
     */
    private File savePictureFile() {
        PermissionRequester.Builder requester = new PermissionRequester.Builder(this);

        int result = requester
                    .create()
                    .request(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            20000,
                            new PermissionRequester.OnClickDenyButtonListener() {
                                @Override
                                public void onClick(Activity activity) {

                                }
                            }
                    );

        // 권한 수락시
        if( result == PermissionRequester.ALREADY_GRANTED || result == PermissionRequester.REQUEST_PERMISSION) {

            // 사진 파일의 이름을 만든다.
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = "IMG_" + timeStamp;

            // 사진 파일 저장될 장소(사진을 저장할 폴더 및에 폴더를 생성해서 사진을 저장하겠다.)
            File pictureStorage = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MYAPP/");
            if ( !pictureStorage.exists()) {
                // dir은 하나의 파일만 생성해주고, dirs는 경로에 없는 파일들을 하나하나 전부 생성해준다.
                pictureStorage.mkdirs();
            }

            try {
                File file = File.createTempFile(fileName, ".jpg", pictureStorage);

                // 사진 파일의 절대 경로를 얻어온다.
                // 나중에 ImageView에 보여줄 때 필요하다.
                imgPath = file.getAbsolutePath();

                // 찍힌 사진을 "갤러리" 앱에 추가한다.
                Intent mediaScanIntent = new Intent( Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                File f = new File(imgPath);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);

                return file;

            } catch (IOException e) {
                e.getMessage();
            }

        }

        return null;
    }

    private class SendPicutureTask extends AsyncTask<String, Integer, Void> {
        @Override
        protected Void doInBackground(String... params) {
            HttpClient.Builder http = new HttpClient.Builder(
                    "POST", "http://10.225.152.172:8080/SpringSimpleBoard/sendPicture"
            );
            //파라미터 추가.
            http.addOrReplaceParameter("image", params[0]);
            HttpClient post = http.create();
            post.request();

            int statusCode =post.getHttpStatusCode();
            Log.d("Result", statusCode + "");

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(MainActivity.this, "사진전송 완료", Toast.LENGTH_SHORT).show();
        }
    }


}
