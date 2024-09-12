package com.example.lab7_1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.QueryDocumentSnapshot;


public class ChoiceDatabase extends AppCompatActivity {

    private RadioButton  radioFirestore, radioRealtime;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_database);

        radioFirestore = findViewById(R.id.radiobtnFirestore);
        radioRealtime = findViewById(R.id.radiobtnRealtime);
        buttonSubmit = findViewById(R.id.btnSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioFirestore.isChecked()) {
                    useFirestore();
                } else if (radioRealtime.isChecked()){
                    useRealtimeDatabase();
                } else {
                    Toast.makeText(ChoiceDatabase.this, "Choose Database", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void useFirestore() {
        Intent intent = new Intent(ChoiceDatabase.this, UserFirestoreActivity.class);
        startActivity(intent);
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("users")
//                .add(new User("UserFirestore","eMailFirestore"))
//                .addOnSuccessListener(documentReference -> {
//                })
//                .addOnFailureListener(e -> {
//                });
    }

    private void useRealtimeDatabase() {
        Intent intent = new Intent(ChoiceDatabase.this, UserActivity.class);
        startActivity(intent);

//        DatabaseReference database = FirebaseDatabase.getInstance().getReference("users");
//        User data = new User("UserRealTimeDB","eMailRealTimeDB");
//        database.push().setValue(data)
//                .addOnSuccessListener(aVoid -> {
//                    Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
//                })
//                .addOnFailureListener(e -> {
//                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
//                });
    }
    private void fetchFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Обработка данных документа
                            Log.d("Firestore", document.getId() + " => " + document.getData());
                        }
                    } else {
                        Log.w("Firestore", "Error in docs", task.getException());
                    }
                });
    }
}

