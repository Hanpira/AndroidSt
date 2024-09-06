package com.example.item;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText email, nameUser, phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        email = (EditText)findViewById(R.id.editTextEmail);
        nameUser = (EditText)findViewById(R.id.editTextName);
        phone = (EditText)findViewById(R.id.editTextPhone);

        Bundle args = getIntent().getExtras();
        if (args != null) {
            email.setText(args.getString("Email"));
            nameUser.setText(args.getString("NameUser"));
            phone.setText(args.getString("Phone"));
        }

    }

    public void OnContinueClick(View view){
        Log.i("nextStep", "OnContinueClick: ");

        Bundle args = getIntent().getExtras();
        if (email != null && phone != null ) {
            Intent intent = new Intent(this, Plant.class);
            intent.putExtra("Email", email.getText().toString());
            intent.putExtra("NameUser", nameUser.getText().toString());
            intent.putExtra("Phone", nameUser.getText().toString());
            if (args != null) {
                intent.putExtra("Email", args.getString("Email", ""));
                intent.putExtra("NameUser", args.getString("Name", ""));
                intent.putExtra("Phone", args.getString("Phone", ""));
            }
            startActivity(intent);
        } else {
            Toast.makeText(this, "Введите данные почты и номер телефона", Toast.LENGTH_SHORT).show();
        }
    }
}