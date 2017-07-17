package com.example.e610.bakingapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.example.e610.bakingapp.Fragments.IngredientFragment;
import com.example.e610.bakingapp.R;


public class IngredientActivity extends AppCompatActivity  {

    //Global Variable
    private IngredientFragment  ingredientFragment;
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        InitializeGlobalVariables();

        addFragment(savedInstanceState);

    }

    private void InitializeGlobalVariables() {

        bundle=new Bundle();
        ingredientFragment=new IngredientFragment();
        bundle=getIntent().getBundleExtra("IngredientBundle");
        ingredientFragment.setArguments(bundle);
    }


    private void addFragment(Bundle savedInstanceState) {

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().add(R.id.ingredient_fragment,ingredientFragment).commit();
        }
        else{
            ingredientFragment=(IngredientFragment) getSupportFragmentManager().findFragmentById(R.id.ingredient_fragment);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBundle("IngredientBundle",bundle);
    }
}
