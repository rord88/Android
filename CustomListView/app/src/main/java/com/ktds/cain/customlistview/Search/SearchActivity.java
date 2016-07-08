package com.ktds.cain.customlistview.Search;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ktds.cain.customlistview.R;
import com.ktds.cain.customlistview.facebook.DetailActivity;
import com.ktds.cain.customlistview.facebook.Facebook;
import com.restfb.types.Post;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends ActionBarActivity {
    private final int SEPARATE = 100;
    private ListView lvArticleList;
    private Facebook facebook;

    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        handler = new Handler();
        lvArticleList = (ListView) findViewById(R.id.lvArticleList);

        lvArticleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String postId = ((Post) lvArticleList.getAdapter().getItem(position)).getId();
                Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
                intent.putExtra("postId", postId );
                startActivity(intent);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        Intent intent = getIntent();
        final String query = intent.getStringExtra("query");
        setTitle("검색어" + query);

        facebook = new Facebook(this);
        facebook.auth(new Facebook.After() {
            @Override
            public void doAfter(Context context) {
                setTimeline(query);
            }
        });


    }

    public void setTimeline(final String query) {
        if ( facebook.isLogin() ) {
            // .... timeline 가져오기
            facebook.getTimeline(new Facebook.TimelineSerializable() {
                @Override
                public void serialize(final List<Post> posts) {
                    //검색속도를 대비한 코딩
                    int postSize = posts.size();
                    int threadCount = 0;
                    if(postSize > SEPARATE) {
                        threadCount = Math.round(postSize / SEPARATE);
                    }
                    threadCount++;

                    final List<Post> searchPost = new ArrayList<Post>();

                    final  BaseAdapter baseAdapter = new ArticleListViewAdapter(SearchActivity.this, searchPost);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            lvArticleList.setAdapter(baseAdapter);
                        }
                    });

                    for ( int i = 0; i< threadCount; i++) {
                        final  int startIndex = i * SEPARATE;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 검색어와 관련이 없는 메세지 제외
                                Post post = null;
                                for( int j = startIndex; j < (startIndex + SEPARATE); j++) {
                                    try {
                                        post = posts.get(j);
                                    } catch ( RuntimeException re) {
                                        break;
                                    }

                                    if(post.getMessage() != null && post.getMessage().length() > 0) {
                                        if( post.getMessage().toLowerCase().contains(query.toLowerCase())) {
                                            searchPost.add(post);
                                            setPost(post, searchPost, baseAdapter);                                        }
                                    } else if(post.getStory() != null && post.getStory().length() > 0) {
                                        if( post.getStory().toLowerCase().contains(query.toLowerCase())) {
                                            searchPost.add(post);
                                            setPost(post, searchPost, baseAdapter);
                                            }
                                    } else if(post.getLink() != null && post.getLink().length() > 0) {
                                        if( post.getLink().toLowerCase().contains(query.toLowerCase())) {
                                            searchPost.add(post);
                                            setPost(post, searchPost, baseAdapter);                                            }
                                        }
                                    }
                                }
                            }).start();
                        }
                    }
                });

            }
        }

    private void setPost(Post post, final List<Post> searchPost, final BaseAdapter baseAdapter) {
        searchPost.add(post);
        handler.post(new Runnable() {
            @Override
            public void run() {
                baseAdapter.notifyDataSetChanged();
            }
        });
    }

    private class ArticleListViewAdapter extends BaseAdapter {

        /**
         *  ListView 에 Item 을 셋팅할 요청자의 객체가 들어감.
         */
        private Context context;

        /**
         *  ListView 에 셋팅할 Item 정보들...
         */
        private List<Post> articleList;

        private Post article;

        public ArticleListViewAdapter(Context context, List<Post> articleList) {
            this.context = context;
            this.articleList = articleList;
        }


        /**
         *
         * 아이템의 속성에 따라서 보여질 Item Layout을 정해준다.
         * @param position
         * @return
         */
        @Override
        public int getItemViewType(int position) {
//            return super.getItemViewType(position);
            /**
             * 만약, Message가 Null이 아니라면 list_item_message를 보여주고
             * 만약, Story가 Null이 아니라면 list_item_story를 보여주고
             * 만약, Link가 Null 이 아니라면 list_item Link를 보여준다.
             */
            article = (Post) getItem(position);
            if (article.getMessage() != null && article.getMessage().length() > 0) {
                return 0;
            } else if (article.getStory() != null && article.getStory().length() > 0) {
                return 1;
            } else if (article.getLink() != null && article.getLink().length() > 0) {
                return 2;
            }

            return 0;
        }

        public int getLayoutType(int index) {
            if ( index == 0) {
                return R.layout.list_item_message;
            } else if ( index == 1) {
                return R.layout.list_item_story;
            } else if ( index == 2) {
                return R.layout.list_item_link;
            }
            return -1;
        }

        /**
         * Item Layout의 개수를 정한다.
         * @return
         */
        @Override
        public int getViewTypeCount() {
            return 3;
        }

        /**
         * ListView 에 셋팅할 Item 들의 개수
         * @return
         */
        @Override
        public int getCount() {
            return this.articleList.size();
        }

        /**
         *  position 번째 Item 정보를 가져옴.
         * @param position
         * @return
         */
        @Override
        public Object getItem(int position) {
            return this.articleList.get(position);
        }

        /**
         *  Item Index 를 가져옴.
         *  Item Index == position
         * @param position
         * @return
         */
        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         *  ListView 에 Item 들을 셋팅함.
         * @param position : 현재 보여질 Item 의 Index, 0부터 getCount() 까지 증가함.
         * @param convertView : ListView 의 Item Cell 객체를 가져옴.
         * @param parent : ListView
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ItemHolder holder = null;
            int layoutType = getItemViewType(position);
            // 가장 효율적인 방법
            if ( convertView == null ) {


                // Item Cell 에 Layout 을 적용시킬 Inflater 객체
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);

                // Item Cell 에 Layout 을 적용시킨다.
                convertView = inflater.inflate(getLayoutType(layoutType), parent, false);

                holder = new ItemHolder();

                if ( layoutType == 0 ) {
                    holder.tvSubject = (TextView) convertView.findViewById(R.id.tvSubject);
                    holder.tvAuthor = (TextView) convertView.findViewById(R.id.tvAuthor);
                    holder.tvHitCount = (TextView) convertView.findViewById(R.id.tvHitCount);
                } else if ( layoutType == 1 ) {
                    holder.tvSubject = (TextView) convertView.findViewById(R.id.tvSubject);
                } else if( layoutType == 2 ) {
                    holder.tvSubject = (TextView) convertView.findViewById(R.id.tvSubject);
                }
                // converView 가 holder 를 같이 데리고 다님.
                convertView.setTag(holder);
            }
            else {
                Log.d("RESULT", "NOT NULL");
                holder = (ItemHolder) convertView.getTag();
            }
            article = (Post) getItem(position);
            if (layoutType == 0 ) {
                holder.tvSubject.setText(article.getMessage());
                holder.tvAuthor.setText(article.getFrom().getName());

                if(article.getLikes() == null) {
                    holder.tvHitCount.setText("0");
                } else {
                    holder.tvHitCount.setText(article.getLikes().getData().size() + "");
                }
            } else if ( layoutType == 1 ) {
                holder.tvSubject.setText(article.getStory());
            } else if ( layoutType == 2 ) {
                holder.tvSubject.setText(article.getLink());
                holder.tvSubject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getLink()));
                        startActivity(intent);
                    }
                });
            }
            return convertView;
        }

    }

    private class ItemHolder {
        public TextView tvSubject;
        public TextView tvAuthor;
        public TextView tvHitCount;
    }


}
