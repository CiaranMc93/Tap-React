package com.ciacavus.tapreact;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;


/**
 * Created by ciaran on 13/06/2016.
 */

public class DBAdapter {

    private static final String ID = "Id";

    private static final String KEY_NAME = "name";
    private static final String KEY_SCORE = "score";
    private static final String KEY_COUNTER = "success_counter";
    private static final String KEY_MINUTES = "minutes";
    private static final String KEY_SECONDS = "seconds";
    private static final String DATABASE_TABLE = "user_score";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "DBAdapter";
    private static final String DATABASE = "CREATE TABLE user_score (ID integer primary key AUTOINCREMENT, " +
            "name varchar(255) not null, score integer not null, success_counter integer not null, minutes integer not null, seconds integer not null);";

    //database variables
    private Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) {
        this.context = ctx;

        DBHelper = new DatabaseHelper(context);
    }

    //method to open the database
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public DBAdapter open() throws SQLException {

        try{
            //create or open the database before any logic is performed
            db = SQLiteDatabase.openOrCreateDatabase("StudentDB", null);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        //get the db
        db = DBHelper.getWritableDatabase();
        //return instance of the database
        return this;
    }

    //method to insert contacts
    public long insertInfo(String name, int score) {

        ContentValues initial = new ContentValues();

        initial.put(KEY_NAME,name);
        initial.put(KEY_SCORE,score);

        return db.insert(DATABASE_TABLE,null,initial);
    }

    //method to close the database
    public void close() {
        //close the db
        DBHelper.close();
    }

    //delete specific contact
    public boolean deleteRow(long rowID)
    {
        return db.delete(DATABASE_TABLE, ID + "-" + rowID,null) > 0;
    }

    //retrieve all contacts
    public Cursor getAllScores()
    {
        //return a cursor query
        return db.query(DATABASE_TABLE, new String[] {ID, KEY_NAME, KEY_SCORE},null,null,null,null,null);
    }

    //get specific contact
    public Cursor getUserScore(long rowId) throws SQLException{
        Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {ID, KEY_NAME, KEY_SCORE}, ID + "=" + rowId,null,null,null,null,null);

        if(mCursor != null)
        {
            mCursor.moveToFirst();
        }

        return mCursor;
    }

    public int updateUserScore(long rowId, String name, int score)
    {
        //create the arguement variable
        ContentValues args = new ContentValues();

        //put arguments in the variable to be used in the query
        args.put(KEY_NAME,name);
        args.put(KEY_SCORE,score);

        //return
        return db.update(DATABASE_TABLE, args, ID + "=" + rowId,null);

    }

    public class DatabaseHelper extends SQLiteOpenHelper{

        public DatabaseHelper(Context context) {
            super(context, DATABASE, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            //try to eecute the query
            try{
                //execute SQL query
                db.execSQL(DATABASE);
            }catch (SQLException e)
            {
                e.printStackTrace();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            Log.w(TAG, "Upgrading database from version" + oldVersion
                    + " to " + newVersion + " will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS user_score");

            onCreate(db);
        }

    }

}
