package com.ktds.cain.customlistview;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ktds.cain.customlistview.Search.SearchActivity;
import com.ktds.cain.customlistview.facebook.DetailActivity;
import com.ktds.cain.customlistview.facebook.Facebook;
import com.ktds.cain.customlistview.facebook.WritePostActivity;
import com.restfb.types.Post;

import java.util.List;

public class MainActivity extends ActionBarActivity {

    private ListView lvArticleList;
    private Facebook facebook;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();

        facebook = new Facebook(this);
        facebook.auth(new Facebook.After() {
            @Override
            public void doAfter(Context context) {
                setTimeline();
            }
        });

        lvArticleList = (ListView) findViewById(R.id.lvArticleList);

        lvArticleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String postId = ((Post) lvArticleList.getAdapter().getItem(position)).getId();
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("postId", postId );
                startActivity(intent);
            }
        });
//        final List<ArticleVO> articleList = new ArrayList<ArticleVO>();
//        for (int i =0; i < 300; i++) {
//            articleList.add(new ArticleVO("제목입니다..." + i, "글쓴이입니다.", new Random().nextInt(9999) + ""));
//        }

//        lvArticleList.setAdapter(new ArticleListViewAdapter(this, articleList));
//        lvArticleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this, id + "", Toast.LENGTH_SHORT).show();
//                ArticleVO article = articleList.get(position);
//                Toast.makeText(MainActivity.this, article.getSubject(), Toast.LENGTH_SHORT).show();
//
//                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
// 객체를 통째로 가져와서 사용할 수 있게해준다.
// 메모리와 배터리의 사용이 많기 때문에 메모리를 통째로 보내버려서 사용하는 케이스가 많다.
// 무한루프를 최대한 자제 시키고, 메모리관리를 해야한다. 이에 따른 오픈 클로즈에 신경을 많이 써줘야한다.
//                intent.putExtra("article", article);
//                startActivity(intent);
               // Parcelable 는 사용하기 힘들지만
               // Serializable 는 사용하기 쉽다. 완전히 다른 메모리 구역에서 내가 참조하고 싶은 클래스에서 시리얼라이저블을 붙이게 되면 해당 클래스와 동기화 기키는 것과 같기 때문에
//            }
//        });

    }

    /**
     * Action Bar에 메뉴를 생성한다.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu, menu);

        //검색기능 활성
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        //검색버튼 가져오기
        MenuItem searchButton = menu.findItem(R.id.searchButton);
        // 검색 버튼 클릭했을 때 SearchView를 가져옴.
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchButton);
        // 검색 힌트를 설정
        searchView.setQueryHint("검색어 입력.");
        //SearchView 검색 가능한 위젯으로 설정
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            /**
             * 검색 버튼 클릭시, 동작이벤트
             * @param s
             * @return
             */
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                // s 파라미터는 입력 검색어
                intent.putExtra("query", s);
                startActivity(intent);
                return true;
            }

            /**
             * 검색어 입력시, 동작이벤트
             * @param s
             * @return
             */
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


        return true;
    }

    /**
     * 메뉴 아이템을 클릭 했을 때 발생되는 이벤트
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if ( id == R.id.newPost ) {
            Intent intent = new Intent(this, WritePostActivity.class);
            startActivityForResult(intent, 1000);
            //Toast.makeText(MainActivity.this, "새 글을 등록하는 버튼을 클릭했다.", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1000 && resultCode == RESULT_OK) {
            setTimeline();
        }

    }

    public void setTimeline() {
        if ( facebook.isLogin() ) {
            // .... timeline 가져오기
            facebook.getTimeline(new Facebook.TimelineSerializable() {
                @Override
                public void serialize(final List<Post> posts) {

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            lvArticleList.setAdapter(new ArticleListViewAdapter(MainActivity.this, posts));
                        }
                    });

                }
            });

        }
    }

    //// 안좋은 코딩 방법. 불필요한 메모리를 만들어버리기 때문에 위아래로 와따 가따 할때마다 반복된 수만큼 반복문에서 실행된 갯수의 텍스쳐 개수가 지속적으로 증식되어 만들어져 버리기 때문이다.
//// 이런 방법을 해결하기 위한 방식이 데이를 엎어가는 방식, 태깅을 이용하면 된다.
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

//        // 가장 간단한 방법(안좋은 방식)
//            // 사용자가 처음으로 Flicking 을 할 때, 아래쪽에 만들어지는 Cell 은 null 이다.
//            if ( convertView == null ) {
//
//                // Item Cell 에 Layout 을 적용시킬 Inflater 객체
//                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
//
//                // Item Cell 에 Layout 을 적용시킨다.
//                convertView = inflater.inflate(R.layout.list_item_message, parent, false);
//
//            }
//        //신규로 생성된 아이템만 플리킹 되어 null인 상태이다. - 사용자가 처음으로 Flicking을 할때, 아래쪽에 만들어지는 Cell은 Null이다.
//
//
//        //에러발생
//        // list_item에 tvSubject/tvAuthor/tvHitCount가 들어있고 이를 converView에 객체화시킨 값을 넣었다. 하지만 아래에서 벨류의 정보를 찾더라도 정확하게 어디에 위치한지 모르기 때문에 에러가 발생한다.
////        TextView tvSubject = (TextView) findViewById(R.id.tvSubject);
////        TextView tvAuthor = (TextView) findViewById(R.id.tvAuthor);
////        TextView tvHitCount = (TextView) findViewById(R.id.tvHitCount);
//
//            TextView tvSubject = (TextView) convertView.findViewById(R.id.tvSubject);
//            TextView tvAuthor = (TextView) convertView.findViewById(R.id.tvAuthor);
//            TextView tvHitCount = (TextView) convertView.findViewById(R.id.tvHitCount);
//
//            ArticleVO article = (ArticleVO) getItem(position);
//            tvSubject.setText(article.getSubject());
//            tvAuthor.setText(article.getAuthor());
//            tvHitCount.setText(article.getHitCount());

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