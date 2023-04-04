package com.example.reefectoire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button registerButton,loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerButton=(Button) findViewById(R.id.btn_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_Activityregister();
            }
        });
        loginButton=(Button) findViewById(R.id.btn_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_ActivityLogin();
            }
        });
    }
    public void open_Activityregister()
    {
        Intent intent=new Intent(this,register.class);
        startActivity(intent);
    }
    public void open_ActivityLogin()
    {
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}