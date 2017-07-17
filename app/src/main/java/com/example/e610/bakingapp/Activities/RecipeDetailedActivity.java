package com.example.e610.bakingapp.Activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e610.bakingapp.Adapters.IngredientStepAdapter;
import com.example.e610.bakingapp.Fragments.IngredientFragment;
import com.example.e610.bakingapp.Fragments.RecipeDetailedFragment;
import com.example.e610.bakingapp.Fragments.StepFragment;
import com.example.e610.bakingapp.Models.Ingredient;
import com.example.e610.bakingapp.Models.Recipe;
import com.example.e610.bakingapp.Models.Step;
import com.example.e610.bakingapp.R;
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
    private RecipeDetailedFragment  recipeDetailedFragment;
    private IngredientFragment ingredientFragment;
    private StepFragment stepFragment;
    private boolean isTablet ;
    private TextView recipeTextName;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detailed);

        InitializeGlobalVariables();

        addFragment(savedInstanceState);

    }

    private void InitializeGlobalVariables() {

        isTablet=getResources().getBoolean(R.bool.isTablet);
        recipeBundle=getIntent().getBundleExtra("recipeBundle");
        recipe=recipeBundle.getParcelable("Recipe");
        steps=recipeBundle.getParcelableArrayList("Steps");
        ingredients=recipeBundle.getParcelableArrayList("Ingredients");

        ingredientStepObjects=new ArrayList<>();
        ingredientStepObjects.add("Ingredients");
        ingredientStepObjects.addAll(steps);
        recipeDetailedFragment=new RecipeDetailedFragment();
    }

    private void addFragment(Bundle savedInstanceState) {

        if(savedInstanceState==null){
            if(!isTablet)
                 getSupportFragmentManager().beginTransaction().add(R.id.recipe_detailed_fragment,recipeDetailedFragment).commit();
            else
            {
                getSupportFragmentManager().beginTransaction().add(R.id.recipe_detailed_fragment,recipeDetailedFragment).commit();
                ingredientFragment=new IngredientFragment();
                Bundle bundle=new Bundle();
                bundle.putParcelableArrayList("Ingredients",ingredients);
                its_Tablet(bundle,ingredientFragment,R.id.step_fragment);
            }
        }
        else{
            recipeDetailedFragment=( RecipeDetailedFragment) getSupportFragmentManager().findFragmentById(R.id.recipe_detailed_fragment);
        }
    }

    @Override
    public void sendView(View view) {

        recipeTextName=(TextView) view.findViewById(R.id.recipe_text_name);
        recipeTextName.setText(recipe.getName());
        setUpRecyclerView(view);
    }

    private void setUpRecyclerView(View view) {

        ingredientStepAdapter=new IngredientStepAdapter(ingredientStepObjects,RecipeDetailedActivity.this);
        recipeDetailedRecyclerView=(RecyclerView)view.findViewById(R.id.ingredient_steps_recyclerView);
        recipeDetailedRecyclerView.setLayoutManager(new GridLayoutManager(RecipeDetailedActivity.this,1));
        recipeDetailedRecyclerView.setAdapter(ingredientStepAdapter);
        ingredientStepAdapter.setClickListener(this);
    }

    @Override
    public void ItemClicked(View v, int position) {

       handleItemClickedEvent(position);

    }

    private void handleItemClickedEvent(int position) {


        Intent intent;
        Bundle bundle=new Bundle();

        if(position==0){

            bundle.putParcelableArrayList("Ingredients",ingredients);

            if(!isTablet) {
                intent = new Intent(this, IngredientActivity.class);
                intent.putExtra("IngredientBundle", bundle);
                startActivity(intent);
            }
            else
            {
                ingredientFragment=new IngredientFragment();
                its_Tablet(bundle,ingredientFragment,R.id.step_fragment);
            }
            Toast.makeText(RecipeDetailedActivity.this,"ingredient ^_^" ,Toast.LENGTH_SHORT).show();
        }

        else {

            bundle.putParcelable("Step",steps.get(position-1));
            bundle.putParcelableArrayList("steps",steps);
            bundle.putInt("currentIndex",position-1);

            if(!isTablet) {
                intent = new Intent(this, StepActivity.class);
                intent.putExtra("StepBundle", bundle);
                startActivity(intent);
            }
            else{

                stepFragment=new StepFragment();
                its_Tablet(bundle,stepFragment,R.id.step_fragment);

            }

            Toast.makeText(RecipeDetailedActivity.this,"Step ^_^" ,Toast.LENGTH_SHORT).show();
        }

    }

    private void its_Tablet(Bundle bundle, Fragment fragment, int fragmentIDContainer) {
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(fragmentIDContainer,fragment).commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBundle("recipeBundle",recipeBundle);
    }
}
