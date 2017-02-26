package com.example.jeancarlo.learn;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Part2 extends AppCompatActivity {

    private Cursor fila;
    private EditText ced,nam,col,sec;
    ListView list; //ObjectLIst
    OpenHelper openHelper;
    List<String> item=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_part2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        list=(ListView)findViewById(R.id.ShowList);


        ced=(EditText)findViewById(R.id.cedu);
        nam=(EditText)findViewById(R.id.name);
        col=(EditText)findViewById(R.id.colegio);
        sec=(EditText)findViewById(R.id.secc);

        showStudents();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    //Show in listView
    private void showStudents(){
        openHelper= new OpenHelper(this,"estudiantes", null, 1); //global variable
        Cursor cursor=openHelper.getStudents(); //All information from database is save in cursor object
        item= new ArrayList<String>(); //global variable
        String id="",name="",school="";

        while(cursor.moveToNext()){

            id=cursor.getString(0);
            name=cursor.getString(1);
           //school=cursor.getString(2);
            item.add(id+"   "+ name);

        }
        ArrayAdapter<String> adapter=
                new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,item); //what we have in item object it is going to pass adapter
        list.setAdapter(adapter); //Save in listView
    }


    //We can clean the editText
    public  void clear_text(){
        ced.setText("");
        nam.setText("");
        col.setText("");
        sec.setText("");
    }

    //in this method we can show the students in order by ced
    public void iniciar(View view){
        clear_text();
        OpenHelper estudiantes= new OpenHelper(this,"estudiantes",null,1);
        SQLiteDatabase bd= estudiantes.getWritableDatabase();

         fila= bd.rawQuery("select * from estudiantes order by ced",null);
        if(fila.moveToFirst()){
            ced.setText(fila.getString(0));
            nam.setText(fila.getString(1));
            col.setText(fila.getString(2));
            sec.setText(fila.getString(3));
        }
        else
            Toast.makeText(Part2.this, "The database don't have any information!", Toast.LENGTH_SHORT).show();
    }


    // The next in order by list
    public void next(View view){
       try {
            if(!fila.isLast()) {
                fila.moveToNext();
                ced.setText(fila.getString(0));
                nam.setText(fila.getString(1));
                col.setText(fila.getString(2));
                sec.setText(fila.getString(3));
            }
           else{
                Toast.makeText(getApplicationContext(),"Were all",Toast.LENGTH_LONG).show();
            }
       }
       catch (Exception e){
            e.printStackTrace();
       }
    }

    //The last of list
    public void last(View view){
        try {
            if(!fila.isFirst()){
                fila.moveToPrevious();
                ced.setText(fila.getString(0));
                nam.setText(fila.getString(1));
                col.setText(fila.getString(2));
                sec.setText(fila.getString(3));
            }
            else
                Toast.makeText(getApplicationContext(),"does not exist  more elements in the list!",Toast.LENGTH_LONG).show();
        }
        catch (Exception E){
            E.printStackTrace();
        }
    }

}
