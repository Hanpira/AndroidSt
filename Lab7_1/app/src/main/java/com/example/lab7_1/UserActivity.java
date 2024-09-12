package com.example.lab7_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    Bundle bundle;
    String userId;
    EditText editName, editEmail;
    Button deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mDatabase = FirebaseDatabase.getInstance("https://authentication-b4c80-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users");

        bundle = getIntent().getExtras();
        editName = findViewById(R.id.editNameAU);
        editEmail = findViewById(R.id.editEmailAU);
        deleteBtn = findViewById(R.id.btnDelete);

        if (bundle != null) {
            userId = bundle.getString("id");
            Toast.makeText(this, "Id element " + userId, Toast.LENGTH_SHORT).show();
            mDatabase.child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    Toast.makeText(UserActivity.this, "Filling succeed", Toast.LENGTH_SHORT).show();
                    if (task.isSuccessful()) {
                        DataSnapshot ds = task.getResult();
                        String email = ds.child("email").getValue(String.class);
                        String name = ds.child("username").getValue(String.class);

                        editEmail.setText(email);
                        editName.setText(name);
                    } else {
                        Toast.makeText(UserActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            deleteBtn.setEnabled(false);
        }
    }

    public void onSaveClick(View view) {
        User user = new User(editName.getText().toString(),
                editEmail.getText().toString());
        if (bundle!=null){
            mDatabase.child(userId).setValue(user);
            Toast.makeText(this, "Changes succeed", Toast.LENGTH_SHORT).show();
        }else {
            mDatabase.push().setValue(user);
        }
        goHome();
    }

    public void onDeleteClick(View view) {
        mDatabase.child(userId).removeValue();
        Toast.makeText(this, "Delete succeed", Toast.LENGTH_SHORT).show();
        goHome();
    }

    private void goHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}