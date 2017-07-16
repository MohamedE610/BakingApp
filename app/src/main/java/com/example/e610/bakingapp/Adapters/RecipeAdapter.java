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
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.e610.bakingapp.Models.Recipe;
import com.example.e610.bakingapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder>  {
    ArrayList<Recipe> recipes;
    Context context;
    int  LastPosition=-1;
    RecyclerViewClickListener ClickListener ;
    public RecipeAdapter(){}
    public RecipeAdapter(ArrayList<Recipe> recipes, Context context){
        this.recipes=new ArrayList<>();
        this.recipes=recipes;
        this.context=context;
    }

    public void setClickListener(RecyclerViewClickListener clickListener){
       this.ClickListener= clickListener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.recipeName.setText(recipes.get(position).getName());
        String str=recipes.get(position).getImage();
        if(!recipes.get(position).getImage().equals("")) {
            Picasso.with(context).load(recipes.get(position).getImage()).placeholder(R.drawable.loadingicon)
                    .error(R.drawable.error).into(holder.recipeImage);
        }
        else{
            Picasso.with(context).load(R.drawable.cupcake).placeholder(R.drawable.loadingicon)
                    .error(R.drawable.error).into(holder.recipeImage);
        }


        setAnimation(holder.recipeNameContainer,position);

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView recipeName;
        ImageView recipeImage;
        LinearLayout recipeNameContainer;
        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            recipeName=(TextView)itemView.findViewById(R.id.RecipeName);
            recipeImage=(ImageView) itemView.findViewById(R.id.recipe_image);
            recipeNameContainer=(LinearLayout)itemView.findViewById(R.id.RecipeNameContainer);
        }

        @Override
        public void onClick(View view) {
            if(ClickListener!=null)
            ClickListener.ItemClicked(view ,getAdapterPosition());
        }

        public void clearAnimation()
        {
            recipeNameContainer.clearAnimation();
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

