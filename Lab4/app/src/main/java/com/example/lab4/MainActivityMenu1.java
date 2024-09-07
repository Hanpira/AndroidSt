package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivityMenu1 extends AppCompatActivity {
    private EditText emailEditText;
    private EditText otherEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu1);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        emailEditText = findViewById(R.id.emailEditText);
        otherEditText = findViewById(R.id.otherEditText);
        Button checkButton = findViewById(R.id.checkButton);

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_fill_email) {
            emailEditText.setText(R.string.test_iba_by);
            return true;
        } else if (item.getItemId() == R.id.action_fill_other) {
            otherEditText.setText(R.string.test1_iba_by);
            return true;
        } else if (item.getItemId() == R.id.action_fill_all) {
            emailEditText.setText(R.string.test_iba_by);
            otherEditText.setText(R.string.test1_iba_by);
            return true;
        } else if (item.getItemId() == R.id.action_clear) {
            clearData();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
    private void clearData() {
        emailEditText.setText("");
        otherEditText.setText("");
    }

    private void checkData() {
        String email = emailEditText.getText().toString();
        String otherValue = otherEditText.getText().toString();

        if (email.isEmpty() && otherValue.isEmpty()) {
            Toast.makeText(this, "Оба поля пустые", Toast.LENGTH_SHORT).show();
        }else {
            StringBuilder result = new StringBuilder("Результаты проверки:\n");
            if (!email.isEmpty()) {
                result.append("Email: ").append(email).append("\n");
            }
            if (!otherValue.isEmpty()) {
                result.append("Другое значение: ").append(otherValue).append("\n");
            }
            Toast.makeText(this, result.toString(), Toast.LENGTH_LONG).show();
        }
    }
}