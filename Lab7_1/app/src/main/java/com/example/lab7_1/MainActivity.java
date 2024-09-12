package com.example.lab7_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    private TextView title;
    TextView textUserInfo, textUserName;
    ListView listUsers;
    List<String> uids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        title = findViewById(R.id.titleMain);
        textUserInfo = findViewById(R.id.textUserInfo);
        textUserName = findViewById(R.id.textUserName);
        auth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance("https://authentication-b4c80-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        title.setText(auth.getCurrentUser().getEmail());
        textUserInfo.setText(auth.getCurrentUser().getEmail().toString());
        textUserName.setText(auth.getCurrentUser().getDisplayName().toString());


        listUsers = findViewById(R.id.listUsers);
        listUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                intent.putExtra("id", uids.get(position));
                startActivity(intent);
            }
        });
    }

    public void onSignOut (View view) {

//        User testUser = new User( "username", "email");
//        mDatabase.push().setValue(testUser);

        auth.signOut();

        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Toast.makeText(MainActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        startActivity(new Intent(this, SignIn.class));
    }

    @Override
    protected void onStart() {
        super.onStart();

        mDatabase.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            List<String> dataList = new ArrayList<>();
            uids = new ArrayList<>();
            for (DataSnapshot ds : snapshot.getChildren()) {
                uids.add(ds.getKey());
                dataList.add(ds.child("email").getValue(String.class) + " " +
                        ds.child("username").getValue(String.class));
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this,
                    android.R.layout.simple_list_item_1, dataList);
            listUsers.setAdapter(arrayAdapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };



//    public void onAddClick(View view) {
//        Intent intent = new Intent(getApplicationContext(), UserActivity.class);
//        startActivity(intent);
//    }
//
//    public void onFirestoreClick(View view) {
//        Intent intent = new Intent(getApplicationContext(), Firestore.class);
//        startActivity(intent);
//    }

    public void ChooseDb(View view) {
        Intent intent = new Intent(getApplicationContext(), ChoiceDatabase.class);
        startActivity(intent);
    }


}