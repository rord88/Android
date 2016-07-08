package com.ktds.cain.myapplication.memo.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.ktds.cain.myapplication.memo.vo.Memo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 206-013 on 2016-06-20.
 */
public class DBHelper extends SQLiteOpenHelper {

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
        sb.append(" CREATE TABLE MEMO( ");
        sb.append(" _ID INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sb.append(" SUBJECT TEXT, ");
        sb.append(" CONTENTS TEXT, ");
        sb.append(" CREATE_DATE TEXT, ");
        sb.append(" END_DATE TEXT, ");
        sb.append(" MODIFY_DATE TEXT ) ");

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
            sb.append(" ALTER TABLE MEMO ADD MODIFY_DATE TEXT ");

            db.execSQL(sb.toString(), new Object[] {});
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

    public void addPerson(Memo memo) {
        //1. write DB instance brief.
        SQLiteDatabase db = getWritableDatabase();

        //2. person data insert.
        StringBuffer sb = new StringBuffer();

        sb.append(" INSERT INTO MEMO ( ");
        sb.append(" SUBJECT, CONTENTS, CREATE_DATE, END_DATE, MODIFY_DATE ) ");
        // ?형태로 집어넣기.
        sb.append(" VALUES (?,?,?,?,?)");
        db.execSQL(sb.toString(), new Object[] { memo.getSubject(), memo.getContents(), memo.getCreateDate(), memo.getEndDate(), memo.getModifyDate() });

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

    public List<Memo> getAllArticles() {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT _ID, SUBJECT, CONTENTS, CREATE_DATE, END_DATE, MODIFY_DATE FROM MEMO ");

        // read only DB instance .
        SQLiteDatabase db = getReadableDatabase();

        // Selecting
        Cursor cursor = db.rawQuery(sb.toString(), null);

        List<Memo> memos = new ArrayList<Memo>();

        Log.d("Results", cursor.getColumnCount()+"");

        Memo memo = null;
        while ( cursor.moveToNext() ) {
            memo = new Memo();
            memo.set_id(cursor.getInt(0));
            memo.setSubject(cursor.getString(1));
            memos.add(memo);
        }
        return memos;
    }

//    public List<Memo> getPersonInfo(int id) {
//        StringBuffer sb = new StringBuffer();
//        sb.append(" SELECT _ID, SUBJECT, CONTENTS, CREATE_DATE, END_DATE, MODIFY_DATE FROM MEMO WHERE " + id);
//
//        // read only DB instance .
//        SQLiteDatabase db = getReadableDatabase();
//
//        // Selecting
//        Cursor cursor = db.rawQuery(sb.toString(), null);
//
//        List<Memo> memos = new ArrayList<Memo>();
//
//        Log.d("Results", cursor.getColumnCount()+"");
//
//        Memo memo = null;
//        while ( cursor.moveToNext() ) {
//            memo = new Memo();
//            memo.set_id(cursor.getInt(0));
//            memo.setSubject(cursor.getString(1));
//            memo.setContents(cursor.getString(2));
//            memos.add(memo);
//        }
//        return memos;
//    }
}
