package com.ktds.cain.customlistview.facebook;

import android.content.Context;
import android.util.Log;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.FacebookType;
import com.restfb.types.Post;
import com.restfb.types.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 206-013 on 2016-06-14.
 */
public class Facebook {

    /**
     * 인증과 관련된 상수들
     */
    private static final String APP_ID = "138519489874332";
    private static final String APP_SECRET = "f4a88701077be8ac7887212b1dae46bb";
    private static final String ACCESS_TOKEN = "EAAB9ZB5T9CZAwBAIzFyZAqIVaTL02ZACk6bBX061ASBHHXOQ1oDoY4350RODisoBBSnVUEdbbaygpkNUiuC4mOZB9K1iAcAlxnEo1iOFw8PUvKpIDflJTivomORQEOnSRl3u3YniZAZAujj3rhKTR3c4f7TzHZColcqNtw5tG0kj3wZDZD";

//    private Context context;
//    //webView/Flagment
//
//    /**
//     * Faccebook 인증 객체
//     */
//    private FacebookClient myFaceBook;
//    private boolean isLogin;
//
//    public Facebook(Context context) {
//        this.context = context;
//    }
//
//    /**
//     * Facebook으로 로그인 가능
//     */
//    public void auth() {
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                myFaceBook = new DefaultFacebookClient(ACCESS_TOKEN, Version.LATEST);
//                //로그인이 성공했는지 체크.
//                User me = myFaceBook.fetchObject("me", User.class);
//                Log.d("FACEBOOK", me.getName() + "로긴");
//
//                isLogin = me != null;
//                if ( isLogin) {
//                    ((MainActivity)context).setTimeLine();
//                }
//            }
//        }).start();
//    }
//
//    public boolean isLogin() {
//        return isLogin;
//    }
//
//    public  void getTimeLine(final TimelineSerializable timelineSerializable) {
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                // 나의 타임라인에서 포스트들을 가져오기
//                Connection<Post> feeds = myFaceBook.fetchConnection("me/feed", Post.class);
//
//                List<Post> postList = new ArrayList<Post>();
//                // 타임라인 정보들
//                for ( List<Post> posts: feeds) {
//                    postList.addAll(posts);
//                    //타임라인에 등록되어 있는 포스트 1건
////                    for(Post post : posts) {
////                        if(post.getStory() != null) {
////                            Log.d("FaceBook", post.getStory());
////                        }
////
////                        if(post.getName() != null) {
////                            Log.d("FaceBook", post.getName());
////                        }
////                        if(post.getMessage() != null) {
////                            Log.d("FaceBook", post.getMessage());
////                        }
////                        if(post.getCreatedTime() != null) {
////                            Log.d("FaceBook", post.getCreatedTime()+"");
////                        }
////                    }
//                }
//                timelineSerializable.serialize(postList);
//            }
//        }).start();
//    }
//
//    public interface TimelineSerializable {
//        public void serialize(List<Post> posts);
//
//    }
//
//}


    private Context context;

    /**
     *  Facebook 인증 객체
     */
    private FacebookClient myFacebook;

    private boolean isLogin;

    public Facebook(Context context) {
        this.context = context;
    }

    /**
     *  Facebook 으로 로그인 한다.
     * @return : 로그인 성공시 true
     */
    public void auth(final After after) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                // Facebook Login
                myFacebook = new DefaultFacebookClient(ACCESS_TOKEN, Version.LATEST);

                // 로그인 성공 여부 체크
                // 로그인된 계정의 정보를 가져온다.
                User me = myFacebook.fetchObject("me", User.class);

                Log.d("FACEBOOK", me.getName() + " 계정으로 로그인 함.");

                isLogin = me != null;
            if( isLogin) {
                after.doAfter(context);
            }
//                if ( isLogin ) {
//                    if( context instanceof  MainActivity) {
//                        ((MainActivity)context).setTimeline();
//                    } else if (context instanceof  WritePostActivity) {
//                        ((WritePostActivity)context).writePost();
//                    }
//                }
            }
        }).start();

    }

    public boolean isLogin() {
        return isLogin;
    }

    public void getTimeline( final TimelineSerializable timelineSerializable ) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("Result", "NotNull");
                // 나의 타임라인에서 포스트들을 가져온다.
                Connection<Post> feeds = myFacebook.fetchConnection("me/feed", Post.class, Parameter.with("fields", "from,likes,message,story,link"));
                List<Post> postList = new ArrayList<Post>();
                // 타임라인 정보들...
                for (List<Post> posts : feeds) {

                    Log.d("Result", posts.size() + "");
                    postList.addAll(posts);
                }
                timelineSerializable.serialize(postList);
            }
        }).start();

    }

    public void getDetail(final String postId, final GetDetailInformation getDetailInformation) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Post post = myFacebook.fetchObject(postId, Post.class, Parameter.with("fields", "id,from,likes,message,created_time,comments"));
                getDetailInformation.details(post);
            }
        }).start();
    }

    public void publishing(final String message, final After after) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                FacebookType facebookType = myFacebook.publish("me/feed", FacebookType.class, Parameter.with("message", message));
                after.doAfter(context);
            }
        }).start();
    }

    public interface TimelineSerializable {
        public void serialize(List<Post> posts);
    }

    public interface GetDetailInformation {
        public void details(Post post);
    }

    public interface After {
        public void doAfter(Context context);
    }

}