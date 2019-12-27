package com.hamidraza.dicee;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button rollButton;
        rollButton = findViewById(R.id.roll_btn);

        final ImageView leftDice = findViewById(R.id.img_leftDice);
        final ImageView rightDice = findViewById(R.id.img_rightDice);

        final int[] diceArray = {R.drawable.dice1,
                R.drawable.dice2,
                R.drawable.dice3,
                R.drawable.dice4,
                R.drawable.dice5,
                R.drawable.dice6};

        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //generate random number
                Random randomNumGen = new Random();
                int number = randomNumGen.nextInt(6);

                //assign the dice images using random number from the arrays
                leftDice.setImageResource(diceArray[number]);

                //re-assign a new random number so the right dice is different from left
                number = randomNumGen.nextInt(6);
                rightDice.setImageResource(diceArray[number]);
            }
        });

    }
}
