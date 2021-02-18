package com.example.test.loginSystem;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test.mapSystem.MapsActivity;
import com.example.test.R;

import java.util.ArrayList;

public class ResetPassword extends AppCompatActivity implements View.OnClickListener {
    private DBOpenHelper mDBOpenHelper;
    private ImageView mReset_back;
    private EditText mReset_phone;
    private EditText mReset_password;
    private EditText mReset_password2;
    private Button mReset_OK;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.reset_password);
        initView();
        mDBOpenHelper = new DBOpenHelper(this);
    }

    private void initView() {
        mReset_phone = (EditText) findViewById(R.id.Reset_phone);
        mReset_password = (EditText) findViewById(R.id.Reset_password);
        mReset_password2 = (EditText) findViewById(R.id.Reset_password2);
        mReset_OK = (Button) findViewById(R.id.Reset_OK);
        mReset_back = findViewById(R.id.Reset_back);

        mReset_back.setOnClickListener(this);
        mReset_OK.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Reset_back: //back to the login page
                Intent intent1 = new Intent(this, LoginActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.Reset_OK:
                //get username and password
                String phone = mReset_phone.getText().toString().trim();
                String password = mReset_password.getText().toString().trim();
                String password2 = mReset_password2.getText().toString().trim();
                if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(password2)) {
                    ArrayList<User> data = mDBOpenHelper.getAllData();  //get the user list
                    boolean match = false;
                    for (int i = 0; i < data.size(); i++) {
                        User user = data.get(i);
                        if (phone.equals(user.getPhone())) {
                            match = true;
                            break;
                        } else {
                            match = false;
                        }
                    }
                    if (match == true) {
                        if (password.equals(password2)) {
                            mDBOpenHelper.updateUser(phone, password);
                            Intent intent2 = new Intent(this, MapsActivity.class);
                            startActivity(intent2);
                            finish();
                            Toast.makeText(this, "Reset Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Passwords not match", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "The user does not exist", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Please complete your information", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
