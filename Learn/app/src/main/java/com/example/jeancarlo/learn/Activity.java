package com.example.jeancarlo.learn;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

public class Activity extends AppCompatActivity {


    private EditText cd,name,col,sec;


  // dataAdapter.setDropDownViewResource(R.layout.showdata_product);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cd= (EditText)findViewById(R.id.cedu);
        name=(EditText)findViewById(R.id.nombre);
        col=(EditText)findViewById(R.id.colegio);
        sec=(EditText)findViewById(R.id.seccion);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    //Save students
    public void save(View view){
        OpenHelper estudiantes = new OpenHelper(this,"estudiantes", null, 1);
        SQLiteDatabase bd= estudiantes.getWritableDatabase();
        String ced= cd.getText().toString();
        String nm= name.getText().toString();
        String co= col.getText().toString();
        String sc= sec.getText().toString();

        //this part verify if the all spaces are full
        if(TextUtils.isEmpty(ced)&TextUtils.isEmpty(nm)&TextUtils.isEmpty(co)&TextUtils.isEmpty(sc)){
            cd.setError("Digitar cédula");
            name.setError("Digitar nombre");
            col.setError("Digitar colegio");
            sec.setError("Digitar sección");
            return;
        }

        else
        {

        ContentValues registro= new ContentValues();   //is a class where We are going to save the informtion
        registro.put("ced", ced);
        registro.put("nombre", nm);
        registro.put("colegio", co);
        registro.put("secc", sc);
        bd.insert("estudiantes", null, registro);
        bd.close();

        Toast.makeText(getApplicationContext(), "Operation successfully completed!",Toast.LENGTH_SHORT).show();
        clear_text();
    }
    }

    public void search(View view){
        OpenHelper estudiantes=new OpenHelper(this,"estudiantes",null,1);
        SQLiteDatabase bd=estudiantes.getWritableDatabase();
        String ced= cd.getText().toString();
        Cursor fila= bd.rawQuery( //return 0 o 1 row //is a query
                "select nombre,colegio,secc from estudiantes where ced=" + ced, null);
        if(fila.moveToFirst()){
            name.setText(fila.getString(0));
            col.setText(fila.getString(1));
            sec.setText(fila.getString(2));
        }else
            Toast.makeText(getApplicationContext(),"Does not exist the ID the you wrote!",Toast.LENGTH_LONG).show();
        bd.close();
    }
    public void update(View view){

        OpenHelper estudiantes=new OpenHelper(this,"estudiantes",null,1);
        SQLiteDatabase bd= estudiantes.getWritableDatabase();
        String ced= cd.getText().toString();
        String nm= name.getText().toString();
        String co= col.getText().toString();
        String sc= sec.getText().toString();
        ContentValues registro= new ContentValues();
        registro.put("ced", ced);
        registro.put("nombre", nm);
        registro.put("colegio", co);
        registro.put("secc", sc);
        int cant= bd.update("estudiantes", registro, "ced=" + ced, null);
        bd.close();
        if(cant==1){
            Toast.makeText(getApplicationContext(),"Updated information",Toast.LENGTH_LONG).show();
            clear_text();
        }else
            Toast.makeText(getApplicationContext(),"You can not to change the ID",Toast.LENGTH_LONG).show();
    }
        public void delete(View view){
            OpenHelper estudiantes=new OpenHelper(this,"estudiantes",null,1);
            SQLiteDatabase bd= estudiantes.getWritableDatabase();
            String ced=cd.getText().toString();
            ContentValues registro= new ContentValues();
            registro.put("ced",ced);
            int cant = bd.delete("estudiantes","ced="+ced,null);
            if(cant==1){
                Toast.makeText(getApplicationContext(),"Has been removed of the data base!",Toast.LENGTH_LONG).show();
                clear_text();
            }else
                Toast.makeText(getApplicationContext(),"Has not been removed",Toast.LENGTH_LONG).show();

        }

   public  void clear_text(){
       cd.setText("");
       name.setText("");
       col.setText("");
       sec.setText("");
   }

    //Open the next view
    public  void open_2(View view){
        Intent intent= new Intent(this,Part2.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
