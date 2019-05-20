package com.example.ritwik.musicplayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class IntroActivity extends AppCompatActivity implements OnClickListener{

    Button proceed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        proceed = findViewById(R.id.start_btn);
        proceed.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent1 = new Intent(this,MainActivity.class);
        startActivity(intent1);
    }
}
