package com.example.savedatademo;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textView1;
    TextView textView2;
    TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        textView1 = findViewById(R.id.save);
        textView1.setOnClickListener(this);

        textView2 = findViewById(R.id.getData);
        textView2.setOnClickListener(this);

        textView3 = findViewById(R.id.showData);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.save) {
            externalStorage();
        } else {
            readFromSdCard();
        }
    }


    private void interiorStorage() {
        String enData = Environment.getDataDirectory().getAbsolutePath();
        String fileDir = getFilesDir().getAbsolutePath();
        String cacheDir = getCacheDir().getAbsolutePath();
        String dir = getDir("data", MODE_PRIVATE).getAbsolutePath();
        print(enData + "\n" + fileDir + "\n" + cacheDir + "\n" + dir);
    }

    private void externalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            File[] files = getExternalFilesDirs(Environment.MEDIA_MOUNTED);
            for (File file : files) {
                Log.e("main", file.getAbsolutePath());
            }
        }

//        String exDir = Environment.getExternalStorageDirectory().getAbsolutePath();
//        String publicDir = Environment.getExternalStoragePublicDirectory("").getAbsolutePath();
//        String externalFilesDir = getExternalFilesDir("").getAbsolutePath();
//        String externalCacheDir = getExternalCacheDir().getAbsolutePath();
//
//
//        print(exDir + "\n" + publicDir + "\n" + externalFilesDir + "\n" + externalCacheDir);


        String myDir = getExternalFilesDir("hello").getAbsolutePath();
        Toast.makeText(getBaseContext(), myDir, Toast.LENGTH_LONG).show();
        try {
            File fs = createFiles(myDir + "/text.txt");

            FileOutputStream outputStream = new FileOutputStream(fs);
            outputStream.write("这是一个数据".getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File createFiles(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return file;
        }

        try {
            if (file.createNewFile()) {
                Toast.makeText(this, "创建成功", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "创建失败", Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


    private void print(String type) {
        Toast.makeText(this, type, Toast.LENGTH_LONG).show();
    }


    private void readFromSdCard() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "没有权限", Toast.LENGTH_LONG).show();
            return;
        }
        File file = getExternalFilesDir("hello/text.txt");
        try {
            FileInputStream fileInputStream = new FileInputStream(file.getCanonicalPath());

            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
            StringBuffer stringBuffer = new StringBuffer();
            String line = null;

            while ((line = reader.readLine())!=null){
                stringBuffer.append(line);
            }
            Toast.makeText(this, stringBuffer.toString(), Toast.LENGTH_LONG).show();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x123);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0x123) {
            if (grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "同意权限", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "不同意权限", Toast.LENGTH_LONG).show();
            }
        }
    }
}
