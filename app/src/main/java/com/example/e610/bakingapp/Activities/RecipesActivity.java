package com.example.e610.bakingapp.Activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.e610.bakingapp.Adapters.RecipeAdapter;
import com.example.e610.bakingapp.Fragments.RecipesFragment;
import com.example.e610.bakingapp.Models.Recipe;
import com.example.e610.bakingapp.R;
import com.example.e610.bakingapp.Utils.FetchData;
import com.example.e610.bakingapp.Utils.NetworkResponse;
import com.example.e610.bakingapp.Utils.NetworkState;
import com.example.e610.bakingapp.Utils.ParsingJson;

import java.util.ArrayList;

public class RecipesActivity extends AppCompatActivity implements RecipeAdapter.RecyclerViewClickListener , NetworkResponse {


    //Global Variables

    private  RecyclerView RecipeRecyclerView;
    private  ArrayList<Recipe> Recipes;
    private  RecipeAdapter recipeAdapter;
    private  FetchData fetchRecipeData;
    private  RecipesFragment recipesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Recipes=new ArrayList<>();
        fetchRecipeData=new FetchData();
        recipesFragment=null;
        recipesFragment=new RecipesFragment();

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().add(R.id.RecipeFragment,recipesFragment).commit();
        }
        else{
             recipesFragment=(RecipesFragment)getSupportFragmentManager().findFragmentById(R.id.RecipeFragment);
        }

        if(NetworkState.ConnectionAvailable(RecipesActivity.this)) {
            fetchRecipeData.setNetworkResponse(this);
            fetchRecipeData.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }



    }

    @Override
    public void ItemClicked(View v, int position) {

        Toast.makeText(RecipesActivity.this,"Hello ^__^",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void OnSuccess(String JsonData) {

        Recipes = ParsingJson.PasreData(JsonData);
        recipeAdapter = new RecipeAdapter(Recipes, RecipesActivity.this);
        recipeAdapter.setClickListener(this);
        RecipeRecyclerView=(RecyclerView)findViewById(R.id.RecipesRecyclerView);
        RecipeRecyclerView.setLayoutManager(new GridLayoutManager(RecipesActivity.this,1));
        RecipeRecyclerView.setAdapter(recipeAdapter);
        recipeAdapter.setClickListener(this);
        recipeAdapter.setClickListener(new RecipeAdapter.RecyclerViewClickListener() {
            @Override
            public void ItemClicked(View v, int position) {
                Toast.makeText(RecipesActivity.this,"^__^",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void OnUpdate(boolean IsDataReceived) {

    }
}