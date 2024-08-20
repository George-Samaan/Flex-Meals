package com.iti.flex_meals.homeActivity.favouriteFragment.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.iti.flex_meals.R;
import com.iti.flex_meals.categoriesMealsActivity.view.OnMealClick;
import com.iti.flex_meals.db.retrofit.pojo.mealDetails.MealsItem;

import java.util.ArrayList;
import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.ViewHolder> {


    List<MealsItem> favourites;
    private OnMealClick onMealClick;

    public FavouritesAdapter(OnMealClick onMealClick) {
        this.favourites = new ArrayList<>();
        this.onMealClick = onMealClick;
    }

    @NonNull
    @Override
    public FavouritesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reusable_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouritesAdapter.ViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.scale_in_animation);
        holder.itemView.startAnimation(animation);
        
        MealsItem meal = favourites.get(position);

        holder.mealName.setText(meal.getStrMeal());
        Glide.with(holder.itemView.getContext()).load(meal.getStrMealThumb()).into(holder.mealImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMealClick.onMealClick(meal.getIdMeal());
            }
        });

    }

    @Override
    public int getItemCount() {
        return favourites.size();
    }

    public void setFavourites(List<MealsItem> favourites) {
        this.favourites = favourites;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mealName;
        ImageView mealImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.category_name);
            mealImage = itemView.findViewById(R.id.category_image);
        }
    }
}