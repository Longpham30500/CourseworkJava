package com.example.mobilecoursework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.Debug;

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

public class edit extends AppCompatActivity {
    EditText app_id,app_name,app_destination,app_date,app_require,app_description;
    Button app_edit, app_delete, app_expense;

    AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        app_id = findViewById(R.id.id);
        app_name = findViewById(R.id.name);
        app_destination  = findViewById(R.id.destination);
        app_date = findViewById(R.id.date);
        app_require = findViewById(R.id.require);
        app_description = findViewById(R.id.description);


        app_edit = findViewById(R.id.bt1);
        app_delete = findViewById(R.id.bt2);
        app_expense = findViewById(R.id.bt3);

        Intent i = getIntent();

        String t1 = i.getStringExtra("id").toString();
        String t2 = i.getStringExtra("name").toString();
        String t3 = i.getStringExtra("destination").toString();
        String t4 = i.getStringExtra("date").toString();
        String t5 = i.getStringExtra("require").toString();
        String t6 = i.getStringExtra("description").toString();
        app_id.setText(t1);
        app_name.setText(t2);
        app_destination.setText(t3);
        app_date.setText(t4);
        app_require.setText(t5);
        app_description.setText(t6);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this,R.id.name,
                RegexTemplate.NOT_EMPTY,R.string.validate_name);
        awesomeValidation.addValidation(this,R.id.destination,
                RegexTemplate.NOT_EMPTY,R.string.validate_destination);
        awesomeValidation.addValidation(this,R.id.require,
                RegexTemplate.NOT_EMPTY,R.string.validate_require);
        awesomeValidation.addValidation(this,R.id.date,
                RegexTemplate.NOT_EMPTY,R.string.validate_date);

        app_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (awesomeValidation.validate()){
                    //success
                    Toast.makeText(getApplicationContext()
                            , "Successfully..", Toast.LENGTH_SHORT).show();
                    Edit();
                    Intent i = new Intent(getApplicationContext(), view.class);
                    startActivity(i);
                }else {
                    Toast.makeText(getApplicationContext()
                            , "Fail.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        app_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delete();
                Intent i = new Intent(getApplicationContext(), view.class);
                startActivity(i);
            }

        });


        app_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),expenseview.class);
                startActivity(i);

            }
        });

    }


    public void Delete()
    {
        try
        {
            String id = app_id.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("SliteDb", Context.MODE_PRIVATE,null);


            String sql = "delete from details where id = ?";
            SQLiteStatement statement = db.compileStatement(sql);

            statement.bindString(1,id);
            statement.execute();
            Toast.makeText(this,"Record Deleted",Toast.LENGTH_LONG).show();


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
    public void Edit()
    {
        try
        {
            String name = app_name.getText().toString();
            String destination = app_destination.getText().toString();
            String date = app_date.getText().toString();
            String require = app_require.getText().toString();
            String description = app_description.getText().toString();

            String id = app_id.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("SliteDb",Context.MODE_PRIVATE,null);


            String sql = "update details set name = ?,destination=?,date=?,require = ?,description=? where id= ?";
            SQLiteStatement statement = db.compileStatement(sql);

            statement.bindString(1,name);
            statement.bindString(2,destination);
            statement.bindString(3,date);
            statement.bindString(4,require);
            statement.bindString(5,description);
            statement.bindString(6,id);

            statement.execute();
            Toast.makeText(this,"Record Updateddd",Toast.LENGTH_LONG).show();


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