package com.example.e610.bakingapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e610.bakingapp.R;
import com.example.e610.bakingapp.Utils.SendViewToActivity;

public class RecipeDetailedFragment extends Fragment {

     SendViewToActivity sendViewToActivity;
    public RecipeDetailedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_recipe_detailed, container, false);

        sendViewToActivity= (SendViewToActivity) getActivity();
        sendViewToActivity.sendView(view);

        return view ;
    }

}
