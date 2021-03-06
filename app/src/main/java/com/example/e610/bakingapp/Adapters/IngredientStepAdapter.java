package com.example.e610.bakingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.e610.bakingapp.Models.Step;
import com.example.e610.bakingapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class IngredientStepAdapter extends RecyclerView.Adapter<IngredientStepAdapter.MyViewHolder>  {
    ArrayList<Object> ingredientAndSteps;
    Context context;
    int  LastPosition=-1;
    RecyclerViewClickListener ClickListener ;
    public IngredientStepAdapter(){}
    public IngredientStepAdapter(ArrayList<Object> ingredientAndSteps, Context context){
        this.ingredientAndSteps=new ArrayList<>();
        this.ingredientAndSteps=ingredientAndSteps;
        this.context=context;
    }

    public void setClickListener(RecyclerViewClickListener clickListener){
       this.ClickListener= clickListener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_step_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if(position==0) {
            String ingredientStr ;
            ingredientStr=(String) ingredientAndSteps.get(position);
            holder.ingredientAndStepName.setText(ingredientStr);
        }
        else{
            Step step;
            step=(Step)ingredientAndSteps.get(position);
            if(!step.getThumbnailURL().equals("")) {
                Picasso.with(context).load(step.getThumbnailURL()).into(holder.ingredientAndStepImageView);
            }
            else{
                Picasso.with(context).load(R.drawable.bowl).placeholder(R.drawable.loadingicon)
                        .error(R.drawable.error).into(holder.ingredientAndStepImageView);
            }
            holder.ingredientAndStepName.setText(step.getShortDescription());
        }


        setAnimation(holder.ingredientAndStepContainer,position);

    }

    @Override
    public int getItemCount() {
        return ingredientAndSteps.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {



        TextView ingredientAndStepName;
        ImageView ingredientAndStepImageView;
        LinearLayout ingredientAndStepContainer;
        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ingredientAndStepName=(TextView)itemView.findViewById(R.id.ingredient_step_name);
            ingredientAndStepImageView=(ImageView)itemView.findViewById(R.id.ingredient_step_image);
            ingredientAndStepContainer=(LinearLayout)itemView.findViewById(R.id.ingredient_step_container);
        }

        @Override
        public void onClick(View view) {
            if(ClickListener!=null)
            ClickListener.ItemClicked(view ,getAdapterPosition());
        }

        public void clearAnimation()
        {
            ingredientAndStepContainer.clearAnimation();
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

