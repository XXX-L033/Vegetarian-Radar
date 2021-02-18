package com.example.test.loginSystem;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test.mapSystem.MapsActivity;
import com.example.test.R;

import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mLogin_username;
    private EditText mLogin_password;
    private Button mLogin_OK;
    private TextView mLogin_forget;
    private TextView mLogin_newCustomer;
    private DBOpenHelper mDBOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.login);
        mDBOpenHelper = new DBOpenHelper(this);
        initView();
    }

    private void initView() {
        mLogin_username = findViewById(R.id.Login_username);
        mLogin_password =  findViewById(R.id.Login_password);
        mLogin_OK =  findViewById(R.id.Login_OK);
        mLogin_forget = findViewById(R.id.Login_forget);
        mLogin_newCustomer =  findViewById(R.id.Login_newCustomer);
        mLogin_OK.setOnClickListener(this);
        mLogin_forget.setOnClickListener(this);
        mLogin_newCustomer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // register
            case R.id.Login_newCustomer:
                startActivity(new Intent(this, RegistrationActivity.class)); //change to register page
                finish();
                break;
            // check login
            case R.id.Login_OK:
                String name = mLogin_username.getText().toString().trim();
                String password = mLogin_password.getText().toString().trim();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
                    ArrayList<User> data = mDBOpenHelper.getAllData();  //get the user list
                    boolean match = false;
                    for (int i = 0; i < data.size(); i++) {
                        User user = data.get(i);
                        if (name.equals(user.getName()) && password.equals(user.getPassword())) {
                            match = true;
                            break;
                        } else {
                            match = false;
                        }
                    }
                    if (match == true) {
                        Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        Intent intentPhone = getIntent();
                        String phone = intentPhone.getStringExtra("phone");
                        Intent intent = new Intent(this, MapsActivity.class);  //
                        intent.putExtra("phone",phone);
                        startActivity(intent);
                        finish();//delete Activity
                    } else {
                        Toast.makeText(this, "The user does not exist", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "NAME or PASSWORD cannot be empty", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.Login_forget:
                startActivity(new Intent(this, ResetPassword.class)); //change to register page
                finish();
                break;
        }
    }
}

