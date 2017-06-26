package com.example.e610.bakingapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.e610.bakingapp.Fragments.IngredientFragment;
import com.example.e610.bakingapp.Fragments.StepFragment;
import com.example.e610.bakingapp.R;

public class StepActivity extends AppCompatActivity {

    //Global Variable
    private StepFragment stepFragment;
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        bundle=new Bundle();
        bundle=getIntent().getBundleExtra("StepBundle");
        stepFragment=new StepFragment();
        stepFragment.setArguments(bundle);

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().add(R.id.StepFragment,stepFragment).commit();
        }
        else{
            stepFragment=(StepFragment) getSupportFragmentManager().findFragmentById(R.id.StepFragment);
        }


    }
}
