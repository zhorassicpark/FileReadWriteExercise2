package com.example.filereadwriteexercise2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SubActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        textView = findViewById(R.id.text);
        Intent intent = getIntent();
        textView.setText("hello, " + intent.getStringExtra("username") + "!");
    }
}