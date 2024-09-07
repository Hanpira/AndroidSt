package com.example.lab5;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class AnimalDescriptionFragment extends Fragment {

    private TextView animalDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_animal_description, container, false);
        animalDescription = view.findViewById(R.id.animal_description);

        if (getArguments() != null) {
            String animal = getArguments().getString("animal");
            animalDescription.setText("Описание: " + animal);
        }

        return view;
    }
}
