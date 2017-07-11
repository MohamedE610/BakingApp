package com.example.e610.bakingapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.e610.bakingapp.Models.Ingredient;
import com.example.e610.bakingapp.Models.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class MySharedPreferences {




    static Context context;
    static String FileName;
    static SharedPreferences sharedPref;
    static SharedPreferences.Editor editor;

    public static void setUpMySharedPreferences(Context context_,String FileName_){
         context=context_;
        FileName=FileName_;
        sharedPref = context.getSharedPreferences(FileName, Context.MODE_PRIVATE);
        editor=sharedPref.edit();
    }
    //"widgetRecipe"
    public static void SaveLastRecipe(Recipe recipe){

        Gson gson = new Gson();

        String json = gson.toJson(recipe);
        Log.d("gason", json);
        editor.putString("TheLast",json);
        editor.commit();

    }

    public static Recipe RetriveLastRecipe(){

        Gson gson = new Gson();
        String json = sharedPref.getString("TheLast","");

        if(json.equals(""))
            return null;
        Recipe recipe = gson.fromJson(json, Recipe.class);

        return recipe ;
    }

    public static boolean IsFirstTime(){
        String check=sharedPref.getString("FirstTime","");

        if(check.equals("yes"))
            return false;
         return true;
    }

    public static void FirstTime(){
        editor.putString("FirstTime","yes");
        editor.commit();
    }

    public void Clear(){
        editor.clear();
        editor.commit();
    }

}
