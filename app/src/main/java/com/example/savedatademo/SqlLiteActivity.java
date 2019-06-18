package com.example.savedatademo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class SqlLiteActivity extends AppCompatActivity implements View.OnClickListener {
    private SQLiteDatabase database;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;

    private MySQLiteOpenHelper sqLiteOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql_lite);

        textView1 = findViewById(R.id.add);
        textView1.setOnClickListener(this);

        textView2 = findViewById(R.id.delete);
        textView2.setOnClickListener(this);

        textView3 = findViewById(R.id.update);
        textView3.setOnClickListener(this);

        textView4 = findViewById(R.id.query);
        textView4.setOnClickListener(this);

//        createDataBase();
//        createTable();
        sqLiteOpenHelper = new MySQLiteOpenHelper(this);
        database = sqLiteOpenHelper.getReadableDatabase();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.add:
                addUser(database,"小明",10);
                break;
            case  R.id.delete:
                deleteUSer(database,1);
                break;
            case  R.id.update:
                updateUser(database,1,30);
                break;
            case  R.id.query:
                queryUser(database,1);
                break;
        }
    }

    private void createDataBase() {
        File file = getExternalFilesDir("");
        String path = file.getAbsolutePath() + "/sql.db";
        database = SQLiteDatabase.openOrCreateDatabase(path, null);
    }

    private void createTable() {
        String sql = "create table if not exists user(userID INTEGER PRIMARY KEY autoincrement," +
                "name varchar(30)," +
                "age int" +
                ")";
        database.execSQL(sql);
    }


    private void addUser(SQLiteDatabase database, String name, int age) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("age", age);
        long rowId = database.insert("user", null, values);
        Toast.makeText(this, "插入行号 " + rowId, Toast.LENGTH_LONG).show();
    }

    private void updateUser(SQLiteDatabase database, int userID, int age) {
        ContentValues values = new ContentValues();
        values.put("age", age);
        String[] args = new String[]{userID + ""};
        int result = database.update("user", values, "userID=?", args);
        Toast.makeText(this, "更新Result " + result, Toast.LENGTH_LONG).show();
    }

    private void deleteUSer(SQLiteDatabase database, int userID) {
        String[] args = new String[]{userID + ""};
        int result = database.delete("user", "userID = ?", args);
    }

    private void queryUser(SQLiteDatabase database, int userID) {
        String[] columns = new String[]{"userID", "name", "age"};
//        Cursor cursor = database.query("user", columns, null, null, null, null, null);
        Cursor cursor = database.rawQuery("select * from user",null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("userID"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int age = cursor.getInt(cursor.getColumnIndex("age"));
                Log.e("SQLite", "id: " + id + "  name: " + name + "  age: " + age);
            } while (cursor.moveToNext());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        database.close();
        sqLiteOpenHelper.close();
    }
}




class MySQLiteOpenHelper extends SQLiteOpenHelper{
    MySQLiteOpenHelper(Context context){
        super(context,context.getExternalFilesDir("").getAbsolutePath()+"/sqlHelper.db",null,1);
    }
    private void createTable(SQLiteDatabase db) {
        String sql = "create table if not exists user(userID INTEGER PRIMARY KEY autoincrement," +
                "name varchar(30)," +
                "age int" +
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}






