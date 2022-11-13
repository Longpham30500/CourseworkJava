package com.example.mobilecoursework;

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

public class expenseview extends AppCompatActivity {
    EditText app_type,app_cost,app_date;
    Button app_add, viewAllExpense;

    AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenseview);
        app_type = findViewById(R.id.type);
        app_cost  = findViewById(R.id.cost);
        app_date = findViewById(R.id.date);
        app_add = (Button) findViewById(R.id.addExpense);
        viewAllExpense = (Button) findViewById(R.id.viewAllExpense);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this,R.id.type,
                RegexTemplate.NOT_EMPTY,R.string.validate_type);
        awesomeValidation.addValidation(this,R.id.cost,
                RegexTemplate.NOT_EMPTY,R.string.validate_cost);
        awesomeValidation.addValidation(this,R.id.date,
                RegexTemplate.NOT_EMPTY,R.string.validate_date);
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
        viewAllExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), viewallexpense.class);
                startActivity(i);
            }
        });

    }
    public void insert()
    {
        try
        {
            String type = app_type.getText().toString();
            String cost = app_cost.getText().toString();
            String date = app_date.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("SliteDb", Context.MODE_PRIVATE,null);
            db.execSQL("CREATE TABLE IF NOT EXISTS expenses(id INTEGER PRIMARY KEY AUTOINCREMENT,type VARCHAR,cost VARCHAR,date VARCHAR)");


            String sql = "insert into expenses(type,cost,date)values(?,?,?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1,type);
            statement.bindString(2,cost);
            statement.bindString(3,date);

            statement.execute();
            Toast.makeText(this,"Record added",Toast.LENGTH_LONG).show();

            app_type.setText("");
            app_cost.setText("");
            app_date.setText("");
        }
        catch (Exception ex)
        {
            Toast.makeText(this,"Record Fail",Toast.LENGTH_LONG).show();
        }
    }
}