package com.ktds.cain.mydatabase.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.ktds.cain.mydatabase.VO.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 206-013 on 2016-06-20.
 */
public class DBHelper extends SQLiteOpenHelper{

    public static final int DB_VERSION = 2;

    private Context context;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;

    }

    /**
     * If not definition DataBase, action one time.
     * DB create duty
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer sb = new StringBuffer();
        sb.append(" CREATE TABLE TEST_TABLE( ");
        sb.append(" _ID INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sb.append(" NAME TEXT, ");
        sb.append(" AGE INTEGER, ");
        sb.append(" PHONE TEXT ) ");

        // sql 실행
        db.execSQL(sb.toString());
        Toast.makeText(context, "Table Created", Toast.LENGTH_SHORT).show();
    }

    /**
     * Application version upgrade or Table Construct is modified action.
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if( oldVersion == 1 && newVersion == 2 ) {
            StringBuffer sb = new StringBuffer();
            sb.append(" ALTER TABLE TEST_TABLE ADD ADDRESS TEXT ");

            db.execSQL(sb.toString());
        }
//        } else if ( oldVersion == 1 && newVersion == 3 ){
//
//        }
        Toast.makeText(context, "Version 올라감..", Toast.LENGTH_SHORT).show();
    }

    // previously tests
    public void testDB() {
        SQLiteDatabase db = getReadableDatabase();
    }

    public void addPerson(Person person) {
        //1. write DB instance brief.
        SQLiteDatabase db = getWritableDatabase();

        //2. person data insert.
        StringBuffer sb = new StringBuffer();

        sb.append(" INSERT INTO TEST_TABLE ( ");
        sb.append(" NAME, AGE, PHONE, ADDRESS ) ");
        // ?형태로 집어넣기.
        sb.append(" VALUES (?,?,?,?)");
        db.execSQL(sb.toString(), new Object[] { person.getName(), Integer.parseInt(person.getAge()), person.getPhone(), person.getAddress() });

        // #형태로 집어넣기.
//        sb.append(" VALUES ( #NAME#, #AGE#, #PHONE#");
//
//        String query = sb.toString();
//        query = query.replace("#NAME#", "'" + person.getName() + "'");
//        query = query.replace("#AGE#", person.getAge());
//        query = query.replace("#PHONE#", "'" + person.getPhone() + "'");

//        db.execSQL(query);

        Toast.makeText(context, "Inserts", Toast.LENGTH_SHORT).show();
    }

    public List<Person> getAllPersons() {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT _ID, NAME, AGE, PHONE FROM TEST_TABLE");

        // read only DB instance .
        SQLiteDatabase db = getReadableDatabase();

        // Selecting
        Cursor cursor = db.rawQuery(sb.toString(), null);

        List<Person> persons = new ArrayList<Person>();

        Log.d("Results", cursor.getColumnCount()+"");

        Person person = null;
        while ( cursor.moveToNext() ) {
            person = new Person();
            person.set_id(cursor.getInt(0));
            person.setName(cursor.getString(1));
            persons.add(person);
        }
        return persons;
    }

    public List<Person> getPersonInfo(int id) {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT _ID, NAME, AGE, PHONE FROM TEST_TABLE WHERE " + id);

        // read only DB instance .
        SQLiteDatabase db = getReadableDatabase();

        // Selecting
        Cursor cursor = db.rawQuery(sb.toString(), null);

        List<Person> persons = new ArrayList<Person>();

        Log.d("Results", cursor.getColumnCount()+"");

        Person person = null;
        while ( cursor.moveToNext() ) {
            person = new Person();
            person.set_id(cursor.getInt(0));
            person.setName(cursor.getString(1));
            person.setAge(cursor.getString(2));
            person.setPhone(cursor.getString(3));
            person.setAddress(cursor.getString(4));
            persons.add(person);
        }
        return persons;
    }

}
