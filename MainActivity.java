package com.example.nichetech.filterring_oncontact;

import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    ArrayList<Contact> contacts = new ArrayList<>();
    AutoCompleteTextView cont_Search;
    Custom_contact_Adepter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contacts= readContactData1();
        cont_Search=(AutoCompleteTextView)findViewById(R.id.cont_search);

        cont_Search.setThreshold(1);
        adapter = new Custom_contact_Adepter(this,R.layout.activity_main,R.id.tv_contact_name1, contacts);
        cont_Search.setAdapter(adapter);

       /* Iterator<Contact> i=contacts.iterator();

        while (i.hasNext())
        {
            Contact contact=(Contact)i.next();
            Log.e("name",contact.getName());
            Log.e("Mobile",contact.getMobile());
        }*/

    }


    private ArrayList<Contact> readContactData1() {



        String[] projection = {
                ContactsContract.Data.MIMETYPE,
                ContactsContract.Data.CONTACT_ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Contactables.DATA,
                ContactsContract.CommonDataKinds.Contactables.TYPE,
                ContactsContract.PhoneLookup._ID
        };

        String selection = ContactsContract.Data.MIMETYPE + " in (?, ?)";

        String[] selectionArgs = {
                ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
        };

        Uri uri = ContactsContract.Data.CONTENT_URI;

        Cursor cursor = getContentResolver().query(uri, projection, selection, selectionArgs, null);


        final int mimeTypeIdx = cursor.getColumnIndex(ContactsContract.Data.MIMETYPE);
        final int idIdx = cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID);
        final int nameIdx = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        final int dataIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.DATA);
        final int typeIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.TYPE);
        final int phonebook_id = cursor.getColumnIndex(ContactsContract.PhoneLookup._ID);

        Log.d("Contact Count", cursor.getCount() + "");

        while (cursor.moveToNext()) {

            Contact contact = new Contact();

            String id = "";
            String phoneNumber = "";
            String emailId = "";
            String displayName = "";

            String data = cursor.getString(dataIdx);


            String mimeType = cursor.getString(mimeTypeIdx);

            id = cursor.getString(idIdx);

            if (mimeType.equals(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)) {

                emailId = data;
            } else {

                phoneNumber = data;
            }

            displayName = cursor.getString(nameIdx);



            contact.setName(displayName);
            contact.setMobile(phoneNumber);

            contacts.add(contact);


        }


        cursor.close();
        return contacts;

    }



}
