package com.example.mobilecoursework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class search extends AppCompatActivity {

    EditText value;
    TextView name, destination, date, require, description;
    Button search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        name = (TextView) findViewById(R.id.name);
        destination = (TextView) findViewById(R.id.destination);
        date = (TextView) findViewById(R.id.date);
        require = (TextView) findViewById(R.id.require);
        description = (TextView) findViewById(R.id.description);

        value = (EditText) findViewById(R.id.value);
        search = (Button) findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String n = value.getText().toString();
                SQLiteDatabase db = getApplicationContext().openOrCreateDatabase("SliteDb", Context.MODE_PRIVATE, null );
                Cursor c = db.rawQuery("select * from details where name='"+n+"'",null);
                int nameIndex = c.getColumnIndex("name");
                int destinationIndex = c.getColumnIndex("destination");
                int dateIndex = c.getColumnIndex("date");
                int requireIndex = c.getColumnIndex("require");
                int descriptionIndex = c.getColumnIndex("description");
                if(c.getCount() == 0)
                {
                    Toast.makeText(getApplicationContext(), "No Record Found", Toast.LENGTH_LONG).show();
                    return ;
                }

                while(c.moveToNext()){
                    name.setText("Name: "+ c.getString(nameIndex));
                    destination.setText("Destination: "+ c.getString(destinationIndex));
                    date.setText("date: "+ c.getString(dateIndex));
                    require.setText("require: "+ c.getString(requireIndex));
                    description.setText("description: "+ c.getString(descriptionIndex));
                }

                Toast.makeText(getApplicationContext(), "Result \n"+n, Toast.LENGTH_LONG).show();
            }
        });
    }
}