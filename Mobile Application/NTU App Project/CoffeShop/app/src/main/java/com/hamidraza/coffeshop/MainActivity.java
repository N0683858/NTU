package com.hamidraza.coffeshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerCategories = findViewById(R.id.recycler_categories);
    }

//    private void setCategories(){
//        List<FoodCategory> data = new ArrayList<>();
//
//        FoodCategory foodCategory = new FoodCategory("Latte", R.drawable.ic_latte_coffee);
//        FoodCategory foodCategory2 = new FoodCategory("Espresso", R.drawable.ic_espresso_coffee);
//        FoodCategory foodCategory3 = new FoodCategory("Americano", R.drawable.ic_americano_coffee_icon);
//
//
//        data.add(foodCategory);
//        data.add(foodCategory2);
//        data.add(foodCategory3);
//
//        FoodCategoryAdapter foodCategoryAdapter = new FoodCategoryAdapter(data, MainActivity.this);
//
//        recyclerCategories.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//        recyclerCategories.setAdapter(foodCategoryAdapter);
//        foodCategoryAdapter.notifyDataSetChanged();
//    }
}