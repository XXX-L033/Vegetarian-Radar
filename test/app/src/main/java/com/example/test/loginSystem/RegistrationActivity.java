package com.example.test.loginSystem;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test.mapSystem.MapsActivity;
import com.example.test.R;

import java.util.ArrayList;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private DBOpenHelper mDBOpenHelper;
    private ImageView mRegistration_back;
    private EditText mRegistration_username;
    private EditText mRegistration_password;
    private EditText mRegistration_password2;
    private EditText mRegistration_phone;
    private Button mRegistration_OK;
    private TextView mcb_word;
    public String phone;
    private CheckBox mcb;
    private boolean select = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.registration);
        initView();
        mDBOpenHelper = new DBOpenHelper(this);
    }

    private void initView() {
        mRegistration_username = (EditText) findViewById(R.id.Registration_username);
        mRegistration_password = (EditText) findViewById(R.id.Registration_password);
        mRegistration_password2 = (EditText) findViewById(R.id.Registration_password2);
        mRegistration_phone = (EditText) findViewById(R.id.Registration_phone);
        mRegistration_OK = (Button) findViewById(R.id.Registration_OK);
        mRegistration_back = findViewById(R.id.Registration_back);
        mcb_word = findViewById(R.id.cb_word);
        mcb = findViewById(R.id.cb);

        mRegistration_back.setOnClickListener(this);
        mRegistration_OK.setOnClickListener(this);
        mcb.setOnClickListener(this);
        mcb_word.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Registration_back: //back to the login page
                Intent intent1 = new Intent(this, LoginActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.cb:
                select = true;
                break;
            case R.id.cb_word:
                Intent intent3 = new Intent(this, DisclaimerWord.class);
                startActivity(intent3);
                finish();
                Toast.makeText(this, "Disclaimer", Toast.LENGTH_SHORT).show();
                break;
            case R.id.Registration_OK:
                //get username and password
                String username = mRegistration_username.getText().toString().trim();
                String password = mRegistration_password.getText().toString().trim();
                String password2 = mRegistration_password2.getText().toString().trim();
                phone = mRegistration_phone.getText().toString().trim();
                if (select == true) {
                    if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(password2) && !TextUtils.isEmpty(phone)) {
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
                        if (match == false) {
                            if (checkPhone(phone) == true) {
                                if (checkPassword(password) == true) {
                                    if (password.equals(password2)) {
                                        mDBOpenHelper.addUser(username, password, phone);
                                        Toast.makeText(this, "Register Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent2 = new Intent(this, MapsActivity.class);
                                        intent2.putExtra("phone", phone);
                                        startActivity(intent2);
                                        finish();
                                    } else {
                                        Toast.makeText(this, "PASSWORDS are not match", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(this, "PASSWORDS must be 8-16 digits of English, numbers, / ", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(this, "The format of PHONE NUMBER is incorrect", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "This Phone Number has already register", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Please complete your information", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Please Check the disclaimer", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public static boolean checkPassword(String pwd) {
        String regExp = "^[\\w_]{8,16}$";
        if (pwd.matches(regExp)) {
            return true;
        }
        return false;
    }

    public static boolean checkPhone(String phone_number) {
        String regExp = "[0-9]*";
        if (phone_number.matches(regExp)) {
            return true;
        }
        return false;
    }
}
