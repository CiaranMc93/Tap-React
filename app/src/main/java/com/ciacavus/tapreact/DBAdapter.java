package com.ciacavus.tapreact;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by ciaran on 13/06/2016.
 */

public class DBAdapter {

    private static final String ID = "Id";

    private static final String KEY_NAME = "name";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_SCORE = "score";
    private static final String KEY_COUNTER = "success_counter";
    private static final String KEY_MINUTES = "minutes";
    private static final String KEY_SECONDS = "seconds";
    private static final String USER_SCORE = "user_score";
    private static final String USER_TABLE = "users";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "TapReact";
    private static final String TAG = "DBAdapter";
    //table to hold all of the scores regardless of whether the user is logged in or not
    private static final String GAME_DB = "CREATE TABLE user_score (ID integer primary key AUTOINCREMENT, " +
            "name varchar(255) not null, score integer not null, success_counter integer);";

    //user holds information relating to the user as well as their stats
    private static final String USER = "CREATE TABLE users (name varchar(255) not null, password varchar(255) not null," +
            "score integer not null, success_counter integer, minutes integer, seconds integer);";

    //database variables
    private Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) {
        this.context = ctx;

        DBHelper = new DatabaseHelper(context);
    }

    //open the database
    public DBAdapter open() throws SQLException {

        try{
            //create or open the database before any logic is performed
            db = SQLiteDatabase.openOrCreateDatabase("TapReact", null);

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
    public long insertInfo(String name, int score,int successCounter) {

        ContentValues initial = new ContentValues();

        //add a new row into the database with name,score and successCounter
        initial.put(KEY_NAME,name);
        initial.put(KEY_SCORE,score);
        initial.put(KEY_COUNTER,successCounter);

        //insert into DB
        return db.insert(USER_SCORE,null,initial);
    }

    //method to close the database
    public void close() {
        //close the db
        DBHelper.close();
    }

    //retrieve all contacts
    public Cursor getAllInfo()
    {
        //return a cursor query
        return db.query(USER_SCORE, new String[] {ID, KEY_NAME, KEY_SCORE, KEY_COUNTER},null,null,null,null,KEY_SCORE + " DESC");
    }

    public int updateInfo(long rowId, String name, int score)
    {
        //create the argument variable
        ContentValues args = new ContentValues();

        //put arguments in the variable to be used in the query
        args.put(KEY_NAME,name);
        args.put(KEY_SCORE,score);

        //return
        return db.update(USER_SCORE, args, ID + "=" + rowId,null);

    }

    public int registerUser(String userName,String password)
    {
        ContentValues initial = new ContentValues();

        //add a new row into the database with name,score and successCounter
        initial.put(KEY_NAME,userName);
        initial.put(KEY_PASSWORD,password);

        //insert into DB
        return (int) db.insert(USER_TABLE,null,initial);
    }

    public boolean loginUser(String userName,String password)
    {
        Cursor getUser;

        getUser = db.rawQuery("SELECT * FROM " + USER_TABLE + " WHERE " + KEY_NAME + " =? " + " AND " + KEY_PASSWORD + " =? ",
                new String[]{userName,password});

        //check if the cursor has a table row (select was successful)
        if(getUser.getCount() > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public class DatabaseHelper extends SQLiteOpenHelper
    {

        public DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            //try to execute the query
            try{
                //execute SQL query
                db.execSQL(GAME_DB);
                //execute SQL query 2
                db.execSQL(USER);
            }catch (SQLException e)
            {
                e.printStackTrace();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//            Log.w(TAG, "Upgrading database from version" + oldVersion
//                    + " to " + newVersion + " will destroy all old data");
//            db.execSQL("DROP TABLE IF EXISTS students");
//
//            onCreate(db);
        }

    }

}
