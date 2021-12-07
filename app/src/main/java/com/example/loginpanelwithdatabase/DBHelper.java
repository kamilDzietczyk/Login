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
                + ISADMIN_COL + " TEXT)";
        db.execSQL(query);
        //JASZCZUR/Klapaucius/Koozakk
        db.execSQL("INSERT INTO " + TABLE_NAME+ "(login, password, admin) VALUES ('Kamil','7f069ee232e5a785072f69535710530818c24c20', 'true')");
    }
    public void deleteUser(String userName) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, "login=?", new String[]{userName});
        db.close();
    }

    public void updatePassword(String originStrringName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PASSWORD_COL, "secure");

        db.update(TABLE_NAME, values,"login=?",new String[]{originStrringName});
        db.close();
    }

    public void updateNewUser(String originStrringName,String userLogin, String user_Password, String userAdmin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LOGIN_COL, userLogin);
        values.put(PASSWORD_COL, user_Password);
        values.put(ISADMIN_COL, userAdmin);

        db.update(TABLE_NAME, values,"login=?",new String[]{originStrringName});
        db.close();
    }

    public void addNewUser(String userLogin, String user_Password, String userAdmin) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(LOGIN_COL, userLogin);
        values.put(PASSWORD_COL, user_Password);
        values.put(ISADMIN_COL, userAdmin);

        db.insert(TABLE_NAME, null, values);

        db.close();
    }
    public ArrayList<UserModel> readWhereUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorCourses = db.rawQuery("SELECT * FROM user WHERE login = ? ", new String[] {username});

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
    public ArrayList<UserModel> readAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorCourses = db.rawQuery("SELECT * FROM user ",null);

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
