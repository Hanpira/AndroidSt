package com.example.lab5;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AnimalsFragment extends Fragment {

    private ListView animalsListView;
    private String[] animals = {"Собака", "Кот", "Морская свинка", "Единорог"};
    private OnAnimalSelectedListener listener;

    public interface OnAnimalSelectedListener {
        void onAnimalSelected(String animal);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAnimalSelectedListener) {
            listener = (OnAnimalSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement OnAnimalSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_animals, container, false);
        animalsListView = view.findViewById(R.id.animals_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, animals);
        animalsListView.setAdapter(adapter);

        animalsListView.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedAnimal = animals[position];
            listener.onAnimalSelected(selectedAnimal);
        });

        return view;
    }
}