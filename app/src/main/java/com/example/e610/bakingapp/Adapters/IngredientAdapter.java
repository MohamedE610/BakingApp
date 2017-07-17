package com.example.e610.bakingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.e610.bakingapp.Models.Ingredient;
import com.example.e610.bakingapp.R;

import java.util.ArrayList;


public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.MyViewHolder>  {
    ArrayList<Ingredient>  ingredients;
    Context context;
    int  LastPosition=-1;
    RecyclerViewClickListener ClickListener ;
    public IngredientAdapter(){}
    public IngredientAdapter(ArrayList<Ingredient>  ingredients, Context context){
        this.ingredients=new ArrayList<>();
        this.ingredients=ingredients;
        this.context=context;
    }

    public void setClickListener(RecyclerViewClickListener clickListener){
       this.ClickListener= clickListener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item_and_item_widget,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.ingredientName.setText(ingredients.get(position).getName());
        holder.ingredientQuantity.setText(ingredients.get(position).getQuantity());
        holder.ingredientMeasure.setText(ingredients.get(position).getMeasure());
        setAnimation(holder.ingredientInfoContainer,position);

    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView ingredientName;
        TextView ingredientMeasure;
        TextView ingredientQuantity;
        LinearLayout ingredientInfoContainer;
        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ingredientName=(TextView)itemView.findViewById(R.id.ingredient_name_widget);
            ingredientMeasure=(TextView)itemView.findViewById(R.id.ingredient_measure_widget);
            ingredientQuantity=(TextView)itemView.findViewById(R.id.ingredient_quantity_widget);
            ingredientInfoContainer=(LinearLayout)itemView.findViewById(R.id.ingredientInfoContainer );
        }

        @Override
        public void onClick(View view) {
            if(ClickListener!=null)
            ClickListener.ItemClicked(view ,getAdapterPosition());
        }

        public void clearAnimation()
        {
            ingredientInfoContainer.clearAnimation();
        }
    }

    public interface RecyclerViewClickListener
    {

        public void ItemClicked(View v, int position);
    }

    private void setAnimation(View viewToAnimate, int position)
    {

        if (position > LastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            LastPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(MyViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
             holder.clearAnimation();
    }



}

