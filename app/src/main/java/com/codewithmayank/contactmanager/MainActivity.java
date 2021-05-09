package com.codewithmayank.contactmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.codewithmayank.contactmanager.data.DatabaseHandler;
import com.codewithmayank.contactmanager.model.Contact;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler db = new DatabaseHandler(MainActivity.this);
        //Create Contact Object First
        Contact ram = new Contact();
        ram.setName("Ram");
        ram.setPhoneNumber("8566432900");
        db.addContact(ram);

        Contact shyam = new Contact();
        shyam.setName("Shyam");
        shyam.setPhoneNumber("9566432900");
        db.addContact(shyam);

        Contact get_one = db.getContact(3);
        Log.d("One",get_one.getName());
        get_one.setName("Suresh");
        get_one.setPhoneNumber("1234567890");
        int updated_row=db.updateContact(get_one);
        Log.d("Updated Row", String.valueOf(updated_row));

       // db.deleteContact(get_one);

        List <Contact> list = db.getAllContacts();
        for (Contact contact:list) {
            Log.d("ROW ID " + contact.getId(), contact.getName() + "-->" + contact.getPhoneNumber());
        }
        Log.d("GET_COUNT_DB", String.valueOf(db.getCount()));

    }
}