package com.example.e610.bakingapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

        InitializeGlobalVariables();

        addFragment(savedInstanceState);

    }

    private void InitializeGlobalVariables() {

        bundle=new Bundle();
        bundle=getIntent().getBundleExtra("StepBundle");
        stepFragment=new StepFragment();
        stepFragment.setArguments(bundle);
    }

    private void addFragment(Bundle savedInstanceState) {

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().add(R.id.step_fragment,stepFragment).commit();
        }
        else{
            stepFragment=(StepFragment) getSupportFragmentManager().findFragmentById(R.id.step_fragment);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBundle("StepBundle",bundle);
    }
}
