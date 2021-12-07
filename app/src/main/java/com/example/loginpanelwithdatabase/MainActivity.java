package com.example.loginpanelwithdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText userLoginEdt, userPasswordEdt;
    private Button loginUserBtn;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userLoginEdt = findViewById(R.id.idLogin);
        userPasswordEdt = findViewById(R.id.idPassword);
        loginUserBtn = findViewById(R.id.idSubmit);

        dbHelper = new DBHelper(MainActivity.this);

        loginUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=0;
                String Login = userLoginEdt.getText().toString();
                String Password = userPasswordEdt.getText().toString();

                if(Login.isEmpty() && Password.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!dbHelper.readWhere(Login,Password).isEmpty()){
                    i++;
                }

                if(i>0){
                    if(dbHelper.readWhere(Login,Password).get(0).getISADMIN_COL().equals("true")){
                        Intent intent = new Intent(MainActivity.this, adminActivity.class);
                        startActivity(intent);
                    }else{
                        if(dbHelper.readWhere(Login,Password).get(0).getPASSWORD_COL().equals(SHA.encryptThisString("secure"))){
                            Intent intent = new Intent(MainActivity.this, ResetPassword.class);
                            intent.putExtra("name", dbHelper.readWhere(Login, Password).get(0).getLOGIN_COL());
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                            intent.putExtra("name", dbHelper.readWhere(Login, Password).get(0).getLOGIN_COL());
                            startActivity(intent);
                        }
                    }

                }else{
                    Toast.makeText(MainActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                }
                userLoginEdt.setText("");
                userPasswordEdt.setText("");
            }
        });
    }
}