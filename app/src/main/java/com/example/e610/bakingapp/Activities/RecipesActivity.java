package com.example.e610.bakingapp.Activities;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e610.bakingapp.Adapters.RecipeAdapter;
import com.example.e610.bakingapp.Fragments.RecipesFragment;
import com.example.e610.bakingapp.IdlingResource.SimpleIdlingResource;
import com.example.e610.bakingapp.Models.Ingredient;
import com.example.e610.bakingapp.Models.Recipe;
import com.example.e610.bakingapp.Models.Step;
import com.example.e610.bakingapp.NewAppWidget;
import com.example.e610.bakingapp.R;
import com.example.e610.bakingapp.Utils.FetchData;
import com.example.e610.bakingapp.Utils.MySharedPreferences;
import com.example.e610.bakingapp.Utils.NetworkResponse;
import com.example.e610.bakingapp.Utils.NetworkState;
import com.example.e610.bakingapp.Utils.ParsingJson;

import java.util.ArrayList;

public class RecipesActivity extends AppCompatActivity implements RecipeAdapter.RecyclerViewClickListener , NetworkResponse {


    //Global Variables

    private int Index=0;
    private  RecyclerView RecipeRecyclerView;
    private  ArrayList<Recipe> Recipes;
    private  RecipeAdapter recipeAdapter;
    private  FetchData fetchRecipeData;
    private  RecipesFragment recipesFragment;



    // The Idling Resource which will be null in production.
    @Nullable
    private SimpleIdlingResource mIdlingResource;

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        MySharedPreferences.setUpMySharedPreferences(this,"widgetRecipe");
        Recipes=new ArrayList<>();
        recipesFragment=null;
        recipesFragment=new RecipesFragment();


        // Get the IdlingResource instance
        getIdlingResource();

        fetchRecipeData=new FetchData(mIdlingResource);


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

        Intent intent=new Intent(this,RecipeDetailedActivity.class);
        ArrayList<Step> steps=Recipes.get(position).getSteps();
        ArrayList<Ingredient> ingredients=Recipes.get(position).getIngredients();;
        Recipe recipe =Recipes.get(position);
        Bundle recipeBundle=new Bundle();

        recipeBundle.putParcelable("Recipe",recipe);
        recipeBundle.putParcelableArrayList("Ingredients",ingredients);
        recipeBundle.putParcelableArrayList("Steps",steps);
        intent.putExtra("recipeBundle",recipeBundle);

        MySharedPreferences.SaveLastRecipe(recipe);

        Intent in  = new Intent(RecipesActivity.this, NewAppWidget.class);
        in.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        int ids[] = AppWidgetManager.getInstance( getApplication()).getAppWidgetIds(new ComponentName(getApplication(), NewAppWidget.class));
        AppWidgetManager.getInstance(RecipesActivity.this).notifyAppWidgetViewDataChanged(ids,R.id.IngredientsRecyclerViewWidget);
        in.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(in);

        startActivity(intent);

        Toast.makeText(RecipesActivity.this,"Hello ^__^",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnSuccess(String JsonData) {

     try {
         Recipes = ParsingJson.PasreData(JsonData);
         recipeAdapter = new RecipeAdapter(Recipes, RecipesActivity.this);
         //recipeAdapter.setClickListener(this);
         RecipeRecyclerView = (RecyclerView) findViewById(R.id.RecipesRecyclerView);
         boolean isTablet = getResources().getBoolean(R.bool.isTablet);
         if (isTablet)
             RecipeRecyclerView.setLayoutManager(new GridLayoutManager(RecipesActivity.this, 2));
         else
             RecipeRecyclerView.setLayoutManager(new GridLayoutManager(RecipesActivity.this, 1));

         RecipeRecyclerView.setAdapter(recipeAdapter);
         recipeAdapter.setClickListener(this);

         if (MySharedPreferences.IsFirstTime()) {
             MySharedPreferences.FirstTime();
             MySharedPreferences.SaveLastRecipe(Recipes.get(0));
         }
/*
        recipeAdapter.setClickListener(new RecipeAdapter.RecyclerViewClickListener() {
            @Override
            public void ItemClicked(View v, int position) {
                Toast.makeText(RecipesActivity.this,"^__^",Toast.LENGTH_SHORT).show();
            }
        });
*/

      }
     catch (Exception ex){
         Toast.makeText(RecipesActivity.this,"Internet is very weak",Toast.LENGTH_SHORT);
       }
    }

    @Override
    public void OnUpdate(boolean IsDataReceived) {

    }
}
