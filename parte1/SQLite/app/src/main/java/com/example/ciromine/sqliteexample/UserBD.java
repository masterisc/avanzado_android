package com.example.ciromine.sqliteexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ciromine on 12-09-15.
 */
public class UserBD extends SQLiteOpenHelper {

    static final String name = "SqliteExample";
    Context context;
    static final int version = 1;

    public UserBD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public UserBD(Context context) {
        super(context, name, null, version);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Users (id INT(11) NOT NULL, name TEXT, PRIMARY KEY (id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void create(int id, String name){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            //Si hemos abierto correctamente la base de datos
            if(db != null)
            {
                ContentValues values = new ContentValues();
                values.put("id", id);
                values.put("name", name);
                db.insert("Users", null, values);
            }
            db.close();
        }catch(SQLiteException e){
            Log.e("Error BDD", e.toString());
        }
    }

    public ArrayList getAllUsers(){
        ArrayList<User> Users = new ArrayList<User>();
        User user = new User();
        SQLiteDatabase db = this.getWritableDatabase();
        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            Cursor c = db.rawQuery("SELECT * FROM Users", null);

            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    user.setId(c.getInt(0));
                    user.setName(c.getString(1));

                    Users.add(user);
                    user = new User();
                } while (c.moveToNext());
            }
        }
        db.close();
        return Users;
    }

    public User getUser(int id){
        User user = new User();
        SQLiteDatabase db = this.getWritableDatabase();
        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            Cursor c = db.rawQuery("SELECT * FROM Users where id="+id, null);

            c.moveToFirst();
            user.setId(c.getInt(0));
            user.setName(c.getString(1));
        }
        db.close();
        return user;
    }

    public void update(int id, String name){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            //Si hemos abierto correctamente la base de datos
            if(db != null)
            {
                ContentValues values = new ContentValues();
                values.put("id", id);
                values.put("name", name);
                db.update("Users", values, "id=" + id, null);
            }
            db.close();
        }catch(SQLiteException e){
            Log.e("Error BDD", e.toString());
        }
    }

    public void destroy(int id) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            //Si hemos abierto correctamente la base de datos
            if (db != null) {
                db.delete("Users", "id=" + id, null);
            }
            db.close();
        } catch (SQLiteException e) {
            Log.e("Error BDD", e.toString());
        }
    }
}
