package com.example.lab3;


import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("John Nolan", "25253", "john@example.com"));
        contacts.add(new Contact("Angela Lopez", "1777", "ariana@example.com"));
        contacts.add(new Contact("Wade Grey", "6325", "tom@example.com"));
        contacts.add(new Contact("Lucy Chen", "28537", "ivan@example.com"));
        contacts.add(new Contact("Tim Bradford", "3483", "vanessa@example.com"));
        contacts.add(new Contact("Nyla Harper", "56464", "vanessa@example.com"));


        ContactsAdapter adapter = new ContactsAdapter(this, contacts);
        recyclerView.setAdapter(adapter);
    }
}