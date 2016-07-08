package com.ktds.cain.myapplication.utility;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ktds.cain.myapplication.memo.vo.Memo;

import java.util.List;

/**
 * Created by 206-013 on 2016-06-20.
 */
public class ArticleListAdapter extends BaseAdapter {
    private List<Memo> memos;
    private Context context;

    public ArticleListAdapter(List<Memo> memos, Context context) {
        this.memos = memos;
        this.context = context;
    }
    @Override
    public int getCount() {
        return memos.size();
    }

    @Override
    public Object getItem(int position) {
        return this.memos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder = null;

        if ( convertView == null ) {
            convertView = new LinearLayout(context);
            ((LinearLayout) convertView).setOrientation(LinearLayout.HORIZONTAL);

            TextView tvId = new TextView(context);
            tvId.setPadding(10,10,10,10);
            TextView tvSubject = new TextView(context);
            tvSubject.setPadding(10,10,10,10);


            ((LinearLayout) convertView).addView(tvId);
            ((LinearLayout) convertView).addView(tvSubject);

            holder = new Holder();
            holder.tvId = tvId;
            holder.tvSubject = tvSubject;

            convertView.setTag(holder);

        } else {
            holder = (Holder) convertView.getTag();
        }
        Memo memo = (Memo) getItem(position);
        holder.tvId.setText(memo.get_id() + "");

        holder.tvSubject.setText(memo.getSubject());

        return convertView;
    }

    private class Holder {
        public TextView tvId;
        public TextView tvSubject;
        public TextView tvContents;
        public TextView tvCreateDate;
        public TextView tvModifyDate;
        public TextView tvEndDate;
    }

}

