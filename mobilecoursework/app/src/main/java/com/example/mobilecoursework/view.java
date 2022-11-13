package com.example.mobilecoursework;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class view extends AppCompatActivity {


    ListView lst1;
    ArrayList<String> titles = new ArrayList<String>();
    ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        SQLiteDatabase db = openOrCreateDatabase("SliteDb",Context.MODE_PRIVATE,null);

        lst1 = findViewById(R.id.lst1);
        final Cursor c = db.rawQuery("select * from details",null);
        int id = c.getColumnIndex("id");
        int name = c.getColumnIndex("name");
        int destination = c.getColumnIndex("destination");
        int date = c.getColumnIndex("date");
        int require = c.getColumnIndex("require");
        int description = c.getColumnIndex("description");
        titles.clear();


        arrayAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,titles);
        lst1.setAdapter(arrayAdapter);

        final  ArrayList<trip> stud = new ArrayList<trip>();


        if(c.moveToFirst())
        {
            do{
                trip stu = new trip();
                stu.id = c.getString(id);
                stu.name = c.getString(name);
                stu.destination = c.getString(destination);
                stu.date = c.getString(date);
                stu.require = c.getString(require);
                stu.description = c.getString(description);
                stud.add(stu);

                titles.add(c.getString(id) + " \t " + c.getString(name) + " \t "  + c.getString(destination) + " \t "  + c.getString(date) + " \t "  + c.getString(require) + " \t "  + c.getString(description) );

            } while(c.moveToNext());
            arrayAdapter.notifyDataSetChanged();
            lst1.invalidateViews();



        }

        lst1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String aa = titles.get(position).toString();
                trip stu = stud.get(position);
                Intent i = new Intent(getApplicationContext(),edit.class);
                i.putExtra("id",stu.id);
                i.putExtra("name",stu.name);
                i.putExtra("destination",stu.destination);
                i.putExtra("date",stu.date);
                i.putExtra("require",stu.require);
                i.putExtra("description",stu.description);
                startActivity(i);

            }
        });

    }
}