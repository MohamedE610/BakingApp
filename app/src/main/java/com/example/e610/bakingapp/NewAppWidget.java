package com.example.e610.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.e610.bakingapp.Activities.RecipeDetailedActivity;
import com.example.e610.bakingapp.Activities.RecipesActivity;
import com.example.e610.bakingapp.Fragments.RecipeDetailedFragment;
import com.example.e610.bakingapp.Utils.MySharedPreferences;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        MySharedPreferences.setUpMySharedPreferences(context,"widgetRecipe");

        CharSequence widgetText = MySharedPreferences.RetriveLastRecipe().getName()+" ^_^ ";
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

       Intent in = new Intent(context, GridWidgetService.class);
        views.setRemoteAdapter(R.id.IngredientsRecyclerViewWidget, in);

        Intent  intent = new Intent(context,  RecipesActivity.class);
        PendingIntent  pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);


        //appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.IngredientsRecyclerViewWidget);
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

