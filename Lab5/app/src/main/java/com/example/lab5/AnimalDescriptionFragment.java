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
            String animalDesc = "";

            if( animal == "Собака") {
                animalDesc = "Верный друг";
            } else if (animal == "Кот") {
                animalDesc =  "Котик- это котик";
            } else if (animal == "Морская свинка") {
                animalDesc = "Забавный зверек";
            } else if (animal == "Единорог") {
                animalDesc = "Увы их нет.. наверное";
            } else {
                animalDesc = " ";
            }

            animalDescription.setText("Описание: " + animalDesc);
        }

        return view;
    }
}
