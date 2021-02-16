package com.hamidraza.coffeshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FoodCategoryAdapter extends RecyclerView.Adapter {

    List<FoodCategory> data;
    Context context;
    int selectedItem = 0;

    public FoodCategoryAdapter(List<FoodCategory> data, Context context){
        this.data = data;
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.category_holder, parent,false);

        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView image;

        public CategoryHolder(View holder){
            super(holder);
            title = holder.findViewById(R.id.txt_title);
            image = holder.findViewById(R.id.img);
        }

    }

}
