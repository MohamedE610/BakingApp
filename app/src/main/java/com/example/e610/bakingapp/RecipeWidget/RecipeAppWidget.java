package com.example.e610.bakingapp.RecipeWidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.e610.bakingapp.Activities.RecipesActivity;
import com.example.e610.bakingapp.Models.Recipe;
import com.example.e610.bakingapp.R;
import com.example.e610.bakingapp.Utils.MySharedPreferences;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText="";
        MySharedPreferences.setUpMySharedPreferences(context,"widgetRecipe");
        Recipe recipe= MySharedPreferences.RetriveLastRecipe();
      if(recipe!=null)
            widgetText =recipe.getName()+" ^_^ ";
        else
          widgetText ="No Data Available yet"+" ^_^ ";
        Log.d("hahaWidget",widgetText.toString());
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);
        if(recipe!=null){
            Intent in = new Intent(context, IngredientListWidgetService.class);
            views.setRemoteAdapter(R.id.ingredients_listView_widget, in);
        }

        Intent  intent = new Intent(context,  RecipesActivity.class);
        PendingIntent  pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

