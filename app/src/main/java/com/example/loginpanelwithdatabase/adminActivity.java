package com.example.loginpanelwithdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class adminActivity extends AppCompatActivity {

    TextView tv;
    EditText et;
    Button add, edit, delete, reset;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        et = findViewById(R.id.idName);
        tv = findViewById(R.id.allUsers);
        add = findViewById(R.id.add);
        edit = findViewById(R.id.edit);
        delete = findViewById(R.id.delete);
        reset = findViewById(R.id.reset);
        dbHelper = new DBHelper(this);


        for(int i=0;i<=dbHelper.readAll().size()-1;i++){
            tv.append("num: "+i+" login: "+dbHelper.readAll().get(i).getLOGIN_COL()+" is admin:"+dbHelper.readAll().get(i).getISADMIN_COL()+"\n");
        }

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dbHelper.readWhereUsername(et.getText().toString()).size()<1 ){
                    Toast.makeText(adminActivity.this, "User not exist", Toast.LENGTH_SHORT).show();
                }else if(et.getText().toString().equals("Kamil")){
                    Toast.makeText(adminActivity.this, "User is admin", Toast.LENGTH_SHORT).show();
                }else{
                    dbHelper.updateNewUser(et.getText().toString(),et.getText().toString(),SHA.encryptThisString("secure"),"false");
                    finish();
                    startActivity(getIntent());
                }
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dbHelper.readWhereUsername(et.getText().toString()).size()<1 ){
                    Toast.makeText(adminActivity.this, "User not exist", Toast.LENGTH_SHORT).show();
                }else if(et.getText().toString().isEmpty()){
                    Toast.makeText(adminActivity.this, "Name is empty", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(adminActivity.this, EditUser.class);
                    intent.putExtra("name",et.getText().toString());
                    startActivity(intent);
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dbHelper.readWhereUsername(et.getText().toString()).size()<1 ) {
                    Toast.makeText(adminActivity.this, "User not exist", Toast.LENGTH_SHORT).show();
                }else if(et.getText().toString().isEmpty()){
                    Toast.makeText(adminActivity.this, "Name is empty", Toast.LENGTH_SHORT).show();
                }else if(et.getText().toString().equals("Kamil")){
                    Toast.makeText(adminActivity.this, "Is ADMIN", Toast.LENGTH_SHORT).show();
                }
                else{
                    dbHelper.deleteUser(et.getText().toString());
                    Toast.makeText(adminActivity.this, "User Deleted", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(getIntent());
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dbHelper.readWhereUsername(et.getText().toString()).size()==1){
                    Toast.makeText(adminActivity.this, "Name exist", Toast.LENGTH_SHORT).show();
                }else if(et.getText().toString().isEmpty()){
                    Toast.makeText(adminActivity.this, "Name is empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    dbHelper.addNewUser(et.getText().toString(),SHA.encryptThisString("secure"),"false");
                    finish();
                    startActivity(getIntent());
                }
            }
        });


    }

}