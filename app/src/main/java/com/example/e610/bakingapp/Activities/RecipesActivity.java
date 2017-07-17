package com.example.e610.bakingapp.Activities;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
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
import android.widget.Toast;

import com.example.e610.bakingapp.Adapters.RecipeAdapter;
import com.example.e610.bakingapp.Fragments.RecipesFragment;
import com.example.e610.bakingapp.IdlingResource.SimpleIdlingResource;
import com.example.e610.bakingapp.Models.Ingredient;
import com.example.e610.bakingapp.Models.Recipe;
import com.example.e610.bakingapp.Models.Step;
import com.example.e610.bakingapp.RecipeWidget.RecipeAppWidget;
import com.example.e610.bakingapp.R;
import com.example.e610.bakingapp.Utils.FetchData;
import com.example.e610.bakingapp.Utils.MySharedPreferences;
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

        InitializeGlobalVariables();

        addFragment(savedInstanceState);

        FetchDataFromInternet();

    }

    private void InitializeGlobalVariables() {

        Recipes=new ArrayList<>();
        recipesFragment=new RecipesFragment();
        // Get the IdlingResource instance
        getIdlingResource();
        fetchRecipeData=new FetchData(mIdlingResource);
    }

    private void addFragment(Bundle savedInstanceState) {

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().add(R.id.recipe_fragment,recipesFragment).commit();
        }
        else{
            recipesFragment=(RecipesFragment)getSupportFragmentManager().findFragmentById(R.id.recipe_fragment);
        }

    }


    private void FetchDataFromInternet() {
        if(NetworkState.ConnectionAvailable(RecipesActivity.this)) {
            fetchRecipeData.setNetworkResponse(this);
            fetchRecipeData.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        else
            Toast.makeText(RecipesActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void ItemClicked(View v, int position) {

        handleItemClickedEvent(position);

    }

    private void handleItemClickedEvent(int position) {

        Intent intent=new Intent(this,RecipeDetailedActivity.class);
        ArrayList<Step> steps=Recipes.get(position).getSteps();
        ArrayList<Ingredient> ingredients=Recipes.get(position).getIngredients();;
        Recipe recipe =Recipes.get(position);
        Bundle recipeBundle=new Bundle();

        recipeBundle.putParcelable("Recipe",recipe);
        recipeBundle.putParcelableArrayList("Ingredients",ingredients);
        recipeBundle.putParcelableArrayList("Steps",steps);
        intent.putExtra("recipeBundle",recipeBundle);

//save recipe sample to display it in widget
        MySharedPreferences.SaveLastRecipe(recipe);

        updateWidget();

        startActivity(intent);

        Toast.makeText(RecipesActivity.this,"Hello ^__^",Toast.LENGTH_SHORT).show();
    }

    private void updateWidget() {
        Intent in  = new Intent(RecipesActivity.this, RecipeAppWidget.class);
        //in.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        in.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int ids[] = AppWidgetManager.getInstance( getApplication()).getAppWidgetIds(new ComponentName(getApplication(), RecipeAppWidget.class));
        AppWidgetManager.getInstance(RecipesActivity.this).notifyAppWidgetViewDataChanged(ids,R.id.ingredients_listView_widget);
        in.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(in);
    }

    @Override
    public void OnSuccess(String JsonData) {

     try {
         Recipes = ParsingJson.ParseData(JsonData);

         setUpRecyclerView();

         //save recipe sample to display it in widget
         if (MySharedPreferences.IsFirstTime()) {
              MySharedPreferences.FirstTime();
              MySharedPreferences.SaveLastRecipe(Recipes.get(0));
          }

      }
     catch (Exception ex){
         Toast.makeText(RecipesActivity.this,"Internet is very weak",Toast.LENGTH_SHORT).show();
         Toast.makeText(RecipesActivity.this,"No Data received",Toast.LENGTH_SHORT).show();
       }
    }

    private void setUpRecyclerView() {

        recipeAdapter = new RecipeAdapter(Recipes, RecipesActivity.this);
        RecipeRecyclerView = (RecyclerView) findViewById(R.id.recipes_recyclerView);
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        if (isTablet)
            RecipeRecyclerView.setLayoutManager(new GridLayoutManager(RecipesActivity.this, 2));
        else
            RecipeRecyclerView.setLayoutManager(new GridLayoutManager(RecipesActivity.this, 1));

        RecipeRecyclerView.setAdapter(recipeAdapter);
        recipeAdapter.setClickListener(this);
    }

    @Override
    public void OnFailure(boolean Failure) {

        if(Failure){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(RecipesActivity.this, "Internet Connection is very weak", Toast.LENGTH_SHORT).show();
                    Toast.makeText(RecipesActivity.this, "No Data Received", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList("Recipes",Recipes);
    }
}
