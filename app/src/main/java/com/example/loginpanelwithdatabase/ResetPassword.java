package com.example.loginpanelwithdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResetPassword extends AppCompatActivity {

    EditText et;
    Button bt;
    private DBHelper dbHelper;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        et = findViewById(R.id.idPassword);
        bt = findViewById(R.id.idSubmit);
        Intent i = getIntent();
        Bundle b =i.getExtras();
        value = (String) b.get("name");
        dbHelper = new DBHelper(this);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et.getText().toString().equals("secure")){
                    Toast.makeText(ResetPassword.this, "secure is blocked password", Toast.LENGTH_SHORT).show();
                }
                if(et.getText().toString().isEmpty()){
                    Toast.makeText(ResetPassword.this, "Password must be changed", Toast.LENGTH_SHORT).show();
                }else{
                    dbHelper.updateNewUser(value,value,SHA.encryptThisString(et.getText().toString()),"false");
                    Intent intent = new Intent(ResetPassword.this,MainActivity2.class);
                    intent.putExtra("name",value);
                    startActivity(intent);
                }

            }
        });

    }
}