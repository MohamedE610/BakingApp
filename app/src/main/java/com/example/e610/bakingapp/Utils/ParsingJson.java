package com.example.e610.bakingapp.Utils;

import com.example.e610.bakingapp.Models.Ingredient;
import com.example.e610.bakingapp.Models.Recipe;
import com.example.e610.bakingapp.Models.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by E610 on 6/19/2017.
 */
public class ParsingJson {

    public static ArrayList<Recipe> PasreData(String json){
        ArrayList<Recipe> recipes =new ArrayList<>();

        try {

            JSONArray jsonArray=new JSONArray(json);
           for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonRecipe=(JSONObject)jsonArray.get(i);
                Recipe recipe=new Recipe();
                recipe.setId(jsonRecipe.getString("id"));
                recipe.setName(jsonRecipe.getString("name"));
                recipe.setIngredients(ParseIngredientData(json,i));
                recipe.setSteps(ParseStepData(json,i));
                recipe.setServings(jsonRecipe.getString("servings"));
                recipe.setImage(jsonRecipe.getString("image"));

               recipes.add(recipe);

           }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        return recipes;
    }

    private static  ArrayList<Step> ParseStepData(String json, int i) {
        ArrayList<Step>  steps =new ArrayList<>();

        try {

            JSONArray jsonArray=new JSONArray(json);
            JSONObject jsonRecipe=(JSONObject)jsonArray.get(i);
            JSONArray jsonSteps=jsonRecipe.getJSONArray("steps");
            for(int j=0;j<jsonSteps.length();j++){
                JSONObject jsonStep=(JSONObject)jsonSteps.get(j);
                Step step=new Step();
                step.setId(jsonStep.getString("id"));
                step.setShortDescription(jsonStep.getString("shortDescription"));
                step.setDescription(jsonStep.getString("description"));
                step.setVideoURL(jsonStep.getString("videoURL"));
                step.setThumbnailURL(jsonStep.getString("thumbnailURL"));

                steps.add(step);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        return steps;
    }

    private static ArrayList<Ingredient> ParseIngredientData(String json, int i) {

        ArrayList<Ingredient> ingredients =new ArrayList<>();

        try {

            JSONArray jsonArray=new JSONArray(json);
            JSONObject jsonRecipe=(JSONObject)jsonArray.get(i);
            JSONArray jsonIngredients=jsonRecipe.getJSONArray("ingredients");
            for(int j=0;j<jsonIngredients.length();j++){
                JSONObject jsonIngredient=(JSONObject)jsonIngredients.get(j);
                Ingredient ingredient=new Ingredient();
                ingredient.setQuantity(jsonIngredient.getString("quantity"));
                ingredient.setMeasure(jsonIngredient.getString("measure"));
                ingredient.setName(jsonIngredient.getString("ingredient"));

                ingredients.add(ingredient);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ingredients;
    }
}
