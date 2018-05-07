package com.newsapp.newsapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class DatabaseManagerUser extends DatabaseManager {

    private static final String FIRSTNAME_TABLE = "demmo";
    private static final String CN_ID = "_id";
    private static final String CN_EMAIL = "EMAIL";
    private static final String CN_PASSWORD = "password";
    private static final String CN_IMAGE = "image";
    private static final String CN_FIRSTNAME = "FIRSTNAME";

    public static final String CREATE_TABLE = "create table " + FIRSTNAME_TABLE + " ("
            + CN_ID + " integer PRIMARY KEY AUTOINCREMENT, "
            + CN_EMAIL + " text NULL, "
            + CN_PASSWORD + " text NULL, "
            + CN_IMAGE + " BLOB NULL, "
            + CN_FIRSTNAME + " text NOT NULL "
            + ");";

    public DatabaseManagerUser(Context ctx) {
        super(ctx);
    }

    @Override
    public void close() {
        super.getDb().close();
    }

    private ContentValues generarContentValues(String id, String EMAIL, String password, byte[] image, String FIRSTNAME){
        ContentValues values = new ContentValues();
        values.put(CN_ID, id);
        values.put(CN_EMAIL, EMAIL);
        values.put(CN_PASSWORD, password);
        values.put(CN_IMAGE, image);
        values.put(CN_FIRSTNAME, FIRSTNAME);

        return values;
    }


    public void insert_parameters (String id, String EMAIL, String password, byte[] image, String FIRSTNAME) {
        Log.d("user_insert", super.getDb().insert(FIRSTNAME_TABLE,null,generarContentValues(id, EMAIL, password, image, FIRSTNAME))+"");
    }

    public void update_parameters(String id, String EMAIL, String password, byte[] image, String FIRSTNAME) {

        ContentValues values = new ContentValues();
        values.put(CN_ID, id);
        values.put(CN_EMAIL, EMAIL);
        values.put(CN_PASSWORD, password);
        values.put(CN_IMAGE, image);
        values.put(CN_FIRSTNAME, FIRSTNAME);


        String[] args = new String[]{id};

        Log.d("to update", super.getDb().update(FIRSTNAME_TABLE, values,"_ID=?", args)+"");
    }

    @Override
    public void remove (String id) {

        super.getDb().delete(FIRSTNAME_TABLE, CN_ID +"=?", new String[]{id});
    }

    @Override
    public void removeAll() {

        super.getDb().execSQL("DELETE FROM "+ FIRSTNAME_TABLE+";");
    }

    @Override
    public Cursor loadCursor() {

        String[] columns = new String[]{CN_ID, CN_EMAIL, CN_PASSWORD, CN_IMAGE, CN_FIRSTNAME};

        return super.getDb().query(FIRSTNAME_TABLE, columns, null, null, null, null, null);
    }

    @Override
    public boolean checkRegister(String EMAIL) {
        boolean one;
        Cursor resultSet = super.getDb().rawQuery("SELECT EMAIL FROM demmo" + " WHERE EMAIL='"+EMAIL+"'", null);
        if(resultSet.getCount()<=0){
            one = false;
        }else{
            one = true;
        }
        return one;
    }

    public List<User> getUsersList(){

        List<User> list = new ArrayList<>();
        Cursor c = loadCursor();

        while (c.moveToNext()){
            User Users = new User();

            Users.setId(c.getString(0));
            Users.setEmail(c.getString(1));
            Users.setPassword(c.getString(2));
            Users.setBytes(c.getBlob(3));
            Users.setFIRSTNAME(c.getString(4));


            list.add(Users);
        }

        return list;
    }

    public User getUser(String ident){

        Cursor c1 = super.getDb().rawQuery("SELECT _id, EMAIL, password, image, FIRSTNAME FROM demmo WHERE EMAIL" + "='" + ident+ "'", null);

        User user = new User();

        c1.moveToNext();

        user.setId(c1.getString(0));
        user.setEmail(c1.getString(1));
        user.setPassword(c1.getString(2));
        user.setBytes(c1.getBlob(3));
        user.setFIRSTNAME(c1.getString(4));
        return user;
    }


}




