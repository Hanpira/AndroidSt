package com.example.lab5;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AnimalDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_detail);

        if (savedInstanceState == null) {
            String animal = getIntent().getStringExtra("animal");
            AnimalDescriptionFragment descriptionFragment = new AnimalDescriptionFragment();
            Bundle args = new Bundle();
            args.putString("animal", animal);
            descriptionFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, descriptionFragment)
                    .commit();
        }
    }
}