package com.example.ryannaidoo_mobileappdevelopment.smartpantrymanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ryannaidoo_mobileappdevelopment.R;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }
    public List<Recipe> recipes;
    public OnRecipeClickListener listener;
    public String subtitle;
    public RecipeAdapter(List<Recipe> recipes, OnRecipeClickListener listener, String subtitle) {
        this.recipes = recipes;
        this.listener = listener;
        this.subtitle = subtitle;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.name.setText(recipe.name);
        holder.subtitle.setText(subtitle);
        holder.itemView.setOnClickListener(v -> listener.onRecipeClick(recipe));
    }

    @Override
    public int getItemCount(){
        return recipes.size();
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder{
        public TextView name, subtitle;

        RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_recipe_name);
            subtitle = itemView.findViewById(R.id.text_recipe_subtitle);
        }
    }

}
