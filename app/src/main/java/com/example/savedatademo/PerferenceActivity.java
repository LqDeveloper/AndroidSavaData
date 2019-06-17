package com.example.savedatademo;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class PerferenceActivity extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView textView1;
    TextView textView2;
    TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perference);

        sharedPreferences = getSharedPreferences("demo", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        textView1 = findViewById(R.id.save);
        textView1.setOnClickListener(this);

        textView2 = findViewById(R.id.getData);
        textView2.setOnClickListener(this);

        textView3 = findViewById(R.id.showData);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.save) {
            editor.putString("name", "小明");
            editor.putInt("age", 10);
            editor.apply();
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        } else {
            String name = sharedPreferences.getString("name", "");
            int age = sharedPreferences.getInt("age", 0);
            textView3.setText("name: " + name + "  age: " + age);
        }
    }
}
