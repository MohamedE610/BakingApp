package com.example.e610.bakingapp.Activities;

import android.content.Intent;
import android.support.annotation.BoolRes;
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

import com.example.e610.bakingapp.Adapters.IngredientStepAdapter;
import com.example.e610.bakingapp.Adapters.RecipeAdapter;
import com.example.e610.bakingapp.Fragments.IngredientFragment;
import com.example.e610.bakingapp.Fragments.RecipeDetailedFragment;
import com.example.e610.bakingapp.Fragments.RecipesFragment;
import com.example.e610.bakingapp.Fragments.StepFragment;
import com.example.e610.bakingapp.IdlingResource.SimpleIdlingResource;
import com.example.e610.bakingapp.Models.Ingredient;
import com.example.e610.bakingapp.Models.Recipe;
import com.example.e610.bakingapp.Models.Step;
import com.example.e610.bakingapp.R;
import com.example.e610.bakingapp.Utils.FetchData;
import com.example.e610.bakingapp.Utils.SendViewToActivity;

import java.util.ArrayList;

public class RecipeDetailedActivity extends AppCompatActivity
                                   implements IngredientStepAdapter.RecyclerViewClickListener,SendViewToActivity {


    //Global Variables

    private Recipe recipe;
    private RecyclerView recipeDetailedRecyclerView;
    private ArrayList<Object> ingredientStepObjects;
    private IngredientStepAdapter  ingredientStepAdapter;
    private ArrayList<Step> steps;
    private ArrayList<Ingredient> ingredients;
    private Bundle recipeBundle;
    private String ingredientStr="";
    private RecipeDetailedFragment  recipeDetailedFragment;
    private IngredientFragment ingredientFragment;
    private StepFragment stepFragment;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detailed);


        recipeBundle=getIntent().getBundleExtra("recipeBundle");
        recipe=recipeBundle.getParcelable("Recipe");
        steps=recipeBundle.getParcelableArrayList("Steps");
        ingredients=recipeBundle.getParcelableArrayList("Ingredients");

        ingredientStepObjects=new ArrayList<>();
        ingredientStepObjects.add("Ingredients");
        ingredientStepObjects.addAll(steps);
        recipeDetailedFragment=new RecipeDetailedFragment();

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().add(R.id.RecipeDetailedFragment,recipeDetailedFragment).commit();
        }
        else{
            recipeDetailedFragment=( RecipeDetailedFragment) getSupportFragmentManager().findFragmentById(R.id.RecipeDetailedFragment);
        }
    }

    @Override
    public void sendView(View view) {

        ingredientStepAdapter=new IngredientStepAdapter(ingredientStepObjects,RecipeDetailedActivity.this);
        recipeDetailedRecyclerView=(RecyclerView)view.findViewById(R.id.IngredientStepsRecyclerView);
        recipeDetailedRecyclerView.setLayoutManager(new GridLayoutManager(RecipeDetailedActivity.this,1));
        recipeDetailedRecyclerView.setAdapter(ingredientStepAdapter);
        ingredientStepAdapter.setClickListener(this);

    }

    @Override
    public void ItemClicked(View v, int position) {

        Intent intent;
        Bundle bundle=new Bundle();

        isTablet=getResources().getBoolean(R.bool.isTablet);

        if(position==0){
/*

            ingredientStr="";
            int index=0;
            for(Ingredient ingredient : ingredients){
                ingredientStr+="ingredient "+index+" is :"+"\n"+"    quantity = "+ingredient.getQuantity()
                        +"\n"+"    measure = "+ingredient.getMeasure()+"\n"+"    ingredient = "+ingredient.getName()+"\n"+"\n";
                index++;
            }

*/
            bundle.putParcelableArrayList("Ingredients",ingredients);

            if(!isTablet) {
                intent = new Intent(this, IngredientActivity.class);
                intent.putExtra("IngredientBundle", bundle);
                startActivity(intent);
            }
            else
            {
                ingredientFragment=new IngredientFragment();
                ingredientFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.StepFragment,ingredientFragment).commit();
            }
            Toast.makeText(RecipeDetailedActivity.this,"ingredient ^_^" ,Toast.LENGTH_SHORT).show();
        }

        else {

            bundle.putParcelable("Step",steps.get(position-1));
            bundle.putParcelableArrayList("steps",steps);
            if(!isTablet) {
                intent = new Intent(this, StepActivity.class);
                intent.putExtra("StepBundle", bundle);
                startActivity(intent);
            }
            else{
                stepFragment=new StepFragment();
                stepFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.StepFragment,stepFragment).commit();
            }

            Toast.makeText(RecipeDetailedActivity.this,"Step ^_^" ,Toast.LENGTH_SHORT).show();
        }

    }
 boolean isTablet ;

}
