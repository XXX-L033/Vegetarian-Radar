package com.example.test.loginSystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test.R;

public class DisclaimerWord extends AppCompatActivity implements View.OnClickListener {
    private ImageView mDisclaimer_back;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.disclaimer_layout);
        initView();
    }

    private void initView() {
        mDisclaimer_back = findViewById(R.id.Disclaimer_back);
        mDisclaimer_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Disclaimer_back:
                Intent intent1 = new Intent(this, RegistrationActivity.class);
                startActivity(intent1);
                finish();
                break;
        }
    }
}
