package com.example.mobilecoursework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class viewallexpense extends AppCompatActivity {
    ListView lst1;
    ArrayList<String> titles = new ArrayList<String>();
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewallexpense);

        SQLiteDatabase db = openOrCreateDatabase("SliteDb", Context.MODE_PRIVATE,null);

        lst1 = findViewById(R.id.lst1);
        final Cursor c = db.rawQuery("select * from expenses",null);

        int id = c.getColumnIndex("id");
        int type = c.getColumnIndex("type");
        int cost = c.getColumnIndex("cost");
        int date = c.getColumnIndex("date");
        titles.clear();


        arrayAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,titles);
        lst1.setAdapter(arrayAdapter);

        final  ArrayList<expense> stud = new ArrayList<expense>();


        if(c.moveToFirst())
        {
            do{
                expense stu = new expense();
                stu.id = c.getString(id);
                stu.type = c.getString(type);
                stu.cost = c.getString(cost);
                stu.date = c.getString(date);
                stud.add(stu);

                titles.add(c.getString(id) + " \t " + c.getString(type) + " \t "  + c.getString(cost) + " \t "  + c.getString(date));

            } while(c.moveToNext());
            arrayAdapter.notifyDataSetChanged();
            lst1.invalidateViews();

        }
    }
}