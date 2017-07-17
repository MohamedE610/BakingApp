package com.example.e610.bakingapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.e610.bakingapp.Adapters.IngredientAdapter;
import com.example.e610.bakingapp.Models.Ingredient;
import com.example.e610.bakingapp.R;

import java.util.ArrayList;

public class IngredientFragment extends Fragment {

     //Global Variable
    private RecyclerView IngredientRecyclerView;
    private ArrayList<Ingredient> Ingredients;
    private IngredientAdapter ingredientAdapter;
    private Bundle bundle;


     public IngredientFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_ingredient, container, false);

        setUpRecyclerView(view);

        return view ;
    }

    private void setUpRecyclerView(View view) {

        bundle=getArguments();
        Ingredients=bundle.getParcelableArrayList("Ingredients");
        ingredientAdapter=new IngredientAdapter(Ingredients,getContext());
        IngredientRecyclerView=(RecyclerView)view.findViewById(R.id.ingredient_recyclerView);
        IngredientRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        IngredientRecyclerView.setAdapter(ingredientAdapter);

    }


}
