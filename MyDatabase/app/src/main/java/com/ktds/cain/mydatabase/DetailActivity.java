package com.ktds.cain.mydatabase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ktds.cain.mydatabase.DB.DBHelper;
import com.ktds.cain.mydatabase.VO.Person;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private ListView lvPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        int personId = Integer.parseInt(intent.getStringExtra("personId"));

        if ( dbHelper == null ) {
            dbHelper = new DBHelper(DetailActivity.this, "TEST", null, DBHelper.DB_VERSION);
        }
        List<Person> persons = dbHelper.getPersonInfo(personId);

        lvPerson.setAdapter(new PersonListAdapter(persons, DetailActivity.this));

    }

    private class PersonListAdapter extends BaseAdapter {

        private List<Person> persons;
        private Context context;

        public PersonListAdapter(List<Person> persons, Context context) {
            this.persons = persons;
            this.context = context;
        }
        @Override
        public int getCount() {
            return persons.size();
        }

        @Override
        public Object getItem(int position) {
            return this.persons.get(position);
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

                TextView tvName = new TextView(context);
                tvName.setPadding(10,10,10,10);

                TextView tvAge = new TextView(context);
                tvName.setPadding(10,10,10,10);

                TextView tvPhone = new TextView(context);
                tvName.setPadding(10,10,10,10);

                TextView tvAddress = new TextView(context);
                tvName.setPadding(10,10,10,10);


                ((LinearLayout) convertView).addView(tvId);
                ((LinearLayout) convertView).addView(tvName);
                ((LinearLayout) convertView).addView(tvAge);
                ((LinearLayout) convertView).addView(tvPhone);
                ((LinearLayout) convertView).addView(tvAddress);

                holder = new Holder();
                holder.tvId = tvId;
                holder.tvName = tvName;
                holder.tvName = tvAge;
                holder.tvName = tvPhone;
                holder.tvName = tvAddress;

                convertView.setTag(holder);

            } else {
                holder = (Holder) convertView.getTag();
            }
            Person person = (Person) getItem(position);
            holder.tvId.setText(person.get_id() + "");

            holder.tvName.setText(person.getName());

            return convertView;
        }
    }

    private class Holder {
        public TextView tvId;
        private TextView tvName;
        private TextView tvAge;
        private TextView tvPhone;
        private TextView tvAddress;

    }

}
