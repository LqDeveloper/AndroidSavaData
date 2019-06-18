package com.example.savedatademo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textView1;
    TextView textView2;
    TextView textView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView1 = findViewById(R.id.sharedPerference);
        textView1.setOnClickListener(this);

        textView2 = findViewById(R.id.file);
        textView2.setOnClickListener(this);

        textView3 = findViewById(R.id.sqlLite);
        textView3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.sharedPerference:
                intent = new Intent(MainActivity.this, PerferenceActivity.class);
                startActivity(intent);
                break;
            case R.id.file:
                intent = new Intent(MainActivity.this, FileActivity.class);
                startActivity(intent);
                break;
            case R.id.sqlLite:
                intent = new Intent(MainActivity.this, SqlLiteActivity.class);
                startActivity(intent);
                break;
        }
    }
}
