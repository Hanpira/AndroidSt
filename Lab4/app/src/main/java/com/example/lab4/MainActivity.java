package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainMenu1), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void onClickMenu1(View v){
        Intent intent = new Intent(this, MainActivityMenu1.class);
        startActivity(intent);
    }
    public void onClickMenu2(View v){
        Intent intent = new Intent(this, MainActivityMenu2.class);
        startActivity(intent);
    }
    public void onClickMenu3(View v){
        Intent intent = new Intent(this, MainActivityMenu3.class);
        startActivity(intent);
    }
}