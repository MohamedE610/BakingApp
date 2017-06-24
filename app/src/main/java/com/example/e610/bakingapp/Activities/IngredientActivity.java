package com.example.e610.bakingapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.e610.bakingapp.Fragments.IngredientFragment;
import com.example.e610.bakingapp.R;
import com.example.e610.bakingapp.Utils.SendViewToActivity;

public class IngredientActivity extends AppCompatActivity implements SendViewToActivity {

    //Global Variable
    /****************************/
   // private TextView ingredientText;
   // private String ingredientStr="";
    /****************************/
    private IngredientFragment  ingredientFragment;
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);
        bundle=new Bundle();
        bundle=getIntent().getBundleExtra("IngredientBundle");
        /****************************/
         // ingredientStr=bundle.getString("IngredientStr");
        /****************************/
        ingredientFragment=new IngredientFragment();
        ingredientFragment.setArguments(bundle);

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().add(R.id.IngredientFragment,ingredientFragment).commit();
        }
        else{
            ingredientFragment=(IngredientFragment) getSupportFragmentManager().findFragmentById(R.id.IngredientFragment);
        }

    }

    @Override
    public void sendView(View view) {

/****************************/
       // ingredientText=(TextView) view.findViewById(R.id.IngredientText);
       // ingredientText.setText(ingredientStr);
/****************************/

    }
}
