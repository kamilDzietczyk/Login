package com.example.loginpanelwithdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "usersdb";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "user";
    private static final String ID_COL = "id";
    private static final String LOGIN_COL = "login";
    private static final String PASSWORD_COL = "password";
    private static final String ISADMIN_COL = "admin";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + LOGIN_COL + " TEXT,"
                + PASSWORD_COL + " TEXT,"
                + ISADMIN_COL + " BOOLEAN)";
        db.execSQL(query);
        //JASZCZUR/Klapaucius/Koozakk
        db.execSQL("INSERT INTO " + TABLE_NAME+ "(login, password, admin) VALUES ('Kamil','7f069ee232e5a785072f69535710530818c24c20', false)");
        db.execSQL("INSERT INTO " + TABLE_NAME+ "(login, password, admin) VALUES ('Aneta','48e6c1047535448535cd9395cc1bc43df5175f0d', false)");
        db.execSQL("INSERT INTO " + TABLE_NAME+ "(login, password, admin) VALUES ('Małgorzata','438b9f9c7e6169d2f42ecbf611966dbd65206a7', true)");
    }

    public void addNewCourse(String userLogin, String user_Password, Boolean userAdmin) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(LOGIN_COL, userLogin);
        values.put(PASSWORD_COL, user_Password);
        values.put(ISADMIN_COL, userAdmin);

        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    public ArrayList<UserModel> readWhere(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorCourses = db.rawQuery("SELECT * FROM user WHERE login = ? AND password = ?", new String[] {username, SHA.encryptThisString(password)});

        ArrayList<UserModel> courseModalArrayList = new ArrayList<>();

        if (cursorCourses.moveToFirst()) {
            do {
                courseModalArrayList.add(new UserModel(cursorCourses.getString(1),
                        cursorCourses.getString(2),
                        cursorCourses.getString(3)));
            } while (cursorCourses.moveToNext());
        }
        cursorCourses.close();
        return courseModalArrayList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
