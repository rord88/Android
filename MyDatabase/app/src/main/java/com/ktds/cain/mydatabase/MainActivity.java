package com.ktds.cain.mydatabase;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ktds.cain.mydatabase.DB.DBHelper;
import com.ktds.cain.mydatabase.VO.Person;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private Button btnCreateDatabase;
    private Button btnInsertData;
    private Button btnSelectAllDatas;
    private ListView lvPersons;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreateDatabase = (Button) findViewById(R.id.btnCreateDatabase);
        btnCreateDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvPersons.setVisibility(View.INVISIBLE);

                final EditText etDBName = new EditText(MainActivity.this);
                etDBName.setHint("DB명을 입력하세요.");

                // Dialog로 database의 입력 받기.
                AlertDialog.Builder diBuilder = new AlertDialog.Builder(MainActivity.this);
                diBuilder.setTitle("Database 이름을 입력하세요.")
                         .setMessage("Database 이름을 입력바람.")
                         .setView(etDBName)
                         .setPositiveButton("생성", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 if(etDBName.getText().toString().length() > 0 ) {
                                     dbHelper = new DBHelper(MainActivity.this, etDBName.getText().toString(), null, DBHelper.DB_VERSION);
                                     dbHelper.testDB();
                                 }
                                 //Toast.makeText(MainActivity.this, tvDBName.getText(), Toast.LENGTH_SHORT).show();
                             }
                         })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create().show();

            }
        });
        btnInsertData = (Button) findViewById(R.id.btnInsertData);
        btnInsertData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvPersons.setVisibility(View.INVISIBLE);

                LinearLayout layout = new LinearLayout(MainActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText etName = new EditText(MainActivity.this);
                etName.setHint("write your name.");

                final EditText etAge = new EditText(MainActivity.this);
                etAge.setHint("write your age.");

                final EditText etPhone = new EditText(MainActivity.this);
                etPhone.setHint("write your phoneNumber.");

                final EditText etAddress = new EditText(MainActivity.this);
                etPhone.setHint("write your address.");

                layout.addView(etName);
                layout.addView(etAge);
                layout.addView(etPhone);
                layout.addView(etAddress);

                AlertDialog.Builder diBuilder = new AlertDialog.Builder(MainActivity.this);
                diBuilder.setTitle("정보입력")
                         .setView(layout)
                         .setPositiveButton("등록", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 String name = etName.getText().toString();
                                 String age = etAge.getText().toString();
                                 String phone = etPhone.getText().toString();
                                 String address = etAddress.getText().toString();

                                 if ( dbHelper == null ) {
                                     dbHelper = new DBHelper(MainActivity.this, "Test", null, DBHelper.DB_VERSION);
                                 }

                                 Person person = new Person();
                                 person.setName(name);
                                 person.setAge(age);
                                 person.setPhone(phone);
                                 person.setAddress(address);

                                 dbHelper.addPerson(person);

                                // Toast.makeText(MainActivity.this, name + " / " + age + " / " + phone, Toast.LENGTH_SHORT).show();

                             }
                         })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create().show();
            }
        });

        lvPersons = (ListView) findViewById(R.id.lvPersons);
        lvPersons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String personId = String.valueOf(((Person) lvPersons.getAdapter().getItem(position)).get_id());
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("personId", personId);
                startActivity(intent);
            }
        });

        btnSelectAllDatas = (Button) findViewById(R.id.btnSelectAllDatas);
        btnSelectAllDatas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 클릭시, List 확인가능.
                lvPersons.setVisibility(View.VISIBLE);
                //1. Person data all brief
                if ( dbHelper == null ) {
                    dbHelper = new DBHelper(MainActivity.this, "TEST", null, DBHelper.DB_VERSION);
                }
                List<Person> persons = dbHelper.getAllPersons();

                //2. ListView
                lvPersons.setAdapter(new PersonListAdapter(persons, MainActivity.this));
            }
        });
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


                ((LinearLayout) convertView).addView(tvId);
                ((LinearLayout) convertView).addView(tvName);

                holder = new Holder();
                holder.tvId = tvId;
                holder.tvName = tvName;

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
        public TextView tvName;
    }


}
