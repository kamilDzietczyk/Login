package com.example.loginpanelwithdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditUser extends AppCompatActivity {

    EditText login, isAdmin;
    Button Login, Back;
    private DBHelper dbHelper;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        dbHelper = new DBHelper(EditUser.this);

        Intent i = getIntent();
        Bundle b =i.getExtras();
        value = (String) b.get("name");

        login = findViewById(R.id.idLogin);
        isAdmin = findViewById(R.id.idAdmin);
        Login = findViewById(R.id.idSubmit);
        Back = findViewById(R.id.idBack);

        login.setText(dbHelper.readWhereUsername(value).get(0).getLOGIN_COL());
        isAdmin.setText(dbHelper.readWhereUsername(value).get(0).getISADMIN_COL());

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isAdmin.getText().toString().isEmpty() && !login.getText().toString().isEmpty() && (isAdmin.getText().toString().equals("true") || isAdmin.getText().toString().equals("false"))){
                    dbHelper.updateNewUser(value,login.getText().toString(),dbHelper.readWhereUsername(value).get(0).getPASSWORD_COL(),isAdmin.getText().toString());
                    Intent intent = new Intent(EditUser.this, adminActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(EditUser.this, "Admin must be true or false", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditUser.this, adminActivity.class);
                startActivity(intent);
            }
        });
    }
}