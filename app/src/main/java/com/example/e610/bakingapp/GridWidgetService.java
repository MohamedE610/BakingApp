package com.example.e610.bakingapp;

/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.e610.bakingapp.Models.Recipe;
import com.example.e610.bakingapp.Utils.MySharedPreferences;


public class GridWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext());
    }
}

class  GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;

    Recipe recipe=null;

    public GridRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;
        MySharedPreferences.setUpMySharedPreferences(mContext,"widgetRecipe");
    }

    @Override
    public void onCreate() {
       // MySharedPreferences.setUpMySharedPreferences(mContext,"widgetRecipe");
        //recipe=MySharedPreferences.RetriveLastRecipe();
    }

    //called on start and when notifyAppWidgetViewDataChanged is called
    @Override
    public void onDataSetChanged() {
       // MySharedPreferences.setUpMySharedPreferences(mContext,"widgetRecipe");
        recipe=MySharedPreferences.RetriveLastRecipe();

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return recipe.getIngredients().size();
    }

    /**
     * This method acts like the onBindViewHolder method in an Adapter
     *
     * @param position The current position of the item in the GridView to be displayed
     * @return The RemoteViews object to display for the provided postion
     */
    @Override
    public RemoteViews getViewAt(int position) {

        if(recipe==null) {
            recipe = MySharedPreferences.RetriveLastRecipe();
            Log.d("haha","hhhhhhhhhhhhhhhhhhhhhhhhhhhh");
        }

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_item);

        if(recipe!=null) {
            Log.d("haha",recipe.getIngredients().get(position).getName());
            views.setTextViewText(R.id.ingredient_name_widget, recipe.getIngredients().get(position).getName());
            views.setTextViewText(R.id.ingredient_measure_widget, recipe.getIngredients().get(position).getMeasure());
            views.setTextViewText(R.id.ingredient_quantity_widget, recipe.getIngredients().get(position).getQuantity());
        }
        else
        {
            views.setTextViewText(R.id.ingredient_name_widget, "Empty");
            views.setTextViewText(R.id.ingredient_measure_widget, "Empty");
            views.setTextViewText(R.id.ingredient_quantity_widget, "Empty");
        }
        return views;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1; // Treat all items in the GridView the same
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}

