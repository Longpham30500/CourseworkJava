package com.example.mobilecoursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;


public class MainActivity extends AppCompatActivity {
    EditText app_name,app_destination,app_date,app_require,app_description;
    Button app_add, app_view, app_search;

    AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app_name = findViewById(R.id.name);
        app_destination  = findViewById(R.id.destination);
        app_date = findViewById(R.id.date);
        app_require = findViewById(R.id.require);
        app_description = findViewById(R.id.description);
        app_add = findViewById(R.id.addTrip);
        app_view = findViewById(R.id.viewTrip);
        app_search = findViewById(R.id.search);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this,R.id.name,
                RegexTemplate.NOT_EMPTY,R.string.validate_name);
        awesomeValidation.addValidation(this,R.id.destination,
                RegexTemplate.NOT_EMPTY,R.string.validate_destination);
        awesomeValidation.addValidation(this,R.id.require,
                RegexTemplate.NOT_EMPTY,R.string.validate_require);
        awesomeValidation.addValidation(this,R.id.date,
                RegexTemplate.NOT_EMPTY,R.string.validate_date);

        app_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), view.class);
                startActivity(i);
            }
        });
        app_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), search.class);
                startActivity(i);
            }
        });
        app_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                if (awesomeValidation.validate()){
                    //success
                    Toast.makeText(getApplicationContext()
                            , "Successfully..", Toast.LENGTH_SHORT).show();
                    insert();
                }else {
                    Toast.makeText(getApplicationContext()
                            , "Fail.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void insert()
    {
        try
        {
            String name = app_name.getText().toString();
            String destination = app_destination.getText().toString();
            String date = app_date.getText().toString();
            String require = app_require.getText().toString();
            String description = app_description.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("SliteDb", Context.MODE_PRIVATE,null);
            db.execSQL("CREATE TABLE IF NOT EXISTS details(id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR,destination VARCHAR,date VARCHAR, require VARCHAR,description VARCHAR)");


            String sql = "insert into details(name,destination,date,require,description)values(?,?,?,?,?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1,name);
            statement.bindString(2,destination);
            statement.bindString(3,date);
            statement.bindString(4,require);
            statement.bindString(5,description);

            statement.execute();
            Toast.makeText(this,"Record added",Toast.LENGTH_LONG).show();

            app_name.setText("");
            app_destination.setText("");
            app_date.setText("");
            app_require.setText("");
            app_description.setText("");

            app_name.requestFocus();
        }
        catch (Exception ex)
        {
            Toast.makeText(this,"Record Fail",Toast.LENGTH_LONG).show();
        }
    }
}