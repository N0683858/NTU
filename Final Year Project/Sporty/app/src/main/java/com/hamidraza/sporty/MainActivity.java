package com.hamidraza.sporty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // CardView Objects
    private CardView searchSportCardView;
    private CardView chatCardView;
    private CardView temp1hCardView;
    private CardView temp2CardView;
    private CardView temp3CardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assign CardView
        searchSportCardView = findViewById(R.id.search_sport_card);
        chatCardView = findViewById(R.id.chat_card);
        temp1hCardView = findViewById(R.id.temp1_card);
        temp2CardView = findViewById(R.id.temp2_card);
        temp3CardView = findViewById(R.id.temp_card3);

        // Assign OnClickListener
        searchSportCardView.setOnClickListener(this);
        chatCardView.setOnClickListener(this);
        temp1hCardView.setOnClickListener(this);
        temp2CardView.setOnClickListener(this);
        temp3CardView.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        Intent intent;

        switch (view.getId())
        {
            case R.id.search_sport_card:
                intent = new Intent(this, SportsInfo.class);
                startActivity(intent);
                break;
            case R.id.chat_card:
                intent = new Intent(this, ChatRoom.class);
                startActivity(intent);
                break;
//            case R.id.temp_card3:
//                intent = new Intent(this, temp3.class);
//                startActivity(intent);
//                break;
            default: break;
        }

    }
}