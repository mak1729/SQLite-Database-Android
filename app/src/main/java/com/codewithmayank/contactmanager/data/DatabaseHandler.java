package com.codewithmayank.contactmanager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.codewithmayank.contactmanager.R;
import com.codewithmayank.contactmanager.model.Contact;
import com.codewithmayank.contactmanager.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME,null,Util.DATABASE_VERSION);
    }

    //We Create our database
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL - Structured Query Language
        /*
        create table_name(id,name,phone_number);
         */
        String CREATE_CONTACT_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "("
                + Util.KEY_ID + " INTEGER PRIMARY KEY," + Util.KEY_NAME + " TEXT,"
                + Util.KEY_PHONE_NUMBER + " TEXT" + ")";
        db.execSQL(CREATE_CONTACT_TABLE); //creating our table

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = String.valueOf(R.string.db_drop);
        db.execSQL(DROP_TABLE, new String[]{Util.DATABASE_NAME});
        //Create a table again
        onCreate(db);
    }

    /*
        CRUD = Create Read Update and Delete
     */

    // Add Contact
    public void addContact(Contact contact)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.KEY_NAME, contact.getName());
        contentValues.put(Util.KEY_PHONE_NUMBER,contact.getPhoneNumber());

        // Insert to row
        db.insert(Util.TABLE_NAME,null,contentValues);
        Log.d("DBHandler", "addContact: " + "item added");
        db.close(); //closing db connection
    }

    //Get Contact
    public Contact getContact(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Util.TABLE_NAME,
                new String[]{ Util.KEY_ID, Util.KEY_NAME, Util.KEY_PHONE_NUMBER},
                Util.KEY_ID +"=?",new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact("Ram", "213986");
        contact.setId(Integer.parseInt(cursor.getString(0)));
        contact.setName(cursor.getString(1));
        contact.setPhoneNumber(cursor.getString(2));
        return contact;
    }

    //Get All Contacts
    public List<Contact> getAllContacts()
    {
        List<Contact> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        //Select All Contacts
        String selectAll = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll,null);

        //Loop through data we are receiving
        if(cursor.moveToFirst())
        {
            do {
                Contact contact =new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                contactList.add(contact);
            }while(cursor.moveToNext());
        }
        return contactList;
    }

    //Update Contact
    public int updateContact(Contact contact)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME,contact.getName());
        values.put(Util.KEY_PHONE_NUMBER,contact.getPhoneNumber());

        //update the row
        //update(tablename, values, where id = 43)
        return db.update(Util.TABLE_NAME, values, Util.KEY_ID + "=?",
                new String[]{String.valueOf(contact.getId())});
    }

    //Delete Contact
    public void deleteContact(Contact contact)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Util.TABLE_NAME,Util.KEY_ID+ "=?",
                new String[]{String.valueOf(contact.getId())}
                );
    }

    // Get Contacts Count
    public int getCount()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String COUNT_QUERY = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor=db.rawQuery(COUNT_QUERY,null);
        return cursor.getCount();
    }
}
