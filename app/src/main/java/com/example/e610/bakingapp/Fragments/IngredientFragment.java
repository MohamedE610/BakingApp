package com.example.e610.bakingapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.e610.bakingapp.R;
import com.example.e610.bakingapp.Utils.SendViewToActivity;

public class IngredientFragment extends Fragment {

     //Global Variable
    private TextView ingredientText;
    private String ingredientStr="";
    private Bundle bundle;

    /****************************/
    //SendViewToActivity sendViewToActivity;
    /****************************/

     public IngredientFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_ingredient, container, false);
        /****************************/
        // sendViewToActivity= (SendViewToActivity) getActivity();
        //sendViewToActivity.sendView(view);
        /****************************/

        bundle=getArguments();
        ingredientStr=bundle.getString("IngredientStr");
        ingredientText=(TextView) view.findViewById(R.id.IngredientText);
        ingredientText.setText(ingredientStr);



        return view ;
    }



}
